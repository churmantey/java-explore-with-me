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

    private  Long id;

    @NotBlank
    @Length(min = 1, max = 50)
    private  String name;
}