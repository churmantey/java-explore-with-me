package ru.practicum.ewm.participation.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.participation.dto.ParticipationRequestDto;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
public class ParticipationRequestPrivateController {

    @GetMapping
    List<ParticipationRequestDto> getUserRequests (@PathVariable("userId") Long userId, HttpServletRequest request) {
        return null;
    }

    @PostMapping
    ParticipationRequestDto createRequest(@PathVariable("userId") Long userId, HttpServletRequest request) {
        return null;
    }

    @PatchMapping("/{requestId}/cancel")
    ParticipationRequestDto cancelRequest(@PathVariable("userId") Long userId,
                                          @PathVariable("requestId") Long requestId,
                                          HttpServletRequest request) {
        return null;
    }
}

