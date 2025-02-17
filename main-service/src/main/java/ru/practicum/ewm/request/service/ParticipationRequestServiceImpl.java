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
        return mapper.toParticipationRequestDto(requestRepository.findByRequestor_IdOrderByIdAsc(userId));
    }

    @Override
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        User requestor = userRepository.getExistingUser(userId);
        Event event = eventRepository.getExistingEvent(eventId);
        validateNewRequest(requestor, event);
        ParticipationRequest request = new ParticipationRequest();
        request.setRequestor(requestor);
        request.setEvent(event);
        if (!event.getRequestModeration() || event.getParticipantLimit().equals(0)) {
            request.setState(RequestStates.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests()+1);
        } else {
            request.setState(RequestStates.PENDING);
        }
        return mapper.toParticipationRequestDto(requestRepository.save(request));
    }

    private void validateNewRequest(User user, Event event) {
        if (user == null) throw new ValidationException("Null user value received");
        if (event == null) throw new ValidationException("Null event value received");
        if (!event.getState().equals(EventStates.PUBLISHED))
            throw new NotFoundException("Event with id=" + event.getId() + " is not available");
        // проверка повторного запроса на участие
        if (requestRepository.existsByRequestor_IdAndEvent_Id(user.getId(), event.getId())) {
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
