package ru.practicum.ewm.compilation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.event.dto.EventShortDto;

import java.util.Set;

/**
 * DTO for {@link ru.practicum.ewm.compilation.Compilation}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompilationDto {
    private Long id;
    private Set<EventShortDto> events;
    private boolean pinned;
    private String title;
}