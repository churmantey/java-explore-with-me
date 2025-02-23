package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventStates;
import ru.practicum.ewm.event.repository.EventRepository;
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

    @Override
    public List<ParticipationRequestDto> getEventRequests(Long eventId) {
        return mapper.toParticipationRequestDto(requestRepository.findByEventIdOrderByIdAsc(eventId));
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        return mapper.toParticipationRequestDto(requestRepository.findByRequesterIdOrderByIdAsc(userId));
    }

    @Override
    @Transactional
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
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
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
        request.setStatus(RequestStates.CANCELED);
        return mapper.toParticipationRequestDto(request);
    }

    private void validateNewRequest(User user, Event event) {
        if (user == null) throw new ValidationException("Null user value received");
        if (event == null) throw new ValidationException("Null event value received");
        if (!event.getState().equals(EventStates.PUBLISHED))
            throw new ValidationException("Event with id=" + event.getId() + " is not available");
        // проверка повторного запроса на участие
        if (requestRepository.existsByRequesterIdAndEventId(user.getId(), event.getId())) {
            throw new ValidationException("User with id=" + user.getId() + " already has a request for " +
                    "event with id=" + event.getId());
        }
        if (event.getInitiator().getId().equals(user.getId())) {
            throw new ValidationException("User with id=" + user.getId() + " is an initiator of " +
                    "event with id=" + event.getId());
        }
        if (!event.hasFreeSpots()) {
            throw new ValidationException("Event with id=" + event.getId() +
                    " is fully booked, no more requests allowed");
        }
    }

}
