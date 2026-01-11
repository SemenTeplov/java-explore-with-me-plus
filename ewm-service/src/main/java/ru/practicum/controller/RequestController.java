package main.java.ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.java.ru.practicum.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.openapi.api.RequestApi;
import ru.practicum.openapi.model.ParticipationRequestDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RequestController implements RequestApi {
    private final RequestService requestService;

    @Override
    public ResponseEntity<ParticipationRequestDto> _addParticipationRequest(Long userId, Long eventId) {
        log.info("");
        ParticipationRequestDto participationRequestDto = requestService.addRequest(userId, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(participationRequestDto);
    }

    @Override
    public ResponseEntity<ParticipationRequestDto> _cancelRequest(Long userId, Long requestId) {
        log.info("");
        requestService.cancelRequest(userId, requestId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ParticipationRequestDto>> _getUserRequests(Long userId) {
        log.info("");
        List<ParticipationRequestDto> requests = requestService.getRequestsByUser(userId);
        return ResponseEntity.ok(requests);
    }
}
