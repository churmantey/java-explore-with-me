package ru.practicum.ewm.location.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventLocDto;
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
    @ResponseStatus(HttpStatus.OK)
    public List<LocationDto> getLocations(HttpServletRequest request) {
        log.info("GET locations");
        statsLogger.logIPAndPath(request);
        return locationService.getVisibleLocations();
    }

    @GetMapping("/{locId}")
    @ResponseStatus(HttpStatus.OK)
    public LocationDto getLocationById(@PathVariable Long locId, HttpServletRequest request) {
        log.info("GET location by id={}", locId);
        statsLogger.logIPAndPath(request);
        return locationService.getVisibleLocationById(locId);
    }

    @GetMapping("/{locId}/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventLocDto> getLocationEvents(@PathVariable Long locId,
                                               @RequestParam(name = "distance", defaultValue = "30") Integer distance,
                                               @RequestParam(name = "from", defaultValue = "0") int from,
                                               @RequestParam(name = "size", defaultValue = "10") int size,
                                               HttpServletRequest request) {
        log.info("GET location events, locId={}, distance={}, from={}, size={}", locId, distance, from, size);
        statsLogger.logIPAndPath(request);
        return locationService.getLocationEvents(locId, distance, from, size);
    }
}

