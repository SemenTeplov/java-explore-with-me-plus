package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.practicum.constant.Messages;
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
        log.info("POST /hit: app={}, uri={}, ip={}",
                request.getApp(), request.getUri(),request.getIp());
        statsService.saveHit(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(Messages.INFORMATION_ADDED);
    }

    @GetMapping("/stats")
    public ResponseEntity<ViewStats> getStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime start,

            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime end,

            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("GET /stats: start={}, end={}, uris={}, unique={}",
                start, end, uris, unique);

        List<ViewStats> stats = statsService.getStats(start, end, uris, unique);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
