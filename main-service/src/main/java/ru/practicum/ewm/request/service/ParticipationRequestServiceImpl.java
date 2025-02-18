package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventStates;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.request.ParticipationRequest;
import ru.practicum.ewm.request.RequestStates;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.mapper.ParticipationRequestMapper;
import ru.practicum.ewm.request.repository.ParticipationRequestRepository;
import ru.practicum.ewm.user.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private final ParticipationRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ParticipationRequestMapper mapper;

    private final EventMapper eventMapper;

    @Override
    public List<ParticipationRequestDto> getEventRequests(Long eventId) {
        return mapper.toParticipationRequestDto(requestRepository.findByEvent_IdOrderByIdAsc(eventId));
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        return mapper.toParticipationRequestDto(requestRepository.findByRequester_IdOrderByIdAsc(userId));
    }

    @Override
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        User requestor = userRepository.getExistingUser(userId);
        Event event = eventRepository.getExistingEvent(eventId);
        validateNewRequest(requestor, event);
        ParticipationRequest request = new ParticipationRequest();
        request.setRequester(requestor);
        request.setEvent(event);
        request.setCreated(LocalDateTime.now());
        if (!event.getRequestModeration() || event.getParticipantLimit().equals(0)) {
            request.setStatus(RequestStates.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests()+1);
        } else {
            request.setStatus(RequestStates.PENDING);
        }
        return mapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        User user = userRepository.getExistingUser(userId);
        ParticipationRequest request = requestRepository.getExistingRequest(requestId);
        if (!request.getRequester().getId().equals(userId)) {
            throw new ValidationException("User with id=" + userId + " has no request with id=" + requestId);
        }
        request.setStatus(RequestStates.REJECTED);
        return null;
    }

    private void validateNewRequest(User user, Event event) {
        if (user == null) throw new ValidationException("Null user value received");
        if (event == null) throw new ValidationException("Null event value received");
        if (!event.getState().equals(EventStates.PUBLISHED))
            throw new NotFoundException("Event with id=" + event.getId() + " is not available");
        // проверка повторного запроса на участие
        if (requestRepository.existsByRequester_IdAndEvent_Id(user.getId(), event.getId())) {
            throw new ValidationException("User with id=" + user.getId() + " already has a request for " +
                    "event with id=" + event.getId());
        }
        if (event.getInitiator().getId().equals(user.getId())) {
            throw new ValidationException("User with id=" + user.getId() + " is an initiator of " +
                    "event with id=" + event.getId());
        }
        if (!eventHasFreeSpots(event)) {
            throw new ValidationException("Event with id=" + event.getId() +
                    " is fully booked, no more requests allowed");
        }
    }

    private boolean eventHasFreeSpots(Event event) {
        return event.getParticipantLimit() == null || event.getParticipantLimit().equals(0)
                || event.getConfirmedRequests() < event.getParticipantLimit();
    }

}
