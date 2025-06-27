package ru.practicum.ewm.event.repository;

import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventSortTypes;
import ru.practicum.ewm.event.EventStates;
import ru.practicum.ewm.event.dto.EventLocDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepositoryCustom {

    List<Event> getEventsByFilters(String text, List<Long> categoryIds, Boolean paid,
                                   LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                   Boolean onlyAvailable, EventSortTypes sortType, int from, int size);

    List<Event> getAdminEventsByFilters(List<Long> users, List<EventStates> states, List<Long> categories,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                        int from, int size);

    List<EventLocDto> findEventsAroundLocation(Float latitude, Float longitude, Integer distance, int from, int size);

}
