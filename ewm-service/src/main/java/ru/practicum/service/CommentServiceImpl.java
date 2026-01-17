package main.java.ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.java.ru.practicum.constant.Exceptions;
import main.java.ru.practicum.constant.Messages;
import main.java.ru.practicum.exception.NotFoundException;
import main.java.ru.practicum.exception.ValidationException;
import main.java.ru.practicum.mapper.CommentMapper;
import main.java.ru.practicum.mapper.EventMapper;
import main.java.ru.practicum.mapper.UserMapper;
import main.java.ru.practicum.persistence.entity.Comment;
import main.java.ru.practicum.persistence.entity.Event;
import main.java.ru.practicum.persistence.entity.User;
import main.java.ru.practicum.persistence.repository.CommentRepository;
import main.java.ru.practicum.persistence.repository.EventRepository;
import main.java.ru.practicum.persistence.repository.RequestRepository;
import main.java.ru.practicum.persistence.repository.UserRepository;
import main.java.ru.practicum.persistence.status.StatusRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import ru.practicum.openapi.model.CommentDto;
import ru.practicum.openapi.model.EventShortDto;
import ru.practicum.openapi.model.NewCommentDto;
import ru.practicum.openapi.model.UserShortDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.openapi.model.EventFullDto.StateEnum.PUBLISHED;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;
    private final EventMapper eventMapper;

    @Override
    public CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto) {
        log.info(Messages.MESSAGE_ADD_COMMENT, userId, eventId);

        if (newCommentDto.getText() == null || newCommentDto.getText().trim().isEmpty()) {
            throw new ValidationException(Exceptions.EXCEPTION_COMMENT_IS_EMPTY);
        }

        User author = checkAndGetUser(userId);
        Event event = checkAndGetEvent(eventId);
        if (!event.getState().equals(PUBLISHED.toString())) {
            throw new ValidationException(Exceptions.EXCEPTION_COMMENT_NOT_PUBLISHED);
        }
        Comment comment = commentRepository.save(commentMapper.toComment(newCommentDto, author, event));
        UserShortDto userShort = userMapper.userToUserShortDto(author);

        Long confirmedRequests = requestRepository.countByEventIdAndStatus(eventId, StatusRequest.CONFIRMED.toString());

        EventShortDto eventShort = createEventShortDtoWithConfirmedRequests(event, confirmedRequests);

        return commentMapper.toCommentDto(comment, userShort, eventShort);
    }

    @Override
    public CommentDto updateComment(Long userId, Long eventId, Long commentId, NewCommentDto newCommentDto) {
        log.info(Messages.MESSAGE_UPDATE_COMMENT, userId, eventId, commentId);

        if (newCommentDto.getText() == null || newCommentDto.getText().trim().isEmpty()) {
            throw new ValidationException(Exceptions.EXCEPTION_COMMENT_IS_EMPTY);
        }

        User author = checkAndGetUser(userId);
        Event event = checkAndGetEvent(eventId);
        Comment comment = checkAndGetComment(commentId);

        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ValidationException(Exceptions.EXCEPTION_ONLY_AUTHOR_CAN_EDIT);
        }

        if (!comment.getEvent().getId().equals(eventId)) {
            throw new ValidationException(Exceptions.EXCEPTION_COMMENT_FOR_OTHER_EVENT);
        }

        comment.setText(newCommentDto.getText());
        comment.setEdited(LocalDateTime.now());
        comment = commentRepository.save(comment);

        UserShortDto userShort = userMapper.userToUserShortDto(author);
        Long confirmedRequests = requestRepository.countByEventIdAndStatus(eventId, StatusRequest.CONFIRMED.toString());
        EventShortDto eventShort = createEventShortDtoWithConfirmedRequests(event, confirmedRequests);

        return commentMapper.toCommentDto(comment, userShort, eventShort);
    }

    @Override
    public List<CommentDto> getCommentsByAuthor(Long userId, Integer from, Integer size) {
        log.info(Messages.MESSAGE_GET_COMMENTS_BY_AUTHOR, userId);

        checkAndGetUser(userId);
        PageRequest pageRequest = PageRequest.of(from / size, size);

        return commentRepository.findAllByAuthorId(userId, pageRequest)
                .stream()
                .map(comment -> {
                    UserShortDto userShort = userMapper.userToUserShortDto(comment.getAuthor());
                    Long confirmedRequests = requestRepository.countByEventIdAndStatus(
                            comment.getEvent().getId(), StatusRequest.CONFIRMED.toString());
                    EventShortDto eventShort = createEventShortDtoWithConfirmedRequests(
                            comment.getEvent(), confirmedRequests);
                    return commentMapper.toCommentDto(comment, userShort, eventShort);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getComments(Long eventId, Integer from, Integer size) {
        log.info(Messages.MESSAGE_GET_COMMENTS_BY_EVENT, eventId);

        Event event = checkAndGetEvent(eventId);
        PageRequest pageRequest = PageRequest.of(from / size, size);
        Long confirmedRequests = requestRepository.countByEventIdAndStatus(eventId, StatusRequest.CONFIRMED.toString());
        EventShortDto eventShort = createEventShortDtoWithConfirmedRequests(event, confirmedRequests);

        return commentRepository.findAllByEventId(eventId, pageRequest)
                .stream()
                .map(comment -> {
                    UserShortDto userShort = userMapper.userToUserShortDto(comment.getAuthor());
                    return commentMapper.toCommentDto(comment, userShort, eventShort);
                })
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long commentId) {
        log.info(Messages.MESSAGE_GET_COMMENT_BY_ID, commentId);

        Comment comment = checkAndGetComment(commentId);
        UserShortDto userShort = userMapper.userToUserShortDto(comment.getAuthor());
        Long confirmedRequests = requestRepository.countByEventIdAndStatus(
                comment.getEvent().getId(), StatusRequest.CONFIRMED.toString());
        EventShortDto eventShort = createEventShortDtoWithConfirmedRequests(
                comment.getEvent(), confirmedRequests);
        return commentMapper.toCommentDto(comment, userShort, eventShort);
    }

    @Override
    public void deleteComment(Long userId, Long commentId) {
        log.info(Messages.MESSAGE_DELETE_COMMENT, userId, commentId);

        User author = checkAndGetUser(userId);
        Comment comment = checkAndGetComment(commentId);

        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ValidationException(Exceptions.EXCEPTION_ONLY_AUTHOR_CAN_DELETE);
        }

        commentRepository.deleteById(commentId);
    }

    @Override
    public void deleteComment(Long commentId) {
        log.info(Messages.MESSAGE_DELETE_COMMENT_BY_ADMIN, commentId);

        checkAndGetComment(commentId);
        commentRepository.deleteById(commentId);
    }

    private User checkAndGetUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format(Exceptions.EXCEPTION_NOT_FOUND_USER, userId)));
    }

    private Event checkAndGetEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format(Exceptions.EXCEPTION_EVENT_NOT_FOUND, eventId)));
    }

    private Comment checkAndGetComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new NotFoundException(String.format(Exceptions.EXCEPTION_COMMENT_NOT_FOUND, commentId)));
    }

    private EventShortDto createEventShortDtoWithConfirmedRequests(Event event, Long confirmedRequests) {
        if (event == null) {
            return null;
        }

        EventShortDto dto = eventMapper.eventToEventShortDto(event);
        dto.setConfirmedRequests(confirmedRequests);
        dto.setViews(event.getViews() != null ? event.getViews() : 0L);

        return dto;
    }
}
