package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StatsService {
    private final StatsRepository statsRepository;

    public void saveHit(EndpointHitDto hitDto) {
        try {
            EndpointHit hit = EndpointHit.builder()
                    .app(hitDto.getApp())
                    .uri(hitDto.getUri())
                    .ip(hitDto.getIp())
                    .timestamp(hitDto.getTimestamp())
                    .build();
            statsRepository.save(hit);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при сохранении hit", e
            );
        }
    }

    @Transactional(readOnly = true)
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (start.isAfter(end)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Дата От может быть только после даты До"
            );
        }
        return statsRepository.getStats(start, end, uris, unique == null ? false : unique);
    }
}
