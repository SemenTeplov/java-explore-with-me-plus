package main.java.ru.practicum.conroller;

import constant.Messages;

import controller.StatsController;

import dto.EndpointHitDto;
import dto.ViewStats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsControllerImpl implements StatsController {
    private final StatsService statsService;

    @Override
    public ResponseEntity<String> saveHit(EndpointHitDto request) {
        log.info(Messages.POST_HIT_REQUEST, request.getApp(), request.getUri(),request.getIp());

        statsService.saveHit(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(Messages.INFORMATION_ADDED);
    }

    @Override
    public ResponseEntity<List<ViewStats>> getStats(
            LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        log.info(Messages.GET_STATS_REQUEST,start, end, uris, unique);

        List<ViewStats> stats = statsService.getStats(start, end, uris, unique);

        return ResponseEntity.status(HttpStatus.OK).body(stats);
    }
}
