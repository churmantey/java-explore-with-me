package ru.practicum.ewm.compilation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.compilation.Compilation;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.mapper.EventMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {EventMapper.class})
public interface CompilationMapper {

    Compilation toEntity(CompilationDto compilationDto);

    @Mapping(target = "events", expression = "java(eventIdsToEvents(newCompilationDto.getEvents()))")
    Compilation toEntity(NewCompilationDto newCompilationDto);

    @Mapping(target = "events", expression = "java(eventIdsToEvents(updateCompilationRequest.getEvents()))")
    Compilation toEntity(UpdateCompilationRequest updateCompilationRequest);

    CompilationDto toCompilationDto(Compilation compilation);

    List<CompilationDto> toCompilationDto(List<Compilation> compilation);

    @Mapping(target = "events", expression = "java(eventsToEventIds(compilation.getEvents()))")
    NewCompilationDto toNewCompilationDto(Compilation compilation);

    @Mapping(target = "events", expression = "java(eventsToEventIds(compilation.getEvents()))")
    UpdateCompilationRequest toUpdateCompilationRequest(Compilation compilation);

    default Set<Long> eventsToEventIds(Set<Event> events) {
        return events.stream().map(Event::getId).collect(Collectors.toSet());
    }

    default Set<Event> eventIdsToEvents(Set<Long> eventIds) {
        if (eventIds == null) return null;
        return eventIds.stream()
                .map(eventId -> {
                    Event ev = new Event();
                    ev.setId(eventId);
                    return ev;})
                .collect(Collectors.toSet());
    }

}