package ru.practicum.ewm.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.stats.model.Hit;

import java.util.List;

public interface StatsRepository extends JpaRepository<Hit, Long> {

    List<Hit> findAllByOrderByIdAsc();

}
