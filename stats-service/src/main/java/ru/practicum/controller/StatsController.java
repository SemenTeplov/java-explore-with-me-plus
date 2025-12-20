package ru.practicum.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import ru.practicum.constant.Messages;
import ru.practicum.constant.Values;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.service.StatsService;
import ru.practicum.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> saveHit(@Valid @RequestBody EndpointHitDto request) {
        log.info(Messages.POST_HIT_REQUEST, request.getApp(), request.getUri(),request.getIp());

        statsService.saveHit(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(Messages.INFORMATION_ADDED);
    }

    @GetMapping("/stats")
    public ResponseEntity<List<ViewStats>> getStats(
            @RequestParam @DateTimeFormat(pattern = Values.DATE_TIME_PATTERN)
            LocalDateTime start,

            @RequestParam @DateTimeFormat(pattern = Values.DATE_TIME_PATTERN)
            LocalDateTime end,

            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {
        log.info(Messages.GET_STATS_REQUEST,start, end, uris, unique);

        List<ViewStats> stats = statsService.getStats(start, end, uris, unique);

        return ResponseEntity.status(HttpStatus.OK).body(stats);
    }
}
