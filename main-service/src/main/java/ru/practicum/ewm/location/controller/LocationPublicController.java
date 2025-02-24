package ru.practicum.ewm.location.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.service.LocationService;
import ru.practicum.ewm.statslogger.StatsLogger;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationPublicController {

    private final LocationService locationService;
    private final StatsLogger statsLogger;

    @GetMapping
    public List<LocationDto> getLocations() {

    }

    @GetMapping("{locId}")
    public LocationDto getLocationById(@PathVariable Long locId) {

    }



}

