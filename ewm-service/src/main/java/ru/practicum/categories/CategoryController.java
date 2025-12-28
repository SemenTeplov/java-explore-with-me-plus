package main.java.ru.practicum.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.openapi.api.CategoryApi;
import ru.practicum.openapi.model.CategoryDto;
import ru.practicum.openapi.model.NewCategoryDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController implements CategoryApi {
    private final CategoryService categoryService;


    @Override
    public ResponseEntity<CategoryDto> _addCategory(NewCategoryDto newCategoryDto) {
        log.info("POST /admin/categories with request: {}", newCategoryDto);
        CategoryDto categoryDto = categoryService.addCategory(newCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDto);
    }

    @Override
    public ResponseEntity<Void> _deleteCategory(Long catId) {
        log.info("DELETE /admin/categories/{}", catId);
        categoryService.deleteCategory(catId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<CategoryDto>> _getCategories(Integer from, Integer size) {
        log.info("GET /categories");
        List<CategoryDto> categories = categoryService.getCategories(from, size);
        return ResponseEntity.ok(categories);
    }

    @Override
    public ResponseEntity<CategoryDto> _getCategory(Long catId) {
        log.info("GET /categories/{}", catId);
        CategoryDto category = categoryService.getCategoryById(catId);
        return ResponseEntity.ok(category);
    }

    @Override
    public ResponseEntity<CategoryDto> _updateCategory(Long catId, CategoryDto categoryDto) {
        log.info("PATCH /admin/categories/{} with request: {}", catId, categoryDto);
        CategoryDto updatedCategory = categoryService.updateCategory(catId, categoryDto);
        return ResponseEntity.ok(updatedCategory);
    }
}
