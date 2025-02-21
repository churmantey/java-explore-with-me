package ru.practicum.ewm.category.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.service.CategoryService;
import ru.practicum.ewm.statslogger.StatsLogger;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryPublicController {

    private final CategoryService categoryService;
    private final StatsLogger statsLogger;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(name = "from", defaultValue = "0") int from,
                                           @RequestParam(name = "size", defaultValue = "10") int size,
                                           HttpServletRequest request) {
        log.info("GET categories from={}, size={}", from, size);
        statsLogger.logIPAndPath(request);
        return categoryService.getCategories(from, size);
    }

    @GetMapping("/{id}")
    public CategoryDto getCategory(@PathVariable("id") Long categoryId, HttpServletRequest request) {
        log.info("GET category id={}", categoryId);
        statsLogger.logIPAndPath(request);
        return categoryService.getCategory(categoryId);
    }

}
