package ru.practicum.ewm.request.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.service.ParticipationRequestService;
import ru.practicum.ewm.statslogger.StatsLogger;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class ParticipationRequestPrivateController {

    private final ParticipationRequestService requestService;
    private final StatsLogger statsLogger;

    @GetMapping
    List<ParticipationRequestDto> getUserRequests(@PathVariable("userId") Long userId, HttpServletRequest request) {
        log.info("GET user requests , userId={}", userId);
        statsLogger.logIPAndPath(request);
        return requestService.getUserRequests(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ParticipationRequestDto createRequest(@PathVariable("userId") Long userId, @RequestParam("eventId") Long eventId,
                                          HttpServletRequest request) {
        log.info("POST new user requests , userId={}, eventId={}", userId, eventId);
        statsLogger.logIPAndPath(request);
        return requestService.createRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    ParticipationRequestDto cancelRequest(@PathVariable("userId") Long userId,
                                          @PathVariable("requestId") Long requestId,
                                          HttpServletRequest request) {
        log.info("PATCH cancel user requests , userId={}, requestId={}", userId, requestId);
        statsLogger.logIPAndPath(request);
        return requestService.cancelRequest(userId, requestId);
    }
}

