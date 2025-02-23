package ru.practicum.ewm.request.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import java.util.List;

@Service
public interface ParticipationRequestService {

    List<ParticipationRequestDto> getUserRequests(Long userId);

    List<ParticipationRequestDto> getEventRequests(Long eventId);

    ParticipationRequestDto createRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);

}
