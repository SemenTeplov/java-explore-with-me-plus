package main.java.ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import main.java.ru.practicum.constant.Exceptions;
import main.java.ru.practicum.constant.Messages;
import main.java.ru.practicum.exception.LimitRequestsExceededException;
import main.java.ru.practicum.exception.MismatchDateException;
import main.java.ru.practicum.exception.NotFoundException;
import main.java.ru.practicum.exception.NotRespondStatusException;
import main.java.ru.practicum.mapper.CategoryMapper;
import main.java.ru.practicum.mapper.EventMapper;
import main.java.ru.practicum.mapper.LocationMapper;
import main.java.ru.practicum.mapper.UserMapper;
import main.java.ru.practicum.persistence.entity.Category;
import main.java.ru.practicum.persistence.entity.Event;
import main.java.ru.practicum.persistence.entity.LocationEntity;
import main.java.ru.practicum.persistence.entity.Request;
import main.java.ru.practicum.persistence.entity.User;
import main.java.ru.practicum.persistence.repository.CategoryRepository;
import main.java.ru.practicum.persistence.repository.EventRepository;
import main.java.ru.practicum.persistence.repository.LocationRepository;
import main.java.ru.practicum.persistence.repository.RequestRepository;
import main.java.ru.practicum.persistence.repository.UserRepository;
import main.java.ru.practicum.persistence.status.StatusRequest;

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

    private final RequestRepository requestRepository;

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
        LocationEntity location = locationRepository
                .save(locationMapper.locationToLocationEntity(newEventDto.getLocation()));

        Event event = eventMapper.newEventDtoToEvent(newEventDto);
        event.setInitiator(userId);
        event.setLocation(location.getId());
        event = eventRepository.save(event);

        return ResponseEntity.status(HttpStatus.CREATED).body(getEventFullDto(event, user, category, location));
    }

    @Override
    public ResponseEntity<EventRequestStatusUpdateResult> changeRequestStatus
            (Long userId, Long eventId, EventRequestStatusRequest eventRequestStatusRequest) {
        log.info(Messages.MESSAGE_CHANGE_STATUS);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.EXCEPTION_NOT_FOUND_USER, userId)));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(Exceptions.EXCEPTION_NOT_FOUND));
        List<Request> requests = requestRepository
                .getRequestsByIds(eventRequestStatusRequest.getRequestIds().toArray(new Long[0]));

        if (event.getParticipantLimit() < requests.size()) {
            throw new LimitRequestsExceededException(Exceptions.EXCEPTION_LIMIT_EXCEEDED);
        }

        List<ParticipationRequestDto> participationRequestDto = requestRepository.saveAll(requests.stream().peek(r -> {
            if (!r.getStatus().equals(StatusRequest.PENDING.toString())) {
                throw new NotRespondStatusException(Messages.MESSAGE_NOT_RESPOND_STATUS);
            } else {
                r.setStatus(eventRequestStatusRequest.getStatus().toString());
            }
        }).toList()).stream().map(eventMapper::requestToParticipationRequestDto).toList();

        EventRequestStatusUpdateResult result;

        if (eventRequestStatusRequest.getStatus().equals(StatusRequest.CONFIRMED.toString())) {
            result = EventRequestStatusUpdateResult.builder().confirmedRequests(participationRequestDto).build();
        } else {
            result = EventRequestStatusUpdateResult.builder().rejectedRequests(participationRequestDto).build();
        }

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<EventFullDto> getEvent(Long id) {
        log.info(Messages.MESSAGE_GET_EVENT_BY_ID, id);

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Exceptions.EXCEPTION_NOT_FOUND));
        User user = userRepository.findById(event.getInitiator())
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.EXCEPTION_NOT_FOUND_USER, event.getInitiator())));
        Category category = categoryRepository.findById(event.getCategory())
                .orElseThrow(() -> new NotFoundException(Exceptions.EXCEPTION_NOT_FOUND));
        LocationEntity location = locationRepository.findById(event.getLocation())
                .orElseThrow(() -> new NotFoundException(Exceptions.EXCEPTION_NOT_FOUND));

        return ResponseEntity.status(HttpStatus.CREATED).body(getEventFullDto(event, user, category, location));
    }

    @Override
    public ResponseEntity<List<ParticipationRequestDto>> getEventParticipants(Long userId, Long eventId) {
        log.info(Messages.MESSAGE_GET_PARTICIPANTS, userId, eventId);

        List<ParticipationRequestDto> result = requestRepository.getRequestsByUserIdAndEventId(userId, eventId)
                .stream().map(eventMapper::requestToParticipationRequestDto).toList();

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<EventFullDto> getEventUser(Long userId, Long eventId) {
        log.info(Messages.MESSAGE_GET_EVENTS_BY_USER_ID_AND_EVENT_ID, eventId, userId);

        Event event = eventRepository.getEventByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new NotFoundException(Exceptions.EXCEPTION_NOT_FOUND));
        User user = userRepository.findById(event.getInitiator())
                .orElseThrow(() -> new NotFoundException(String.format(Exceptions.EXCEPTION_NOT_FOUND_USER, event.getInitiator())));
        Category category = categoryRepository.findById(event.getCategory())
                .orElseThrow(() -> new NotFoundException(Exceptions.EXCEPTION_NOT_FOUND));
        LocationEntity location = locationRepository.findById(event.getLocation())
                .orElseThrow(() -> new NotFoundException(Exceptions.EXCEPTION_NOT_FOUND));

        return ResponseEntity.status(HttpStatus.OK).body(getEventFullDto(event, user, category, location));
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

    private EventFullDto getEventFullDto(Event event, User user, Category category, LocationEntity location) {
        EventFullDto eventFullDto = eventMapper.eventToEventFullDto(event);

        eventFullDto.setState(EventFullDto.StateEnum.PUBLISHED);
        eventFullDto.setPublishedOn(OffsetDateTime.now());
        eventFullDto.setInitiator(userMapper.userToUserShortDto(user));
        eventFullDto.setCategory(categoryMapper.toCategoryDto(category));
        eventFullDto.setLocation(locationMapper.locationEntityToLocation(location));

        return eventFullDto;
    }
}
