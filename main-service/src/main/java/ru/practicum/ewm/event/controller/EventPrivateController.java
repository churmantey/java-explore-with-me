package ru.practicum.ewm.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.statslogger.StatsLogger;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class EventPrivateController {

    private final EventService eventService;
    private final StatsLogger statsLogger;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getUserEvents(@PathVariable("userId") Long userId,
                                             @RequestParam(name = "from", defaultValue = "0") int from,
                                             @RequestParam(name = "size", defaultValue = "10") int size,
                                             HttpServletRequest request) {
        log.info("GET user events , userId={}, from={}, size={}", userId, from, size);
        statsLogger.logIPAndPath(request);
        return eventService.getUserEvents(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable("userId") Long userId, @Valid @RequestBody NewEventDto newEventDto,
                                    HttpServletRequest request) {
        log.info("POST new event, userId={}, {}", userId, newEventDto);
        statsLogger.logIPAndPath(request);
        return eventService.createEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getUserEventById(@PathVariable("userId") Long userId, @PathVariable("eventId") Long eventId,
                                         HttpServletRequest request) {
        log.info("GET user event , userId={}, eventId={}", userId, eventId);
        statsLogger.logIPAndPath(request);
        return eventService.getUserEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateUserEvent(@PathVariable("userId") Long userId, @PathVariable("eventId") Long eventId,
                                        @Valid @RequestBody UpdateUserEventDto updateUserEventDto,
                                        HttpServletRequest request) {
        log.info("PATCH user event , userId={}, eventId={}, data={}", userId, eventId, updateUserEventDto);
        statsLogger.logIPAndPath(request);
        return eventService.updateUserEvent(userId, eventId, updateUserEventDto);
    }

    //Получение информации о запросах на участие в событии текущего пользователя
    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getUserEventRequests(@PathVariable("userId") Long userId,
                                                              @PathVariable("eventId") Long eventId,
                                                              HttpServletRequest request) {
        log.info("GET user event requests, userId={}, eventId={}", userId, eventId);
        statsLogger.logIPAndPath(request);
        return eventService.getUserEventRequests(userId, eventId);
    }

    //PATCH /users/{userId}/events/{eventId}/requests
    //Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя
    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResponse updateRequestStates(
            @PathVariable("userId") Long userId,
            @PathVariable("eventId") Long eventId,
            @RequestBody EventRequestStatusUpdateRequest updateRequest,
            HttpServletRequest request) {
        log.info("PATCH requests states, userId={}, eventId={}, updateRequest={}", userId, eventId, updateRequest);
        statsLogger.logIPAndPath(request);
        return eventService.updateRequestStates(userId, eventId, updateRequest);
    }

}
