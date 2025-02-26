package ru.practicum.ewm.location.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.location.LocationState;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.dto.NewLocationDto;
import ru.practicum.ewm.location.dto.UpdateLocationDto;
import ru.practicum.ewm.location.service.LocationService;
import ru.practicum.ewm.statslogger.StatsLogger;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/locations")
@RequiredArgsConstructor
public class LocationAdminController {

    private final LocationService locationService;
    private final StatsLogger statsLogger;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto createLocation(@Valid @RequestBody NewLocationDto newLocationDto, HttpServletRequest request) {
        log.info("POST new location {}", newLocationDto);
        statsLogger.logIPAndPath(request);
        return locationService.createLocation(newLocationDto);
    }

    @PatchMapping("/{locId}")
    @ResponseStatus(HttpStatus.OK)
    public LocationDto updateLocation(@PathVariable("locId") Long locId,
                                      @Valid @RequestBody UpdateLocationDto updateLocationDto, HttpServletRequest request) {
        log.info("PATCH location id={}, data={}", locId, updateLocationDto);
        statsLogger.logIPAndPath(request);
        return locationService.updateLocation(locId, updateLocationDto);
    }

    @DeleteMapping("/{locId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable("locId") Long locId, HttpServletRequest request) {
        log.info("DELETE location id={}", locId);
        statsLogger.logIPAndPath(request);
        locationService.deleteLocation(locId);
    }

    @GetMapping("/{locId}")
    @ResponseStatus(HttpStatus.OK)
    public LocationDto getLocationById(@PathVariable Long locId, HttpServletRequest request) {
        log.info("GET admin location by id={}", locId);
        statsLogger.logIPAndPath(request);
        return locationService.getLocationById(locId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LocationDto> getAdminLocationsByFilters(@RequestParam(required = false) LocationState state,
                                                        @RequestParam(defaultValue = "0") int from,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        HttpServletRequest request) {
        log.info("GET admin locations state={}, from={}, size={}", state, from, size);
        statsLogger.logIPAndPath(request);
        return locationService.getAdminLocationsByFilters(state, from, size);
    }
}

