package ru.practicum.ewm.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.stats.model.Hit;
import ru.practicum.ewm.stats.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Hit, Long> {

    // @Query("select it from Item as it where it.available " +
    //         "and lower(it.name) like lower(concat('%', :text,'%')) " +
    //         "or lower(it.description) like lower(concat('%', :text,'%')) order by it.id")
    // List<Item> findAvailableByNameOrDescription(@Param("text") String text);

    @Query("select new ru.practicum.ewm.stats.model.Stats(h.app, h.uri, COUNT(h.ip))" +
            " FROM Hit AS h where h.timestamp between :start and :end" +
            " GROUP BY h.app, h.uri ORDER BY COUNT(h.ip) DESC"
    )
    List<Stats> getAllHits(@Param("start") LocalDateTime start,
                           @Param("end") LocalDateTime end);

    @Query("select new ru.practicum.ewm.stats.model.Stats(h.app, h.uri, COUNT(DISTINCT h.ip))" +
            " FROM Hit AS h where h.timestamp between :start and :end" +
            " GROUP BY h.app, h.uri ORDER BY COUNT(DISTINCT h.ip) DESC"
    )
    List<Stats> getAllUniqueHits(@Param("start") LocalDateTime start,
                                 @Param("end") LocalDateTime end);

    @Query("select new ru.practicum.ewm.stats.model.Stats(h.app, h.uri, COUNT(DISTINCT h.ip))" +
            " FROM Hit AS h where (h.timestamp between :start and :end) and h.uri IN :uriList" +
            " GROUP BY h.app, h.uri ORDER BY COUNT(DISTINCT h.ip) DESC"
    )
    List<Stats> getAllUniqueHitsByUris(@Param("start") LocalDateTime start,
                                       @Param("end") LocalDateTime end,
                                       @Param("uriList") List<String> uris);

    @Query("select new ru.practicum.ewm.stats.model.Stats(h.app, h.uri, COUNT(h.ip))" +
            " FROM Hit AS h where (h.timestamp between :start and :end) and h.uri IN :uriList" +
            " GROUP BY h.app, h.uri ORDER BY COUNT(h.ip) DESC"
    )
    List<Stats> getAllHitsByUris(@Param("start") LocalDateTime start,
                                 @Param("end") LocalDateTime end,
                                 @Param("uriList") List<String> uris);

}
