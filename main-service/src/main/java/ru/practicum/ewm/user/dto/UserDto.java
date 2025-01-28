package ru.practicum.ewm.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.practicum.ewm.user.User;

/**
 * DTO for {@link User}
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