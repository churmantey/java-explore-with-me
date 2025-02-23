package ru.practicum.ewm.location.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO for {@link ru.practicum.ewm.location.Location}
 */
@AllArgsConstructor
@Getter
public class NewLocationDto {
    @Size(min = 3, max = 50)
    @NotBlank
    private String title;

    @Size(max = 2000)
    private String description;

    @NotNull
    @Min(-90)
    @Max(90)
    private Float latitude;

    @NotNull
    @Min(-180)
    @Max(180)
    private Float longitude;
}