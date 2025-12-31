package main.java.ru.practicum.mapper;

import main.java.ru.practicum.persistence.entity.Category;

import org.mapstruct.Mapper;

import ru.practicum.openapi.model.CategoryDto;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto categoryToCategoryDto(Category category);
}
