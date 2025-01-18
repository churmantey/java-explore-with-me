package ru.practicum.emw.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.ewm.stats.dto.HitDto;
import ru.practicum.ewm.stats.dto.StatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class StatsClientImpl implements StatsClient {

    private final RestTemplate restTemplate;
    private static final String HIT_ENDPOINT = "/hit";
    private static final String STATS_ENDPOINT = "/stats";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String baseUrl;


    @Autowired
    public StatsClientImpl(@Value("${stats-server.url}") String statsServerUrl) {
        this.restTemplate = new RestTemplate();
        this.baseUrl = statsServerUrl;
    }

    @Override
    public HitDto addHit(String app, String uri, String ip, LocalDateTime timestamp) {
        return restTemplate.postForObject(baseUrl + HIT_ENDPOINT,
                new HitDto(app, uri, ip, timestamp),
                HitDto.class);
    }

    @Override
    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(STATS_ENDPOINT)
                .queryParam("start", start.format(dtf))
                .queryParam("end", end.format(dtf))
                .queryParam("uris", uris)
                .queryParam("unique", unique);

        String resultUrl = builder.build().toUriString();

        StatsDto[] statsArray = restTemplate.getForObject(baseUrl + resultUrl, StatsDto[].class);
        if (statsArray != null) {
            return Arrays.asList(statsArray);
        } else {
            return new ArrayList<>();
        }
    }
}
