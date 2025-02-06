package ru.practicum.ewm.event.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.time.LocalDateTime;

/**
 * DTO for {@link ru.practicum.ewm.event.Event}
 */
@AllArgsConstructor
@Getter
public class EventShortDto {

    private final Long id;

    @NotNull
    private final CategoryDto category;

    @NotNull
    private final UserShortDto initiator;

    @NotNull
    @Size(max = 120)
    private final String title;

    @NotNull
    @Size(max = 2000)
    private final String annotation;

    @NotNull
    private final LocalDateTime eventDate;

    private final Boolean paid;

    private final Long views;

}