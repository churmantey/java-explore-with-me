package ru.practicum.ewm.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {

    List<Event> findByCategory_IdOrderByIdAsc(Long id);

    @Query(value = """
            select *
            from events as e
            where initiator_id=:userId
            order by e.id offset :from limit :size
            """, nativeQuery = true)
    List<Event> findByInitiator_IdOrderByIdAsc(@Param("userId") Long id,
                                               @Param("from") int from,
                                               @Param("size") int size);

    default Event getExistingEvent(Long eventId) {
        Optional<Event> optEvent = findById(eventId);
        return optEvent.orElseThrow(() -> new NotFoundException("Event not found, id=" + eventId));
    }

}