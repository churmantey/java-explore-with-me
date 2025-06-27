package ru.practicum.ewm.location.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import ru.practicum.ewm.location.LocationState;

/**
 * DTO for {@link ru.practicum.ewm.location.Location}
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateLocationDto {
    @Size(min = 3, max = 50)
    private String title;

    @Size(max = 2000)
    private String description;

    @Range(min = -90, max = 90)
    private Float latitude;

    @Range(min = -180, max = 180)
    private Float longitude;

    private LocationState state;
}