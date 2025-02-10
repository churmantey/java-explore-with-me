package ru.practicum.ewm.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.Event;

import java.util.List;

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

}