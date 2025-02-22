package ru.practicum.ewm.event.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.EventStates;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.UpdateAdminEventDto;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.stats.dto.HitDto;
import ru.practicum.ewm.statslogger.StatsLogger;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class EventAdminController {

    private final EventService eventService;
    private final StatsLogger statsLogger;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getEventsByFilters(
            @RequestParam(name = "users", required = false) List<Long> users,
            @RequestParam(name = "states", required = false) List<EventStates> states,
            @RequestParam(name = "categories", required = false) List<Long> categories,
            @RequestParam(name = "rangeStart", required = false) @DateTimeFormat(pattern = HitDto.DATE_FORMAT_PATTERN)
            LocalDateTime rangeStart,
            @RequestParam(name = "rangeEnd", required = false) @DateTimeFormat(pattern = HitDto.DATE_FORMAT_PATTERN)
            LocalDateTime rangeEnd,
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "10") int size,
            HttpServletRequest request) {
        log.info("GET admin events , users={}, states={}, categories={}, rangeStart={}, rangeEnd={}, from={}, size={}",
                users, states, categories, rangeStart, rangeEnd, from, size);
        statsLogger.logIPAndPath(request);
        return eventService.getAdminEventsByFilters(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateAdminEvent(@PathVariable("eventId") Long eventId,
                                         @Valid @RequestBody UpdateAdminEventDto adminDto, HttpServletRequest request) {
        log.info("PATCH admin event , adminDto={}", adminDto);
        statsLogger.logIPAndPath(request);
        return eventService.updateAdminEvent(eventId, adminDto);
    }
}
