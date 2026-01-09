package main.java.ru.practicum.persistence.repository;

import main.java.ru.practicum.persistence.entity.CompilationToEvents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompilationToEventsRepository extends JpaRepository<CompilationToEvents, Long> {
    @Query(nativeQuery = true, value = """
            SELECT *
            FROM compilation_to_events
            WHERE compilation_to_events.compilation_id = ANY(:ids)
            """)
    List<CompilationToEvents> getCompilationToEventsByIdsCompilation(@Param("ids") Long[] ids);

    @Query(nativeQuery = true, value = """
            DELETE
            FROM compilation_to_events
            WHERE compilation_to_events.compilation_id = :compId
            """)
    void deleteCompilationToEventsByIdsCompilation(@Param("compId") Long compId);
}
