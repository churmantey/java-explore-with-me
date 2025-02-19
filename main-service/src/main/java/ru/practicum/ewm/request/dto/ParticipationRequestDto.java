package ru.practicum.ewm.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.ewm.request.RequestStates;

import java.sql.Date;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime created;
    private RequestStates status;
}