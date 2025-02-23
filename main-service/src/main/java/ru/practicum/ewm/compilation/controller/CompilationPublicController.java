package ru.practicum.ewm.compilation.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDto> getCompilations(@RequestParam(name = "pinned", required = false) Boolean pinned,
                                                @RequestParam(name = "from", defaultValue = "0") int from,
                                                @RequestParam(name = "size", defaultValue = "10") int size,
                                                HttpServletRequest request) {
        log.info("GET compilations pinned={}, from={}, size={}", pinned, from, size);
        statsLogger.logIPAndPath(request);
        return service.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto getCompilation(@PathVariable(name = "compId") Long compId, HttpServletRequest request) {
        log.info("GET compilation by id={}", compId);
        statsLogger.logIPAndPath(request);
        return service.getCompilation(compId);
    }

}

