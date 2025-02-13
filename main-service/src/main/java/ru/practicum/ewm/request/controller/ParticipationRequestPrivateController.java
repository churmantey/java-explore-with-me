package ru.practicum.ewm.request.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.service.ParticipationRequestService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class ParticipationRequestPrivateController {

    private final ParticipationRequestService requestService;

    @GetMapping
    List<ParticipationRequestDto> getUserRequests (@PathVariable("userId") Long userId, HttpServletRequest request) {
        return requestService.getUserRequests(userId);
    }

    @PostMapping
    ParticipationRequestDto createRequest(@PathVariable("userId") Long userId, @RequestParam("eventId") Long eventId,
                                          HttpServletRequest request) {
        return requestService.createRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    ParticipationRequestDto cancelRequest(@PathVariable("userId") Long userId,
                                          @PathVariable("requestId") Long requestId,
                                          HttpServletRequest request) {
        return null;
    }
}

