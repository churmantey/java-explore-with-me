package ru.practicum.ewm.event.mapper;

import org.mapstruct.*;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.user.mapper.UserMapper;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CategoryMapper.class, UserMapper.class})
public interface EventMapper {

    @Mapping(source = "location.lat", target = "locationLat")
    @Mapping(source = "location.lon", target = "locationLon")
    Event toEntity(EventFullDto eventFullDto);

    Event toEntity(EventShortDto eventShortDto);

    @Mapping(source = "category", target = "category.id")
    @Mapping(source = "location.lat", target = "locationLat")
    @Mapping(source = "location.lon", target = "locationLon")
    Event toEntity(NewEventDto newEventDto);

    @Mapping(source = "locationLat", target = "location.lat")
    @Mapping(source = "locationLon", target = "location.lon")
    EventFullDto toEventFullDto(Event event);

    EventShortDto toEventShortDto(Event event);

    List<EventShortDto> toEventShortDtoList(List<Event> eventList);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "location.lat", target = "locationLat")
    @Mapping(source = "location.lon", target = "locationLon")
    @Mapping(target = "state", ignore = true)
    Event toEntity(UpdateUserEventDto updateUserEventDto);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "locationLat", target = "location.lat")
    @Mapping(source = "locationLon", target = "location.lon")
    @Mapping(target = "state", ignore = true)
    UpdateUserEventDto toUpdateUserEventDto(Event event);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "locationLat", target = "location.lat")
    @Mapping(source = "locationLon", target = "location.lon")
    @Mapping(target = "state", ignore = true)
    UpdateAdminEventDto toUpdateAdminEventDto(Event event);

//    @AfterMapping
//    default void setDtoLocations(Event event, @MappingTarget EventFullDto fullDto) {
//        fullDto.getLocation().setLat(event.getLocationLat());
//        fullDto.getLocation().setLon(event.getLocationLon());
//    }
//
//    @AfterMapping
//    default void setEventLocations(@MappingTarget Event event, EventFullDto fullDto) {
//        if (fullDto.getLocation() != null) {
//            event.setLocationLat(fullDto.getLocation().getLat());
//            event.setLocationLon(fullDto.getLocation().getLon());
//        }
//    }
//
//    @AfterMapping
//    default void setEventLocations(@MappingTarget Event event, NewEventDto newDto) {
//        if (newDto.getLocation() != null) {
//            event.setLocationLat(newDto.getLocation().getLat());
//            event.setLocationLon(newDto.getLocation().getLon());
//        }
//    }


}