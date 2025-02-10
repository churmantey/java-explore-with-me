package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventSortTypes;
import ru.practicum.ewm.event.EventStates;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
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
                    throw new ValidationException("User (id=" + userId + ") has no event with id=" + eventId);
                }
                return mapper.toEventFullDto(optEvent.get());
            } else {
                throw makeEventNotFoundException(eventId);
            }
        } else {
            throw makeUserNotFoundException(userId);
        }
    }

    private void validateNewEvent(NewEventDto newEventDto) {
        if (newEventDto == null) {
            throw new ValidationException("New event of NULL received");
        } else if (newEventDto.getEventDate() == null
                    || newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationException("New event should start in not less than 2 hours");
        }
    }

    private NotFoundException makeEventNotFoundException(Long eventId) {
        return new NotFoundException("Event with id=" + eventId + " not found");
    }

    private NotFoundException makeUserNotFoundException(Long userId) {
        return new NotFoundException("User with id=" + userId + "not found");
    }

}
