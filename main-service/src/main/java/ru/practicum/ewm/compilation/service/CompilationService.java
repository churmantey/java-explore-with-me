package ru.practicum.ewm.compilation.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationRequest;

import java.util.List;

@Service
public interface CompilationService {

    List<CompilationDto> getCompilations();

    List<CompilationDto> getCompilation(Long compId);

    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    CompilationDto deleteCompilation(Long compId);

    CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateRequest);

}
