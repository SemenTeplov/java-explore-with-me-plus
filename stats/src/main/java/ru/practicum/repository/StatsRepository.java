package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.practicum.dto.ViewStats;
import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    @Query("SELECT new ru.practicum.dto.ViewStats(" +
            "h.app, h.uri, " +
            "CASE WHEN :unique = true THEN COUNT(DISTINCT h.ip) ELSE COUNT(h.ip) END) " +
            "FROM EndpointHit h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "AND (:uris IS NULL OR :uris IS EMPTY OR h.uri IN :uris) " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY " +
            "CASE WHEN :unique = true THEN COUNT(DISTINCT h.ip) ELSE COUNT(h.ip) END DESC")
    List<ViewStats> getStats(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris,
            @Param("unique") boolean unique);
}
