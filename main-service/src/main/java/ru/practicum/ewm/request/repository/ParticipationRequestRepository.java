package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.request.ParticipationRequest;

import java.util.List;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    boolean existsByRequestor_IdAndEvent_Id(Long userId, Long eventId);

    List<ParticipationRequest> findByEvent_IdOrderByIdAsc(Long eventId);

    List<ParticipationRequest> findByRequestor_IdOrderByIdAsc(Long userId);


}