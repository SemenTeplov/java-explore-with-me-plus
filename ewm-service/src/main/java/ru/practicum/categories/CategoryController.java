package main.java.ru.practicum.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.openapi.api.CategoryApi;
import ru.practicum.openapi.model.CategoryDto;

@RestController
@RequiredArgsConstructor

public class CategoryController implements CategoryApi {
    private final CategoryService categoryService;

    @Override
    public ResponseEntity<CategoryDto> _addCategory(Object body) {
        return categoryService.addCategory(body);
    }

    @Override
    public ResponseEntity<Void> _deleteCategory(Long catId) {
        return categoryService.deleteCategory(catId);
    }

    @Override
    public ResponseEntity<CategoryDto> _getCategories(Integer from, Integer size) {
        return categoryService.getCategories(from, size);
    }

    @Override
    public ResponseEntity<CategoryDto> _getCategory(Long catId) {
        return categoryService.getCategoryById(catId);
    }

    @Override
    public ResponseEntity<CategoryDto> _updateCategory(Long catId, CategoryDto categoryDto) {
        return categoryService.updateCategory(catId, categoryDto);
    }
}
