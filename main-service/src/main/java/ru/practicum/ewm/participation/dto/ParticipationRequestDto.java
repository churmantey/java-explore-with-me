package ru.practicum.ewm.participation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import ru.practicum.ewm.participation.RequestStates;

import java.time.LocalDateTime;

/**
 * DTO for {@link ru.practicum.ewm.participation.ParticipationRequest}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {
    private Long id;

    @NotNull
    private Long event;

    @NotNull
    private Long requestor;

    private LocalDateTime created;
    private RequestStates state;
}