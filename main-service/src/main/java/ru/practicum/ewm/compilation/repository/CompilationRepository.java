package ru.practicum.ewm.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.compilation.Compilation;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.Optional;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    default Compilation getExistingCompilation (Long compilationId) {
        Optional<Compilation> optCompilation = findById(compilationId);
        return optCompilation.orElseThrow(() -> new NotFoundException("Compilation not found, id=" + compilationId));
    }

}