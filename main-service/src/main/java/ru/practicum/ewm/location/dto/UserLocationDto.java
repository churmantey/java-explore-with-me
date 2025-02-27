package ru.practicum.ewm.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link ru.practicum.ewm.location.Location}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLocationDto {
    private Long id;
    private String title;
    private String description;
    private Float latitude;
    private Float longitude;
}