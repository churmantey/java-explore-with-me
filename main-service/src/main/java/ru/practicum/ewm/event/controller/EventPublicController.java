package ru.practicum.ewm.event.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/events")
public class EventPublicController {

    @GetMapping
    public List<EventShortDto> getEventsByFilters() {
        return null;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventsById(@PathVariable("eventId") Long eventId) {
        return null;
    }

}
