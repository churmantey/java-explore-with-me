package ru.practicum.ewm.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.location.Location;
import ru.practicum.ewm.location.LocationState;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByState(LocationState state);

    Optional<Location> findByIdAndState(Long id, LocationState state);

    @Query("select lc from Location lc where lc.state=:state order by lc.id offset :from FETCH FIRST :size ROWS ONLY")
    List<Location> findPortionByState(@Param("state") LocationState state, @Param("from") int from,
                                      @Param("size") int size);

    @Query("select lc from Location lc order by lc.id offset :from FETCH FIRST :size ROWS ONLY")
    List<Location> findPortion(@Param("from") int from, @Param("size") int size);

    default Location getExistingLocation (Long locId) {
        Optional<Location> optLocation = findById(locId);
        return optLocation.orElseThrow(() -> new NotFoundException("Location not found, id=" + locId));
    }

}