package ru.practicum.ewm.compilation.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.service.CompilationService;
import ru.practicum.ewm.statslogger.StatsLogger;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class CompilationPublicController {

    private final CompilationService service;
    private final StatsLogger statsLogger;

    @GetMapping
    public List<CompilationDto> getCompilations(HttpServletRequest request) {
        log.info("GET compilations");
        statsLogger.logIPAndPath(request);
        return service.getCompilations();
    }

    @GetMapping("/{compId}")
    public List<CompilationDto> getCompilation(@PathVariable(name = "compId") Long compId,  HttpServletRequest request) {
        log.info("GET compilation by id={}", compId);
        statsLogger.logIPAndPath(request);
        return service.getCompilation(compId);
    }

}

