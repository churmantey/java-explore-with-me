package ru.practicum.ewm.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    boolean existsByNameIgnoreCase(String name);

    @Query(value = """
            select *
            from categories as c
            order by c.id offset :from limit :size
            """, nativeQuery = true)
    List<Category> findCategories(@Param("from") int from,
                                  @Param("size") int size);

    default Category getExistingCategory(Long categoryId) {
        Optional<Category> optCategory = findById(categoryId);
        return optCategory.orElseThrow(() -> new NotFoundException("Category not found, id=" + categoryId));
    }


}