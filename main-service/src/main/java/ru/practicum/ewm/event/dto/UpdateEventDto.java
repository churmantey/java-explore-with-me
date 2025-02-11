package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import ru.practicum.ewm.event.EventStates;

import java.time.LocalDateTime;

/**
 * DTO for {@link ru.practicum.ewm.event.Event}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventDto {
    Long categoryId;
    String title;
    String annotation;
    String description;
    LocalDateTime eventDate;
    Double locationLat;
    Double locationLon;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    EventStates state;
}