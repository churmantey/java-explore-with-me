package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
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

    @NotNull
    @Size(max = 120)
    private String title;

    @NotNull
    @Size(max = 2000)
    private String annotation;

    @NotNull
    @Size(max = 7000)
    private String description;

    private LocationDto location;

    private boolean paid;

    private int participantLimit;

    private boolean requestModeration = true;

    @NotNull
    @JsonFormat(pattern = HitDto.DATE_FORMAT_PATTERN)
    private LocalDateTime eventDate;
}