package ru.practicum.ewm.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.stats.dto.HitDto;
import ru.practicum.ewm.stats.dto.StatsDto;
import ru.practicum.ewm.stats.mapper.HitMapper;
import ru.practicum.ewm.stats.mapper.StatsMapper;
import ru.practicum.ewm.stats.model.Stats;
import ru.practicum.ewm.stats.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final HitMapper hitMapper;
    private final StatsMapper statsMapper;
    private final StatsRepository statsRepository;

    @Override
    @Transactional
    public HitDto addHit(HitDto hitDto) {
        return hitMapper.hitToDto(statsRepository.save(hitMapper.dtoToHit(hitDto)));
    }

    @Override
    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        List<Stats> result;
        if (uris == null || uris.isEmpty()) {
            if (unique) {
                result = statsRepository.getAllUniqueHits(start, end);
            } else {
                result = statsRepository.getAllHits(start, end);
            }
        } else if (unique) {
            result = statsRepository.getAllUniqueHitsByUris(start, end, uris);
        } else {
            result = statsRepository.getAllHitsByUris(start, end, uris);
        }
        return statsMapper.statsListToDto(result);
    }
}
