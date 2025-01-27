package ru.practicum.ewm.main.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.exception.NotFoundException;
import ru.practicum.ewm.main.exception.ValidationException;
import ru.practicum.ewm.main.user.dto.UserDto;
import ru.practicum.ewm.main.user.mapper.UserMapper;
import ru.practicum.ewm.main.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        if (!userRepository.existsByEmailIgnoreCase(userDto.getEmail())) {
            return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
        } else {
            throw new ValidationException("Email " + userDto.getEmail() + " alredy in use");
        }
    }

    @Override
    public void deleteUserById(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new NotFoundException("User with id " + userId + " not found");
        }
    }

    @Override
    public List<UserDto> getUsers(int from, int size) {
        return userMapper.toDtoList(userRepository.findUsers(from, size));
    }

    @Override
    public List<UserDto> getUsersByIds(List<Long> ids, int from, int size) {
        return userMapper.toDtoList(userRepository.findAllByIds(ids, from, size));
    }
}
