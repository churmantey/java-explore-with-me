package ru.practicum.ewm.main.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.practicum.ewm.stats.dto.HitDto;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String status;
    private final String reason;
    private final String message;

    @JsonFormat(pattern = HitDto.DATE_FORMAT_PATTERN)
    private final LocalDateTime timestamp;
}
