package ru.practicum.ewm.event.mapper;

import org.mapstruct.*;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdateEventDto;
import ru.practicum.ewm.user.mapper.UserMapper;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CategoryMapper.class, UserMapper.class})
public interface EventMapper {

    Event toEntity(EventFullDto eventFullDto);

    Event toEntity(EventShortDto eventShortDto);

    @Mapping(source = "category", target = "category.id")
    Event toEntity(NewEventDto newEventDto);

    EventFullDto toEventFullDto(Event event);

    EventShortDto toEventShortDto(Event event);

    List<EventShortDto> toEventShortDtoList(List<Event> eventList);

    @AfterMapping
    default void setDtoLocations(Event event, @MappingTarget EventFullDto fullDto) {
        fullDto.getLocation().setLat(event.getLocationLat());
        fullDto.getLocation().setLon(event.getLocationLon());
    }

    @AfterMapping
    default void setEventLocations(@MappingTarget Event event, EventFullDto fullDto) {
        if (fullDto.getLocation() != null) {
            event.setLocationLat(fullDto.getLocation().getLat());
            event.setLocationLon(fullDto.getLocation().getLon());
        }
    }

    @AfterMapping
    default void setEventLocations(@MappingTarget Event event, NewEventDto newDto) {
        if (newDto.getLocation() != null) {
            event.setLocationLat(newDto.getLocation().getLat());
            event.setLocationLon(newDto.getLocation().getLon());
        }
    }

    @Mapping(source = "categoryId", target = "category.id")
    Event toEntity(UpdateEventDto updateEventDto);

    @Mapping(source = "category.id", target = "categoryId")
    UpdateEventDto toUpdateEventDto(Event event);

}