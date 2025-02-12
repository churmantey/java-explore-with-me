package ru.practicum.ewm.participation.mapper;

import org.mapstruct.*;
import ru.practicum.ewm.participation.ParticipationRequest;
import ru.practicum.ewm.participation.dto.ParticipationRequestDto;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParticipationRequestMapper {
    @Mapping(source = "requestor", target = "requestor.id")
    @Mapping(source = "event", target = "event.id")
    ParticipationRequest toEntity(ParticipationRequestDto participationRequestDto);

    List<ParticipationRequestDto> toParticipationRequestDto(List<ParticipationRequest> participationRequest);

    @InheritInverseConfiguration(name = "toEntity")
    ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest);
}