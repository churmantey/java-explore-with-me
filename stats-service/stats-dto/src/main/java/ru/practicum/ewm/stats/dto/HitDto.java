package ru.practicum.ewm.stats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HitDto {

    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @NotBlank
    private String app;

    @NotBlank
    private String uri;

    @NotBlank
    @Pattern(regexp = "((\\d{1,3}\\.){3}\\d{1,3})|(([0-9a-f]{1,4}:){7}[0-9a-f]{1,4})", message = "Incorrect ip address")
    private String ip;

    @PastOrPresent
    @JsonFormat(pattern = DATE_FORMAT_PATTERN)
    private LocalDateTime timestamp;

}
