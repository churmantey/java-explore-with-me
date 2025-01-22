package ru.practicum.ewm.main.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO for {@link ru.practicum.ewm.main.user.User}
 */
@Data
public class UserDto {

    private final Long id;

    @NotBlank
    private final String name;

    @Email
    @NotBlank
    private final String email;
}