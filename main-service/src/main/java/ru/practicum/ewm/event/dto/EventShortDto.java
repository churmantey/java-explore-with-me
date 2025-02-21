package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.stats.dto.HitDto;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.time.LocalDateTime;

/**
 * DTO for {@link ru.practicum.ewm.event.Event}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {

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
    @JsonFormat(pattern = HitDto.DATE_FORMAT_PATTERN)
    private LocalDateTime eventDate;

    private Boolean paid;

    private Long views;

    private long confirmedRequests;
}