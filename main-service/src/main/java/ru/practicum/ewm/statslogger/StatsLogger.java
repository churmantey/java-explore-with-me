package ru.practicum.ewm.statslogger;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.StatsClient;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatsLogger {

    private final static String SERVICE_NAME = "ewm-main-service";
    private final StatsClient statsClient;

    public void logIPAndPath(HttpServletRequest request) {
        log.info("client ip: {}", request.getRemoteAddr());
        log.info("endpoint path: {}", request.getRequestURI());
        statsClient.addHit(SERVICE_NAME,
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now());
    }
}
