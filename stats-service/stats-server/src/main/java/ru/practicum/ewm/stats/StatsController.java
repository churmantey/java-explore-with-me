package ru.practicum.ewm.stats;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.stats.dto.HitDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.ewm.stats.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @PostMapping("/hit")
    public HitDto addHit(@Valid @RequestBody HitDto hitDto) {
        log.info("POST a hit {}", hitDto);
        return statsService.addHit(hitDto);
    }

    @GetMapping("/stats")
    public List<StatsDto> getStats(@RequestParam(required = true) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                    @RequestParam(required = true) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                    @RequestParam(defaultValue = "") List<String> uris,
                                    @RequestParam(defaultValue = "false") boolean unique) {
        log.info("GET stat start={}, end={}, uris={}, unique={} ", start, end, uris, unique);
        return statsService.getStats(start, end, uris, unique);
    }

}
