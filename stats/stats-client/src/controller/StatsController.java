package controller;

import jakarta.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import constant.Values;
import dto.EndpointHitDto;
import dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public interface StatsController {
    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> saveHit(@Valid @RequestBody EndpointHitDto request);

    @GetMapping("/stats")
    public ResponseEntity<List<ViewStats>> getStats(
            @RequestParam @DateTimeFormat(pattern = Values.DATE_TIME_PATTERN)
            LocalDateTime start,

            @RequestParam @DateTimeFormat(pattern = Values.DATE_TIME_PATTERN)
            LocalDateTime end,

            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique);
}
