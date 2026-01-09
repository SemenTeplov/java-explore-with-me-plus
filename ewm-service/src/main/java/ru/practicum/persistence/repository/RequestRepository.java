package main.java.ru.practicum.persistence.repository;

import main.java.ru.practicum.persistence.entity.ParticipationRequest;
import main.java.ru.practicum.persistence.entity.Request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {

public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query(nativeQuery = true, value = """
            SELECT *
            FROM requests
            WHERE id = ANY(:ids)
            """)
    List<Request> getRequestsByIds(@Param("ids") Long[] ids);

    @Query(nativeQuery = true, value = """
            SELECT *
            FROM requests
            WHERE requester = :userId AND event = :eventId
            """)
    List<Request> getRequestsByUserIdAndEventId(@Param("userId") Long userId, @Param("eventId") Long eventId);
}
