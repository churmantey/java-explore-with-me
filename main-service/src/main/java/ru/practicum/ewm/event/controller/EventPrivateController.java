package ru.practicum.ewm.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.statslogger.StatsLogger;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class EventPrivateController {

    private final EventService eventService;
    private final StatsLogger statsLogger;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable("userId") Long userId, @Valid @RequestBody NewEventDto newEventDto,
                                    HttpServletRequest request) {
        log.info("POST new event, userId={}, {}", userId, newEventDto);
        statsLogger.logIPAndPath(request);
        return eventService.createEvent(userId, newEventDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getUserEvents(@PathVariable("userId") Long userId,
                                             @RequestParam(name = "from", defaultValue = "0") int from,
                                             @RequestParam(name = "size", defaultValue = "10") int size) {
        return eventService.getUserEvents(userId, from, size);
    }
}
