package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper mapper;

    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if (!categoryRepository.existsByNameIgnoreCase(categoryDto.getName())) {
            return mapper.toCategoryDto(categoryRepository.save(mapper.toEntity(categoryDto)));
        } else {
            throw new ValidationException("Category " + categoryDto.getName() + " already exists.");
        }
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        if (!categoryRepository.existsByNameIgnoreCaseAndIdNot(categoryDto.getName(), categoryId)) {
            Category category = categoryRepository.getExistingCategory(categoryId);
            category.setName(categoryDto.getName());
            return mapper.toCategoryDto(category);
        } else {
            throw new ValidationException("Category " + categoryDto.getName() + " already exists.");
        }
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            if (eventRepository.existsByCategoryId(categoryId)) {
                throw new ValidationException("Category with id=" + categoryId + " has events in it " +
                        "and can not be deleted");
            }
            categoryRepository.deleteById(categoryId);
        } else {
            throw new NotFoundException("Category with id " + categoryId + " not found");
        }
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        return mapper.toCategoryDtoList(categoryRepository.findCategories(from, size));
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        return mapper.toCategoryDto(categoryRepository.getExistingCategory(categoryId));
    }
}
