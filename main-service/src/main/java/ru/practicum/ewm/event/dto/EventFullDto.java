package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.event.EventStates;
import ru.practicum.ewm.stats.dto.HitDto;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.time.LocalDateTime;

/**
 * DTO for {@link ru.practicum.ewm.event.Event}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {

    private Long id;

    @NotNull
    private CategoryDto category;

    @NotNull
    private UserShortDto initiator;

    @NotNull
    @Size(max = 120)
    private String title;

    @NotNull
    @Size(max = 2000)
    private String annotation;

    @NotNull
    @Size(max = 7000)
    private String description;

    @NotNull
    @JsonFormat(pattern = HitDto.DATE_FORMAT_PATTERN)
    private LocalDateTime eventDate;

    private LocationDto location = new LocationDto();

    private Boolean paid;

    private Integer participantLimit;

    @NotNull
    @JsonFormat(pattern = HitDto.DATE_FORMAT_PATTERN)
    private LocalDateTime createdOn;

    @NotNull
    @JsonFormat(pattern = HitDto.DATE_FORMAT_PATTERN)
    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private EventStates state;

    private Long views;

    private long confirmedRequests;
}