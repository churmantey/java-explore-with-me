package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.EventSortTypes;
import ru.practicum.ewm.event.EventStates;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<EventShortDto> getEventsByFilters(String text, List<Long> categoryIds, Boolean paid, LocalDateTime rangeStart,
                                           LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortTypes sortType,
                                           int from, int size);

    List<EventFullDto> getAdminEventsByFilters(List<Long> users, List<EventStates> states, List<Long> categories,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);

    EventFullDto getEventById(Long eventId);

    EventFullDto getPublishedEventById(Long eventId);

    EventFullDto createEvent(Long userId, NewEventDto newEventDto);

    List<EventShortDto> getUserEvents(Long userId, int from, int size);

    EventFullDto getUserEventById(Long userId, Long eventId);

    EventFullDto updateUserEvent(Long userId, Long eventId, UpdateUserEventDto updateUserEventDto);

    EventFullDto updateAdminEvent(Long eventId, UpdateAdminEventDto updateAdminEventDto);

    List<ParticipationRequestDto> getUserEventRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResponse updateRequestStates(Long userId, Long eventId,
                                                         EventRequestStatusUpdateRequest updateRequest);
}
