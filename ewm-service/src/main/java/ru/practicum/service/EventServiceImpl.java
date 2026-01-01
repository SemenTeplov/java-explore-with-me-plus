package main.java.ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import main.java.ru.practicum.constant.Exceptions;
import main.java.ru.practicum.constant.Messages;
import main.java.ru.practicum.exception.MismatchDateException;
import main.java.ru.practicum.exception.NotFoundException;
import main.java.ru.practicum.mapper.CategoryMapper;
import main.java.ru.practicum.mapper.EventMapper;
import main.java.ru.practicum.mapper.LocationMapper;
import main.java.ru.practicum.mapper.UserMapper;
import main.java.ru.practicum.persistence.entity.Category;
import main.java.ru.practicum.persistence.entity.Event;
import main.java.ru.practicum.persistence.entity.LocationEntity;
import main.java.ru.practicum.persistence.entity.User;
import main.java.ru.practicum.persistence.repository.CategoryRepository;
import main.java.ru.practicum.persistence.repository.EventRepository;
import main.java.ru.practicum.persistence.repository.LocationRepository;
import main.java.ru.practicum.persistence.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ru.practicum.openapi.model.EventFullDto;
import ru.practicum.openapi.model.EventRequestStatusRequest;
import ru.practicum.openapi.model.EventRequestStatusUpdateResult;
import ru.practicum.openapi.model.EventShortDto;
import ru.practicum.openapi.model.NewEventDto;
import ru.practicum.openapi.model.ParticipationRequestDto;
import ru.practicum.openapi.model.UpdateEventAdminRequest;
import ru.practicum.openapi.model.UpdateEventUserRequest;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    private final CategoryRepository categoryRepository;

    private final LocationRepository locationRepository;

    private final EventMapper eventMapper;

    private final UserMapper userMapper;

    private final CategoryMapper categoryMapper;

    private final LocationMapper locationMapper;

    @Override
    public ResponseEntity<EventFullDto> addEvent(Long userId, NewEventDto newEventDto) {
        log.info(Messages.MESSAGE_ADD_EVENT, userId, newEventDto);

        if (Duration.between(newEventDto.getEventDate(), OffsetDateTime.now()).toDays() < 2) {
            throw new MismatchDateException(Exceptions.EXCEPTION_DATE_MISMATCH);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.EXCEPTION_NOT_FOUND_USER, userId)));
        Category category = categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new NotFoundException(Exceptions.EXCEPTION_NOT_FOUND));
        LocationEntity location = locationRepository.save(locationMapper.locationToLocationEntity(newEventDto.getLocation()));

        Event event = eventMapper.newEventDtoToEvent(newEventDto);
        event.setInitiator(userId);
        event.setLocation(location.getId());
        event = eventRepository.save(event);

        EventFullDto eventFullDto = eventMapper.eventToEventFullDto(event);
        eventFullDto.setInitiator(userMapper.userToUserShortDto(user));
        eventFullDto.setCategory(categoryMapper.toCategoryDto(category));
        eventFullDto.setLocation(locationMapper.locationEntityToLocation(location));

        return ResponseEntity.status(HttpStatus.CREATED).body(eventFullDto);
    }

    @Override
    public ResponseEntity<EventRequestStatusUpdateResult> changeRequestStatus(Long userId, Long eventId, EventRequestStatusRequest eventRequestStatusRequest) {
        return null;
    }

    @Override
    public ResponseEntity<EventFullDto> getEvent(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<ParticipationRequestDto>> getEventParticipants(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ResponseEntity<EventFullDto> getEventUser(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ResponseEntity<List<EventShortDto>> getEvents(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<List<EventFullDto>> getEventsAdmin(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, Integer from, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<List<EventShortDto>> getEventsUser(Long userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<EventFullDto> updateEvent(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        return null;
    }

    @Override
    public ResponseEntity<EventFullDto> updateEventAdmin(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        return null;
    }
}
