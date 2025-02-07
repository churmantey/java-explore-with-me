package ru.practicum.ewm.event.mapper;

import org.mapstruct.*;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.LocationDto;
import ru.practicum.ewm.user.mapper.UserMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CategoryMapper.class, UserMapper.class})
public interface EventMapper {

    Event toEntity(EventFullDto eventFullDto);

    Event toEntity(EventShortDto eventShortDto);

    @Mapping(source = "locationLat", target = "location.lat")
    @Mapping(source = "locationLon", target = "location.lon")
    EventFullDto toEventFullDto(Event event);

    EventShortDto toEventShortDto(Event event);

//
//    @Named("getLocation")
//    default LocationDto getLocation(Event event) {
//        return new LocationDto(event.getLocationLat(), event.getLocationLon());
//    }
//
//    @Named("getLocation")
//    default Double getLocationLatLon(Event event) {
//        return new LocationDto(event.getLocationLat(), event.getLocationLon());
//    }


}