package ru.practicum.ewm.compilation.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.service.CompilationService;
import ru.practicum.ewm.statslogger.StatsLogger;

@Slf4j
@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class CompilationAdminController {

    private final CompilationService service;
    private final StatsLogger statsLogger;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto,
                                            HttpServletRequest request) {
        log.info("POST compilation, dto={}", newCompilationDto);
        statsLogger.logIPAndPath(request);
        return service.createCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId, HttpServletRequest request) {
        log.info("DELETE compilation, id={}", compId);
        statsLogger.logIPAndPath(request);
        service.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable Long compId,
                                            @Valid @RequestBody UpdateCompilationRequest updateRequest,
                                            HttpServletRequest request) {
        log.info("PATCH compilation, id={}, updateRequest={}", compId, updateRequest);
        statsLogger.logIPAndPath(request);
        return service.updateCompilation(compId, updateRequest);
    }

}

