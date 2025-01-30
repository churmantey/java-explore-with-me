package ru.practicum.ewm.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.user.mapper.UserMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CategoryMapper.class, UserMapper.class})
public interface EventMapper {
    Event toEntity(EventFullDto eventFullDto);

    EventFullDto toEventFullDto(Event event);

    Event toEntity(EventShortDto eventShortDto);

    EventShortDto toEventShortDto(Event event);
}