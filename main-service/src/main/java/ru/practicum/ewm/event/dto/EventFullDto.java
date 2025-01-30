package ru.practicum.ewm.event.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.event.EventStates;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.time.LocalDateTime;

/**
 * DTO for {@link ru.practicum.ewm.event.Event}
 */
@AllArgsConstructor
@Getter
public class EventFullDto {
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
    @Size(max = 7000)
    private final String description;
    @NotNull
    private final LocalDateTime eventDate;
    private final Double locationLat;
    private final Double locationLon;
    private final Boolean paid;
    private final Integer participantLimit;
    @NotNull
    private final LocalDateTime createdOn;
    @NotNull
    private final LocalDateTime publishedOn;
    private final Boolean requestModeration;
    private final EventStates state;
}