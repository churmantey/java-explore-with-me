package ru.practicum.ewm.stats.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.stats.dto.StatsDto;
import ru.practicum.ewm.stats.model.Stats;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StatsMapper {
    StatsDto statsToDto(Stats stats);

    List<StatsDto> statsListToDto(List<Stats> statsList);
}
