package ru.practicum.ewm.stats.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.stats.dto.HitDto;
import ru.practicum.ewm.stats.dto.StatsDto;
import ru.practicum.ewm.stats.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public HitDto addHit(@Valid @RequestBody HitDto hitDto) {
        log.info("POST a hit {}", hitDto);
        return statsService.addHit(hitDto);
    }

    @GetMapping("/stats")
    public List<StatsDto> getStats(@RequestParam @DateTimeFormat(pattern = HitDto.DATE_FORMAT_PATTERN) LocalDateTime start,
                                   @RequestParam @DateTimeFormat(pattern = HitDto.DATE_FORMAT_PATTERN) LocalDateTime end,
                                   @RequestParam(defaultValue = "") List<String> uris,
                                   @RequestParam(defaultValue = "false") boolean unique) {
        log.info("GET stat start={}, end={}, uris={}, unique={} ", start, end, uris, unique);
        return statsService.getStats(start, end, uris, unique);
    }

}
