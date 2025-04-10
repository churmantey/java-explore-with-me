package ru.practicum.ewm.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
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

    List<EventFullDto> toEventFullDto(List<Event> eventList);

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
    @Mapping(target = "stateAction", ignore = true)
    UpdateUserEventDto toUpdateUserEventDto(Event event);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "locationLat", target = "location.lat")
    @Mapping(source = "locationLon", target = "location.lon")
    @Mapping(target = "stateAction", ignore = true)
    UpdateAdminEventDto toUpdateAdminEventDto(Event event);
}