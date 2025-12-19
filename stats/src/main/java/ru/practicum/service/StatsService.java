package ru.practicum.service;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ru.practicum.constant.Messages;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStats;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final StatsRepository statsRepository;

    private final StatsMapper mapper;

    public void saveHit(EndpointHitDto hitDto) {
        statsRepository.save(mapper.endpointHitDtoToEndpointHit(hitDto));
    }

    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (start.isAfter(end)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, Messages.DATE_EXCEPTION);
        }

        return statsRepository.getStats(start, end, uris, unique);
    }
}
