package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.stats.dto.HitDto;

import java.time.LocalDateTime;

/**
 * DTO for {@link Event}
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {

    @NotNull
    private Long category;

    @NotBlank
    @Size(min = 3, max = 120)
    private String title;

    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;

    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;

    private LocationDto location;

    private boolean paid;

    @PositiveOrZero
    private int participantLimit;

    private boolean requestModeration = true;

    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = HitDto.DATE_FORMAT_PATTERN)
    private LocalDateTime eventDate;
}