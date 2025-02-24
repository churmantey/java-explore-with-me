package ru.practicum.ewm.location.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
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
    public List<LocationDto> getLocations(HttpServletRequest request) {
        log.info("GET locations");
        statsLogger.logIPAndPath(request);
        return locationService.getVisibleLocations();
    }

    @GetMapping("/{locId}")
    public LocationDto getLocationById(@PathVariable Long locId, HttpServletRequest request) {
        log.info("GET location by id={}", locId);
        statsLogger.logIPAndPath(request);
        return locationService.getVisibleLocationById(locId);
    }

    @GetMapping("/{locId}/events")
    public List<EventFullDto> getLocationEvents(@PathVariable Long locId, @RequestParam("distance") Integer distance,
                                                HttpServletRequest request) {
        log.info("GET location events, locId={}, distance={}", locId, distance);
        statsLogger.logIPAndPath(request);
        return locationService.getLocationEvents(locId, distance);
    }
}

