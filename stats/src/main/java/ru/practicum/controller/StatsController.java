package ru.practicum.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.practicum.constant.Messages;
import ru.practicum.dto.StatRequestDto;
import ru.practicum.dto.StatsResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsController {
    @PostMapping("/hit")
    public ResponseEntity<String> addRequest(@RequestBody StatRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(Messages.INFORMATION_ADDED);
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponseDto> getRequests(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
