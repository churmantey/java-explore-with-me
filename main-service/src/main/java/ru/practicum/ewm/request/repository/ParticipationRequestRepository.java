package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.ParticipationRequest;

import java.util.List;
import java.util.Optional;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    boolean existsByRequester_IdAndEvent_Id(Long userId, Long eventId);

    List<ParticipationRequest> findByEvent_IdOrderByIdAsc(Long eventId);

    List<ParticipationRequest> findByRequester_IdOrderByIdAsc(Long userId);

    default ParticipationRequest getExistingRequest(Long requestId) {
        Optional<ParticipationRequest> optRequest = findById(requestId);
        return optRequest.orElseThrow(() -> new NotFoundException("Request not found, id=" + requestId));
    }

}