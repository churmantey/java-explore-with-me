package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    List<UserDto> getUsersByIds(List<Long> ids, int from, int size);

    List<UserDto> getUsers(int from, int size);

    void deleteUserById(Long userId);

}
