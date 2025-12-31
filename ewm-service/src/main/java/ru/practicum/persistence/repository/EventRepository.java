package main.java.ru.practicum.persistence.repository;

import main.java.ru.practicum.persistence.entity.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(nativeQuery = true, value = """
            SELECT e.*
            FROM events e
            JOIN compilation_to_events ce ON ce.event_id = e.id
            WHERE ce.compilation_id = ANY(:ids)
            """)
    List<Event> getEventsByCompilationIds(@Param("ids") Long[] ids);
}
