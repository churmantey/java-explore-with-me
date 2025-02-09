package ru.practicum.ewm.event.repository;

import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventSortTypes;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepositoryCustom {

     List<Event> getEventsByFilters(String text, List<Long> categoryIds, Boolean paid,
                                    LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                    Boolean onlyAvailable, EventSortTypes sortType, int from, int size);


}
