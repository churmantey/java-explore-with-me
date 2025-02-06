package ru.practicum.ewm.event.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventPublicController {

    @GetMapping
    public List<EventShortDto> getEventsByFilters() {

    }

    @GetMapping
    public EventFullDto getEventsByFilters() {

    }


}
