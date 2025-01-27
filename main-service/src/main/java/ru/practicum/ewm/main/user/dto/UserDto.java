package ru.practicum.ewm.main.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO for {@link ru.practicum.ewm.main.user.User}
 */
@Data
public class UserDto {

    private final Long id;

    @NotBlank
    @Size(min = 2, max = 250)
    private final String name;

    @Email
    @NotBlank
    @Size(min = 6, max = 254)
    private final String email;
}