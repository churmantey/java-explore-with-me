package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.EventSortTypes;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;

import java.time.LocalDateTime;
import java.util.List;

public class EventServiceImpl implements EventService {

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {
        return null;
    }

    @Override
    public List<EventShortDto> getEventsByFilters(String text, List<Long> categoryIds, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortTypes sortType, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto getEventById(Long eventId) {
        return null;
    }

}
