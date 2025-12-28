package main.java.ru.practicum.categories;

import lombok.experimental.UtilityClass;
import main.java.ru.practicum.categories.dto.NewCategoryDto;
import ru.practicum.openapi.model.CategoryDto;

@UtilityClass
public class CategoryMapper {
    public Category toCategory(NewCategoryDto newCategoryDto) {
        return new Category(newCategoryDto.getName());
    }

    public CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
