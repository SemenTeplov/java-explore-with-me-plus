package main.java.ru.practicum.controller;

import org.springframework.http.ResponseEntity;
import ru.practicum.openapi.api.RequestApi;
import ru.practicum.openapi.model.ParticipationRequestDto;

import java.util.List;

public class RequestController implements RequestApi {
    @Override
    public ResponseEntity<ParticipationRequestDto> _addParticipationRequest(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ResponseEntity<ParticipationRequestDto> _cancelRequest(Long userId, Long requestId) {
        return null;
    }

    @Override
    public ResponseEntity<List<ParticipationRequestDto>> _getUserRequests(Long userId) {
        return null;
    }
}
