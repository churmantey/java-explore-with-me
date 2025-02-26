package ru.practicum.ewm.location.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.dto.EventLocDto;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.location.Location;
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
    private final EventRepository eventRepository;
    private final LocationMapper mapper;

    @Override
    @Transactional
    public LocationDto createLocation(NewLocationDto newLocationDto) {
        Location location = mapper.toEntity(newLocationDto);
        location.setState(LocationState.HIDDEN);
        return mapper.toLocationDto(locationRepository.save(location));
    }

    @Override
    @Transactional
    public LocationDto updateLocation(Long locId, UpdateLocationDto updateLocationDto) {
        Location location = locationRepository.getExistingLocation(locId);
        updateFields(location, updateLocationDto);
        return mapper.toLocationDto(location);
    }

    @Override
    @Transactional
    public void deleteLocation(Long locId) {
        if (locationRepository.existsById(locId)) {
            locationRepository.deleteById(locId);
        } else {
            throw new NotFoundException("Location with id=" + locId + " not found");
        }
    }

    @Override
    public List<LocationDto> getAdminLocationsByFilters(LocationState state, int from, int size) {
        if (state == null) {
            return mapper.toLocationDto(locationRepository.findPortion(from, size));
        } else {
            return mapper.toLocationDto(locationRepository.findPortionByState(state, from, size));
        }
    }

    @Override
    public List<LocationDto> getVisibleLocations() {
        return mapper.toLocationDto(locationRepository.findByStateOrderById(LocationState.VISIBLE));
    }

    @Override
    public LocationDto getVisibleLocationById(Long locId) {
        return mapper.toLocationDto(locationRepository.findByIdAndState(locId, LocationState.VISIBLE)
                .orElseThrow(() -> new NotFoundException("No location found, id=" + locId)));
    }

    @Override
    public LocationDto getLocationById(Long locId) {
        return mapper.toLocationDto(locationRepository.findById(locId)
                .orElseThrow(() -> new NotFoundException("No location found, id=" + locId)));
    }

    @Override
    public List<EventLocDto> getLocationEvents(Long locId, Integer distance, int from, int size) {
        Location location = locationRepository.getExistingLocation(locId);
        return eventRepository.findEventsAroundLocation(
                location.getLatitude(), location.getLongitude(), distance, from, size);
    }

    private void updateFields(Location location, UpdateLocationDto updateLocationDto) {
        String title = updateLocationDto.getTitle();
        if (title != null && !title.isBlank()) {
            location.setTitle(title);
        }
        String description = updateLocationDto.getDescription();
        if (description != null && !description.isBlank()) {
            location.setDescription(description);
        }
        Float lat = updateLocationDto.getLatitude();
        if (lat != null) {
            location.setLatitude(lat);
        }
        Float lon = updateLocationDto.getLongitude();
        if (lon != null) {
            location.setLongitude(lon);
        }
        LocationState state = updateLocationDto.getState();
        if (state != null) {
            location.setState(state);
        }
    }
}
