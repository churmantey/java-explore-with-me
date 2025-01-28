package ru.practicum.ewm.category.service;

import ru.practicum.ewm.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);

    CategoryDto getCategory(Long id);

    List<CategoryDto> getCategories(int from, int size);

    void deleteCategory(Long categoryId);

}
