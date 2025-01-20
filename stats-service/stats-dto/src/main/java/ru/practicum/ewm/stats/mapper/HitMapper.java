package ru.practicum.ewm.stats.mapper;


import org.mapstruct.Mapper;
import ru.practicum.ewm.stats.dto.HitDto;
import ru.practicum.ewm.stats.model.Hit;

@Mapper(componentModel = "spring")
public interface HitMapper {

    HitDto hitToDto(Hit hit);

    Hit dtoToHit(HitDto hitDto);

}
