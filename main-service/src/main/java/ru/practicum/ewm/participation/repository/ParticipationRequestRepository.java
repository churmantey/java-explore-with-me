package ru.practicum.ewm.participation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.participation.ParticipationRequest;

import java.util.List;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findByEvent_IdOrderByIdAsc(Long id);

}