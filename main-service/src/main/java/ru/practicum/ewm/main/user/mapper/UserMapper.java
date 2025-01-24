package ru.practicum.ewm.main.user.mapper;

import org.mapstruct.*;
import ru.practicum.ewm.main.user.User;
import ru.practicum.ewm.main.user.dto.UserDto;
import ru.practicum.ewm.main.user.dto.UserShortDto;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserDto userDto);

    UserDto toDto(User user);

    List<UserDto> toDtoList(List<User> userList);

    UserShortDto toShortDto(User user);

    List<UserShortDto> toShortDtoList(List<User> userList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDto userDto, @MappingTarget User user);
}