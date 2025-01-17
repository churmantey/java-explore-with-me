package ru.practicum.emw.stats;

import ru.practicum.ewm.stats.dto.HitDto;
import ru.practicum.ewm.stats.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsClient {

    HitDto addHit (String app, String uri, String ip, LocalDateTime timestamp);

    List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);

}
