package ru.practicum.ewm.statslogger;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.StatsClient;
import ru.practicum.ewm.stats.StatsClientImpl;

import java.time.LocalDateTime;

@Slf4j
@Component
public class StatsLogger {

    private final StatsClient statsClient = new StatsClientImpl("http://localhost:9090");

    public void logIPAndPath(HttpServletRequest request) {
        log.info("client ip: {}", request.getRemoteAddr());
        log.info("endpoint path: {}", request.getRequestURI());
        statsClient.addHit("ewm-main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now());
    }
}
