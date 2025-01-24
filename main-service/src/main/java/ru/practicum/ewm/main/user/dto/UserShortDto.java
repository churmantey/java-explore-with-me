package ru.practicum.ewm.main.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for {@link ru.practicum.ewm.main.user.User}
 */
@Data
@AllArgsConstructor
public class UserShortDto {
    private final Long id;
    private final String name;
}