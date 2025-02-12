package ru.practicum.ewm.request.dto;

import jakarta.validation.constraints.NotNull;
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
    private Long event;

    @NotNull
    private Long requestor;

    private LocalDateTime created;
    private RequestStates state;
}