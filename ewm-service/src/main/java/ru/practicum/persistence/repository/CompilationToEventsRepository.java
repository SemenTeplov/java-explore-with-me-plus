package main.java.ru.practicum.persistence.repository;

import main.java.ru.practicum.persistence.entity.CompilationToEvents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompilationToEventsRepository extends JpaRepository<CompilationToEvents, Long> {
    @Query(nativeQuery = true, value = """
            SELECT *
            FROM compilation_to_events
            WHERE compilation_id = ANY(:ids)
            """)
    List<CompilationToEvents> getCompilationToEventsByIdsCompilation(@Param("ids") Long[] ids);

    @Modifying
    @Query(nativeQuery = true, value = """
            DELETE
            FROM compilation_to_events AS ce
            USING compilations AS c
            WHERE ce.compilation_id = c.id AND ce.compilation_id = :compilationId
            """)
    void deleteCompilationToEventsByIdsCompilation(@Param("compilationId") Long compilationId);
}
