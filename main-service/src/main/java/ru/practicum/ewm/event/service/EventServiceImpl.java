package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventSortTypes;
import ru.practicum.ewm.event.EventStates;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdateUserEventDto;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.user.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper mapper;

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {
        validateNewEvent(newEventDto);
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isPresent()) {
            Event newEvent = mapper.toEntity(newEventDto);
            newEvent.setInitiator(optUser.get());
            newEvent.setCreatedOn(LocalDateTime.now());
            newEvent.setState(EventStates.PENDING);
            return mapper.toEventFullDto(eventRepository.save(newEvent));
        } else {
            throw makeUserNotFoundException(userId);
        }
    }

    @Override
    public List<EventShortDto> getEventsByFilters(String text, List<Long> categoryIds, Boolean paid,
                                                  LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                  Boolean onlyAvailable, EventSortTypes sortType, int from, int size) {
        return mapper.toEventShortDtoList(eventRepository.getEventsByFilters(text, categoryIds, paid, rangeStart,
                                                                rangeEnd, onlyAvailable, sortType, from, size));
    }

    @Override
    public EventFullDto getEventById(Long eventId) {
        Optional<Event> optEvent = eventRepository.findById(eventId);
        if (optEvent.isPresent()) {
            return mapper.toEventFullDto(optEvent.get());
        }
        else {
           throw makeEventNotFoundException(eventId);
        }
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
            Optional<Event> optEvent = eventRepository.findById(eventId);
            if (optEvent.isPresent()) {
                if (!optEvent.get().getInitiator().getId().equals(userId)) {
                    throw makeUserHasNoEventValidationException(userId, eventId);
                }
                return mapper.toEventFullDto(optEvent.get());
            } else {
                throw makeEventNotFoundException(eventId);
            }
        } else {
            throw makeUserNotFoundException(userId);
        }
    }

    @Override
    public EventFullDto updateUserEvent(Long userId, Long eventId, UpdateUserEventDto updateUserEventDto) {
        Optional<Event> optEvent = eventRepository.findById(eventId);
        if (optEvent.isPresent()) {
            Event event = optEvent.get();
            if (event.getState().equals(EventStates.PUBLISHED)) {
                throw new ValidationException("Published events cannot be modified");
            }
            if (event.getInitiator().getId().equals(userId)) {
                throw makeUserHasNoEventValidationException(userId, eventId);
            }
            updateEventFields(event, updateUserEventDto);

            return mapper.toEventFullDto(event);
        } else {
            throw makeEventNotFoundException(eventId);
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
