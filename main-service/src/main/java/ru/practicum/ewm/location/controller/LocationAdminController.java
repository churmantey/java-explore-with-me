package ru.practicum.ewm.location.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.dto.NewLocationDto;
import ru.practicum.ewm.location.dto.UpdateLocationDto;
import ru.practicum.ewm.location.service.LocationService;
import ru.practicum.ewm.statslogger.StatsLogger;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/location")
@RequiredArgsConstructor
public class LocationAdminController {

    private final LocationService locationService;
    private final StatsLogger statsLogger;

    @PostMapping
    public LocationDto createLocation(@Valid @RequestBody NewLocationDto newLocationDto, HttpServletRequest request) {
        log.info("POST new location {}", newLocationDto);
        statsLogger.logIPAndPath(request);
        return locationService.createLocation(newLocationDto);
    }

    @PatchMapping("/{locId}")
    public LocationDto updateLocation(@PathVariable("locId") Long locId,
                                      @RequestBody UpdateLocationDto updateLocationDto, HttpServletRequest request) {
        log.info("PATCH location id={}, data={}", locId, updateLocationDto);
        statsLogger.logIPAndPath(request);
        return locationService.updateLocation(locId, updateLocationDto);
    }

    @GetMapping
    public List<LocationDto> getAdminLocationsByFilters(@RequestParam(defaultValue = "0") int from,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        HttpServletRequest request) {
        log.info("GET admin locations from={}, size={}", from, size);
        statsLogger.logIPAndPath(request);
        return locationService.getAdminLocationsByFilters(from, size);

    }


}

