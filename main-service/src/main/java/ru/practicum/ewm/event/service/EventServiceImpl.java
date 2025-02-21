package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventSortTypes;
import ru.practicum.ewm.event.EventStates;
import ru.practicum.ewm.event.StateActionAdmin;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.MalformedDataException;
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
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ParticipationRequestRepository requestRepository;
    private final EventMapper mapper;
    private final ParticipationRequestMapper requestMapper;

    @Override
    @Transactional
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {
        validateNewEvent(newEventDto);
        User user = userRepository.getExistingUser(userId);
        Event newEvent = mapper.toEntity(newEventDto);
        newEvent.setInitiator(user);
        newEvent.setCreatedOn(LocalDateTime.now());
        newEvent.setState(EventStates.PENDING);
        return mapper.toEventFullDto(eventRepository.save(newEvent));
    }

    @Override
    public List<EventShortDto> getEventsByFilters(String text, List<Long> categoryIds, Boolean paid,
                                                  LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                  Boolean onlyAvailable, EventSortTypes sortType, int from, int size) {
        validateRangeDates(rangeStart, rangeEnd);
        List<Event> elist = eventRepository.getEventsByFilters(text, categoryIds, paid, rangeStart,
                rangeEnd, onlyAvailable, sortType, from, size);
        return mapper.toEventShortDtoList(elist);
    }

    @Override
    public List<EventFullDto> getAdminEventsByFilters(List<Long> users, List<EventStates> states, List<Long> categories,
                                                      LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                      int from, int size) {
        validateRangeDates(rangeStart, rangeEnd);
        return mapper.toEventFullDto(eventRepository.getAdminEventsByFilters(users, states, categories,
                rangeStart, rangeEnd, from, size));
    }

    @Override
    public EventFullDto getEventById(Long eventId) {
        return mapper.toEventFullDto(eventRepository.getExistingEvent(eventId));
    }

    @Override
    public EventFullDto getPublishedEventById(Long eventId) {
        Event event = eventRepository.getExistingEvent(eventId);
        event.addView();
        if (!event.getState().equals(EventStates.PUBLISHED)) {
            throw new NotFoundException("Event with id=" + eventId + " is not published");
        }
        return mapper.toEventFullDto(event);
    }

    @Override
    public List<EventShortDto> getUserEvents(Long userId, int from, int size) {
        if (userRepository.existsById(userId)) {
            return mapper.toEventShortDtoList(eventRepository.findByInitiator_IdOrderByIdAsc(userId, from, size));
        } else {
            throw makeUserNotFoundException(userId);
        }
    }

    @Override
    public EventFullDto getUserEventById(Long userId, Long eventId) {
        if (userRepository.existsById(userId)) {
            Event event = eventRepository.getExistingEvent(eventId);
            if (!event.getInitiator().getId().equals(userId)) {
                throw makeUserHasNoEventValidationException(userId, eventId);
            }
            return mapper.toEventFullDto(event);
        } else {
            throw makeUserNotFoundException(userId);
        }
    }

    @Override
    @Transactional
    public EventFullDto updateUserEvent(Long userId, Long eventId, UpdateUserEventDto updateUserEventDto) {
        Event event = eventRepository.getExistingEvent(eventId);
        if (event.getState().equals(EventStates.PUBLISHED)) {
            throw new ValidationException("Published events cannot be modified");
        }
        if (!event.getInitiator().getId().equals(userId)) {
            throw makeUserHasNoEventValidationException(userId, eventId);
        }
        updateEventFields(event, updateUserEventDto);
        return mapper.toEventFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto updateAdminEvent(Long eventId, UpdateAdminEventDto updateAdminEventDto) {
        Event event = eventRepository.getExistingEvent(eventId);
        if (updateAdminEventDto.getStateAction() != null
                && updateAdminEventDto.getStateAction().equals(StateActionAdmin.PUBLISH_EVENT)
                && event.getState().equals(EventStates.CANCELED)) {
            throw new ValidationException("Event with id=" + eventId + " is canceled and can not be published");
        }
        if (event.getState().equals(EventStates.PUBLISHED)) {
            throw new ValidationException("Published events cannot be modified");
        }
        updateAdminEventFields(event, updateAdminEventDto);
        return mapper.toEventFullDto(event);
    }

    @Override
    public List<ParticipationRequestDto> getUserEventRequests(Long userId, Long eventId) {
        if (!userRepository.existsById(userId)) {
            throw makeUserNotFoundException(userId);
        }
        Event event = eventRepository.getExistingEvent(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw makeUserHasNoEventValidationException(userId, eventId);
        }
        return requestMapper.toParticipationRequestDto(
                requestRepository.findByEvent_IdOrderByIdAsc(eventId));
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResponse updateRequestStates(Long userId, Long eventId,
                                                                EventRequestStatusUpdateRequest updateRequest) {
        User user = userRepository.getExistingUser(userId);
        Event event = eventRepository.getExistingEvent(eventId);
        if (!event.getInitiator().getId().equals(userId)) {
            throw makeUserHasNoEventValidationException(userId, eventId);
        }
        if (!event.getRequestModeration() || event.getParticipantLimit() == null
                || event.getParticipantLimit().equals(0)) {
            throw new ValidationException("Event with id=" + event.getId() +
                    " has no request moderation");
        }
        List<ParticipationRequest> requests = requestRepository.findAllById(updateRequest.getRequestIds());
        // проверка на наличие запросов не находящихся в ожидании
        if (requests.stream()
                .anyMatch(request -> !request.getStatus().equals(RequestStates.PENDING))) {
            throw new ValidationException("Some requests are not in pending state!");
        }
        // проверка на наличие запросов на другое событие
        if (requests.stream()
                .anyMatch(request -> !request.getEvent().getId().equals(eventId))) {
            throw new ValidationException("Some requests are for other events!");
        }

        EventRequestStatusUpdateResponse response = new EventRequestStatusUpdateResponse();
        switch (updateRequest.getStatus()) {
            case CONFIRMED:
                if (!event.hasFreeSpots()) {
                    throw new ValidationException("Event with id=" + event.getId() +
                            " is fully booked, no more requests allowed");
                }
                for (ParticipationRequest request : requests) {
                    if (event.hasFreeSpots()) {
                        request.setStatus(RequestStates.CONFIRMED);
                        response.getConfirmedRequests().add(requestMapper.toParticipationRequestDto(request));
                        event.addConfirm();
                    } else {
                        request.setStatus(RequestStates.REJECTED);
                        response.getRejectedRequests().add(requestMapper.toParticipationRequestDto(request));
                    }
                }
                break;
            case REJECTED:
                for (ParticipationRequest request : requests) {
                    request.setStatus(RequestStates.REJECTED);
                    response.getRejectedRequests().add(requestMapper.toParticipationRequestDto(request));
                }
                break;
            default:
                throw new ValidationException("Unknown request status: " + updateRequest.getStatus());
        }
        return response;
    }

    private void validateRangeDates(LocalDateTime start, LocalDateTime end) {
        if (start != null && end != null && end.isBefore(start)) {
            throw new MalformedDataException("Invalid date range: starting date is before ending date");
        }
    }

    private void validateNewEvent(NewEventDto newEventDto) {
        if (newEventDto == null) {
            throw new ValidationException("New event of NULL received");
        } else if (newEventDto.getEventDate() == null
                || newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw makeEventDateValidationException();
        }
    }

    private void updateAdminEventFields(Event event, UpdateAdminEventDto updateAdminEventDto) {
        if (updateAdminEventDto.getCategoryId() != null) {
            event.setCategory(categoryRepository.findById(updateAdminEventDto.getCategoryId())
                    .orElseThrow(() -> new ValidationException("Category (id=" +
                            updateAdminEventDto.getCategoryId() + ") doesn't exist")));
        }
        if (updateAdminEventDto.getEventDate() != null) {
            event.setEventDate(updateAdminEventDto.getEventDate());
        }

        if (updateAdminEventDto.getTitle() != null) event.setTitle(updateAdminEventDto.getTitle());
        if (updateAdminEventDto.getAnnotation() != null) event.setAnnotation(updateAdminEventDto.getAnnotation());
        if (updateAdminEventDto.getDescription() != null) event.setDescription(updateAdminEventDto.getDescription());
        if (updateAdminEventDto.getPaid() != null) event.setPaid(updateAdminEventDto.getPaid());
        if (updateAdminEventDto.getParticipantLimit() != null) event.setParticipantLimit(
                updateAdminEventDto.getParticipantLimit());
        if (updateAdminEventDto.getStateAction() != null) {
            switch (updateAdminEventDto.getStateAction()) {
                case REJECT_EVENT:
                    event.setState(EventStates.CANCELED);
                    break;
                case PUBLISH_EVENT:
                    if (event.getEventDate().minusHours(1).isBefore(LocalDateTime.now())) {
                        throw new ValidationException("Can't publish event - event date is too soon.");
                    }
                    event.setState(EventStates.PUBLISHED);
                    event.setPublishedOn(LocalDateTime.now());
                    break;
                default:
                    throw new NotFoundException(
                            "Unknown event state action: " + updateAdminEventDto.getStateAction());
            }
        }
    }

    private void updateEventFields(Event event, UpdateUserEventDto updateUserEventDto) {
        if (updateUserEventDto.getCategoryId() != null) {
            event.setCategory(categoryRepository.findById(updateUserEventDto.getCategoryId())
                    .orElseThrow(() -> new ValidationException("Category (id=" +
                            updateUserEventDto.getCategoryId() + ") doesn't exist")));
        }
        if (updateUserEventDto.getEventDate() != null) {
            if (updateUserEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw makeEventDateValidationException();
            }
            event.setEventDate(updateUserEventDto.getEventDate());
        }

        if (updateUserEventDto.getTitle() != null) event.setTitle(updateUserEventDto.getTitle());
        if (updateUserEventDto.getAnnotation() != null) event.setAnnotation(updateUserEventDto.getAnnotation());
        if (updateUserEventDto.getDescription() != null) event.setDescription(updateUserEventDto.getDescription());
        if (updateUserEventDto.getPaid() != null) event.setPaid(updateUserEventDto.getPaid());
        if (updateUserEventDto.getParticipantLimit() != null) event.setParticipantLimit(
                updateUserEventDto.getParticipantLimit());
        if (updateUserEventDto.getStateAction() != null) {
            switch (updateUserEventDto.getStateAction()) {
                case CANCEL_REVIEW -> event.setState(EventStates.CANCELED);
                case SEND_TO_REVIEW -> event.setState(EventStates.PENDING);
                default -> throw new NotFoundException(
                        "Unknown event state action: " + updateUserEventDto.getStateAction());
            }
        }
    }

    private ValidationException makeUserHasNoEventValidationException(Long userId, Long eventId) {
        return new ValidationException("User with id=" + userId + " has no event with id=" + eventId);
    }

    private ValidationException makeEventDateValidationException() {
        return new ValidationException("Event should start in not less than 2 hours");
    }

    private NotFoundException makeEventNotFoundException(Long eventId) {
        return new NotFoundException("Event with id=" + eventId + " not found");
    }

    private NotFoundException makeUserNotFoundException(Long userId) {
        return new NotFoundException("User with id=" + userId + "not found");
    }
}
