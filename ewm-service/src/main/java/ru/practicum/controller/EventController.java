package main.java.ru.practicum.controller;

import lombok.RequiredArgsConstructor;

import main.java.ru.practicum.service.EventService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ru.practicum.openapi.api.EventApi;
import ru.practicum.openapi.model.EventFullDto;
import ru.practicum.openapi.model.EventRequestStatusRequest;
import ru.practicum.openapi.model.EventRequestStatusUpdateResult;
import ru.practicum.openapi.model.EventShortDto;
import ru.practicum.openapi.model.NewEventDto;
import ru.practicum.openapi.model.ParticipationRequestDto;
import ru.practicum.openapi.model.UpdateEventAdminRequest;
import ru.practicum.openapi.model.UpdateEventUserRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController implements EventApi {
    private final EventService eventService;

    @Override
    public ResponseEntity<EventFullDto> _addEvent(Long userId, NewEventDto newEventDto) {
        return eventService.addEvent(userId, newEventDto);
    }

    @Override
    public ResponseEntity<EventRequestStatusUpdateResult> _changeRequestStatus(Long userId,
                                                                               Long eventId,
                                                                               EventRequestStatusRequest eventRequestStatusRequest) {
        return null;
    }

    @Override
    public ResponseEntity<EventFullDto> _getEvent(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<ParticipationRequestDto>> _getEventParticipants(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ResponseEntity<EventFullDto> _getEventUser(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ResponseEntity<List<EventShortDto>> _getEvents(String text,
                                                          List<Long> categories,
                                                          Boolean paid,
                                                          String rangeStart,
                                                          String rangeEnd,
                                                          Boolean onlyAvailable,
                                                          String sort,
                                                          Integer from,
                                                          Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<List<EventFullDto>> _getEventsAdmin(List<Long> users,
                                                              List<String> states,
                                                              List<Long> categories,
                                                              String rangeStart,
                                                              String rangeEnd,
                                                              Integer from,
                                                              Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<List<EventShortDto>> _getEventsUser(Long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<EventFullDto> _updateEvent(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        return null;
    }

    @Override
    public ResponseEntity<EventFullDto> _updateEventAdmin(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        return null;
    }
}
