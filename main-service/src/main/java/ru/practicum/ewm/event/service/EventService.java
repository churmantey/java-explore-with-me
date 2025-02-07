package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.EventSortTypes;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<EventShortDto> getEventsByFilters(String text, List<Long> categoryIds, Boolean paid, LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortTypes sortType,
                                           int from, int size);

    EventFullDto getEventById(Long eventId);

    EventFullDto createEvent(Long userId, NewEventDto newEventDto);

}
