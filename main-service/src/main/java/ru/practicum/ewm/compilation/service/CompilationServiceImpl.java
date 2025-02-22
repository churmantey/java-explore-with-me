package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.Compilation;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ValidationException;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper mapper;

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        if (pinned == null) {
            return mapper.toCompilationDto(compilationRepository.findByOrderByIdAsc(from, size));
        } else {
            return mapper.toCompilationDto(compilationRepository.findByPinnedOrderByIdAsc(pinned, from, size));
        }
    }

    @Override
    public CompilationDto getCompilation(Long compId) {
        return mapper.toCompilationDto(compilationRepository.getExistingCompilation(compId));
    }

    @Override
    @Transactional
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        if (compilationRepository.existsByTitleIgnoreCase(newCompilationDto.getTitle())) {
            throw new ValidationException("Compilation with title already exists: " + newCompilationDto.getTitle());
        }
        Compilation comp = mapper.toEntity(newCompilationDto);
        populateEvents(comp.getEvents(), newCompilationDto.getEvents());
        return mapper.toCompilationDto(compilationRepository.save(comp));
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        if (compilationRepository.existsById(compId)) {
            compilationRepository.deleteById(compId);
        }
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateRequest) {
        Compilation comp = compilationRepository.getExistingCompilation(compId);
        if (updateRequest.getPinned() != null) {
            comp.setPinned(updateRequest.getPinned());
        }
        if (updateRequest.getTitle() != null && !updateRequest.getTitle().isBlank()) {
            comp.setTitle(updateRequest.getTitle());
        }
        if (updateRequest.getEvents() != null) {
            populateEvents(comp.getEvents(), updateRequest.getEvents());
        }
        return mapper.toCompilationDto(comp);
    }

    private void populateEvents(Set<Event> events, Set<Long> ids) {
        events.clear();
        if (ids != null) {
            for (Long eventId : ids) {
                events.add(eventRepository.getExistingEvent(eventId));
            }
        }
    }
}
