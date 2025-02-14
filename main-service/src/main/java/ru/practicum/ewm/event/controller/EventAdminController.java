package ru.practicum.ewm.event.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.EventStates;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.service.EventService;
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
    public List<EventFullDto> getEventsByFilters(@RequestParam List<Long> users,
                                                 @RequestParam List<EventStates> states,
                                                 @RequestParam List<Long> categories,
                                                 @RequestParam LocalDateTime rangeStart,
                                                 @RequestParam LocalDateTime rangeEnd,
                                                 @RequestParam(name = "from", defaultValue = "0") int from,
                                                 @RequestParam(name = "size", defaultValue = "10") int size,
                                                 HttpServletRequest request) {
        log.info("GET admin events , users={}, states={}, categories={}, rangeStart={}, rangeEnd={}, from={}, size={}",
                users, states, categories, rangeStart, rangeEnd, from, size);
        statsLogger.logIPAndPath(request);
        return eventService.getAdminEventsByFilters(users, states, categories, rangeStart, rangeEnd, from, size);
    }

}
