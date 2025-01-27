package ru.practicum.ewm.main.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.category.Category;
import ru.practicum.ewm.main.category.dto.CategoryDto;
import ru.practicum.ewm.main.category.mapper.CategoryMapper;
import ru.practicum.ewm.main.category.repository.CategoryRepository;
import ru.practicum.ewm.main.exception.NotFoundException;
import ru.practicum.ewm.main.exception.ValidationException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository storage;
    private final CategoryMapper mapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if (!storage.existsByNameIgnoreCase(categoryDto.getName())) {
            return mapper.toCategoryDto(storage.save(mapper.toEntity(categoryDto)));
        } else {
            throw new ValidationException("Category " + categoryDto.getName() + " already exists.");
        }
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        if (!storage.existsByNameIgnoreCaseAndIdNot(categoryDto.getName(), categoryId)) {
            Optional<Category> optCategory = storage.findById(categoryId);
            if (optCategory.isPresent()) {
                Category category = optCategory.get();
                category.setName(categoryDto.getName());
                return mapper.toCategoryDto(category);
            } else {
                throw new NotFoundException("Category with id " + categoryId + " not found");
            }
        } else {
            throw new ValidationException("Category " + categoryDto.getName() + " already exists.");
        }
    }

    @Override
    public void deleteCategory(Long categoryId) {
        if (storage.existsById(categoryId)) {
            storage.deleteById(categoryId);
        } else {
            throw new NotFoundException("Category with id " + categoryId + " not found");
        }
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        return List.of();
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Optional<Category> optCategory = storage.findById(categoryId);
        if (optCategory.isPresent()) {
            return mapper.toCategoryDto(optCategory.get());
        } else {
            throw new NotFoundException("Category with id " + categoryId + " not found");
        }
    }
}
