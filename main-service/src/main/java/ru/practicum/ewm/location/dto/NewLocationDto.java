package ru.practicum.ewm.location.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

/**
 * DTO for {@link ru.practicum.ewm.location.Location}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewLocationDto {
    @NotBlank
    @Size(min = 3, max = 50)
    private String title;

    @Size(max = 2000)
    private String description;

    @NotNull
    @Range(min = -90, max = 90)
    private Float latitude;

    @NotNull
    @Range(min = -180, max = 180)
    private Float longitude;
}