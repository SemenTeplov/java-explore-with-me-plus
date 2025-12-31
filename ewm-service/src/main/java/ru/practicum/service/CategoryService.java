package main.java.ru.practicum.service;

import lombok.RequiredArgsConstructor;

import main.java.ru.practicum.constant.Messages;
import main.java.ru.practicum.mapper.CategoryMapper;
import main.java.ru.practicum.persistence.entity.Category;
import main.java.ru.practicum.persistence.repository.CategoryRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ru.practicum.openapi.model.CategoryDto;
import ru.practicum.openapi.model.NewCategoryDto;

import main.java.ru.practicum.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        return categoryMapper.toCategoryDto(categoryRepository.save(categoryMapper.toCategory(newCategoryDto)));
    }

    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = getCategory(categoryId);
        category.setName(categoryDto.getName());

        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    public List<CategoryDto> getCategories(Integer from, Integer size) {
        return categoryRepository.findAll(PageRequest.of(from / size, size)).stream()
                .map(categoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    public CategoryDto getCategoryById(Long categoryId) {
        return categoryMapper.toCategoryDto(getCategory(categoryId));
    }

    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException(String.format(Messages.MESSAGE_CATEGORY_NOT_FOUND, categoryId));
        }

        categoryRepository.deleteById(categoryId);
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() ->
                new NotFoundException(String.format(Messages.MESSAGE_CATEGORY_NOT_FOUND, categoryId)));
    }
}
