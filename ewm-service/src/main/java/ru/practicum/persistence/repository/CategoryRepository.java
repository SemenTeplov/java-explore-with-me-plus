package main.java.ru.practicum.persistence.repository;

import main.java.ru.practicum.persistence.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
