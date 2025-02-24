package ru.practicum.ewm.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.location.LocationState;

/**
 * DTO for {@link ru.practicum.ewm.location.Location}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private String title;
    private String description;
    private Float latitude;
    private Float longitude;
    private Long id;
    private LocationState state;
}