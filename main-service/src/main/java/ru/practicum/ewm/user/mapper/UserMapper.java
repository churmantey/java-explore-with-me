package ru.practicum.ewm.user.mapper;

import org.mapstruct.*;
import ru.practicum.ewm.user.User;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserDto userDto);

    UserDto toDto(User user);

    List<UserDto> toDtoList(List<User> userList);

    UserShortDto toShortDto(User user);

    List<UserShortDto> toShortDtoList(List<User> userList);
}