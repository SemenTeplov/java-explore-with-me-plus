package main.java.ru.practicum.persistence.repository;

import main.java.ru.practicum.persistence.entity.Event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}
