package ru.practicum.ewm.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.compilation.Compilation;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    @Query("select c from Compilation c order by c.id offset :from FETCH FIRST :size ROWS ONLY")
    List<Compilation> findByOrderByIdAsc(@Param("from") int from, @Param("size") int size);

    @Query("select c from Compilation c where c.pinned=:pinned order by c.id offset :from FETCH FIRST :size ROWS ONLY")
    List<Compilation> findByPinnedOrderByIdAsc(@Param("pinned") boolean pinned, @Param("from") int from,
                                               @Param("size") int size);

    boolean existsByTitleIgnoreCase(String title);

    default Compilation getExistingCompilation(Long compilationId) {
        Optional<Compilation> optCompilation = findById(compilationId);
        return optCompilation.orElseThrow(() -> new NotFoundException("Compilation not found, id=" + compilationId));
    }
}