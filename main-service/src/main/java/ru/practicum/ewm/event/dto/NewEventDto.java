package ru.practicum.ewm.event.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.event.Event;

/**
 * DTO for {@link Event}
 */

@AllArgsConstructor
@Getter
@Setter
public class NewEventDto {
    @NotNull
    CategoryDto category;
    @NotNull
    @Size(max = 120)
    String title;
    @NotNull
    @Size(max = 2000)
    String annotation;
    @NotNull
    @Size(max = 7000)
    String description;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
}