package ru.practicum.ewm.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.category.Category;

/**
 * DTO for {@link Category}
 */
@Data
@AllArgsConstructor
public class CategoryDto {

    private final Long id;

    @NotBlank
    @Length(min = 3, max = 50)
    private final String name;
}