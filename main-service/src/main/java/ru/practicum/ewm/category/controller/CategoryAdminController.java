package ru.practicum.ewm.category.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.service.CategoryService;
import ru.practicum.ewm.statslogger.StatsLogger;

@Slf4j
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryService categoryService;
    private final StatsLogger statsLogger;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(CategoryDto categoryDto, HttpServletRequest request) {
        log.info("POST new category {}", categoryDto);
        statsLogger.logIPAndPath(request);
        return categoryService.createCategory(categoryDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@PathVariable Long id, CategoryDto categoryDto, HttpServletRequest request) {
        log.info("PATCH category id={}, {}", id, categoryDto);
        statsLogger.logIPAndPath(request);
        return categoryService.updateCategory(id, categoryDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id, HttpServletRequest request) {
        log.info("DELETE category id={}", id);
        statsLogger.logIPAndPath(request);
        categoryService.deleteCategory(id);
    }
}
