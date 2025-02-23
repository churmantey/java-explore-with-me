package ru.practicum.ewm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.user.User;

/**
 * DTO for {@link User}
 */
@Data
@AllArgsConstructor
public class UserShortDto {
    private Long id;
    private String name;
}