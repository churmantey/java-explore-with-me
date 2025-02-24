package ru.practicum.ewm.location.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.location.LocationState;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.dto.NewLocationDto;
import ru.practicum.ewm.location.dto.UpdateLocationDto;
import ru.practicum.ewm.location.mapper.LocationMapper;
import ru.practicum.ewm.location.repository.LocationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper mapper;

    @Override
    public LocationDto createLocation(NewLocationDto newLocationDto) {
        return null;
    }

    @Override
    public LocationDto updateLocation(Long locId, UpdateLocationDto updateLocationDto) {
        return null;
    }

    @Override
    public void deleteLocation(Long locId) {
    }

    @Override
    public List<LocationDto> getAdminLocationsByFilters(LocationState state, int from, int size) {
        return List.of();
    }

    @Override
    public List<LocationDto> getVisibleLocations() {
        return List.of();
    }

    @Override
    public LocationDto getVisibleLocationById(Long locId) {
        return null;
    }

    @Override
    public List<EventFullDto> getLocationEvents(Long locId, Integer distance) {
        return List.of();
    }
}
