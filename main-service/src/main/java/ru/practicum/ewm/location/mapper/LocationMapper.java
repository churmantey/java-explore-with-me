package ru.practicum.ewm.location.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.location.Location;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.dto.UpdateLocationDto;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {
    Location toEntity(LocationDto locationDto);

    LocationDto toLocationDto(Location location);

    List<LocationDto> toLocationDto(List<Location> location);

    Location toEntity(UpdateLocationDto updateLocationDto);

    UpdateLocationDto toUpdateLocationDto(Location location);
}