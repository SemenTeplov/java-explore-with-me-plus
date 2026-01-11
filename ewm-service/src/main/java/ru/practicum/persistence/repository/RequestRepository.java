package main.java.ru.practicum.persistence.repository;

import main.java.ru.practicum.persistence.entity.Request;

import main.java.ru.practicum.persistence.status.StatusRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    Boolean existsByRequesterIdAndEventId(Long userId, Long eventId);

    Long countByEventIdAndStatus(Long eventId, StatusRequest statusRequest);

    List<Request> findAllByEventIdAndIdInAndStatus(Long eventId, List<Long> requestIds, StatusRequest statusRequest);

    Request findByIdAndRequesterId(Long requestId, Long userId);

    List<Request> findAllByRequesterId(Long userId);
}
