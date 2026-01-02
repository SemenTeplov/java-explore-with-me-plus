package main.java.ru.practicum.persistence.repository;

import main.java.ru.practicum.persistence.entity.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(nativeQuery = true, value = """
            SELECT *
            FROM events
            WHERE initiator = :userId AND id = :eventId
            """)
    Optional<Event> getEventByUserIdAndEventId(@Param("userId") Long userId, @Param("eventId") Long eventId);
}
