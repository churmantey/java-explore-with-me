package ru.practicum.ewm.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.event.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCategory_IdOrderByIdAsc(Long id);

    List<Event> findByInitiator_IdOrderByIdAsc(Long id);



}