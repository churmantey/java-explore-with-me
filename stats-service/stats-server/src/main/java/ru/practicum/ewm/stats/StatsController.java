package ru.practicum.ewm.stats;

import ru.practicum.ewm.stats.dto.HitDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/stats")
@RequiredArgsConstructor
public class StatsController {

    @PostMapping
    public HitDto addHit() {
        return null;
    }

}
