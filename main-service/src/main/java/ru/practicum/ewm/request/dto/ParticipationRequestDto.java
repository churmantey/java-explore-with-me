package ru.practicum.ewm.request.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.request.RequestStates;

import java.time.LocalDateTime;

/**
 * DTO for {@link ru.practicum.ewm.request.ParticipationRequest}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {

    private Long id;

    @NotNull
    @Positive
    private Long event;

    @NotNull
    @Positive
    private Long requester;

    private LocalDateTime created;
    private RequestStates status;
}