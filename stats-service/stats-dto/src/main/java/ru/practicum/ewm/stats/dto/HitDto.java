package ru.practicum.ewm.stats.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HitDto {

    @NotBlank
    private String app;

    @NotBlank
    private String uri;

    @NotBlank
    @Pattern(regexp = "(\\d{1,3}\\.){3}\\d{1,3}", message = "Incorrect ip address")
    private String ip;

    @PastOrPresent
    private LocalDateTime timestamp;

}
