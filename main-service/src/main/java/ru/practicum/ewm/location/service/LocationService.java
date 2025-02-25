package ru.practicum.ewm.location.service;

import jakarta.validation.Valid;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventLocDto;
import ru.practicum.ewm.location.LocationState;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.dto.NewLocationDto;
import ru.practicum.ewm.location.dto.UpdateLocationDto;

import java.util.List;

public interface LocationService {
    LocationDto createLocation(NewLocationDto newLocationDto);

    LocationDto updateLocation(Long locId, UpdateLocationDto updateLocationDto);

    List<LocationDto> getAdminLocationsByFilters(LocationState state, int from, int size);

    List<LocationDto> getVisibleLocations();

    LocationDto getVisibleLocationById(Long locId);

    List<EventLocDto> getLocationEvents(Long locId, Integer distance, int from, int size);

    void deleteLocation(Long locId);
}
