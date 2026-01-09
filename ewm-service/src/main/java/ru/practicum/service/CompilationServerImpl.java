package main.java.ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import main.java.ru.practicum.constant.Exceptions;
import main.java.ru.practicum.constant.Messages;
import main.java.ru.practicum.exception.NotFoundCompletion;
import main.java.ru.practicum.mapper.CategoryMapper;
import main.java.ru.practicum.mapper.CompilationMapper;
import main.java.ru.practicum.mapper.EventMapper;
import main.java.ru.practicum.mapper.UserMapper;
import main.java.ru.practicum.persistence.entity.Compilation;
import main.java.ru.practicum.persistence.entity.CompilationToEvents;
import main.java.ru.practicum.persistence.entity.Event;
import main.java.ru.practicum.persistence.repository.CategoryRepository;
import main.java.ru.practicum.persistence.repository.CompilationRepository;
import main.java.ru.practicum.persistence.repository.CompilationToEventsRepository;
import main.java.ru.practicum.persistence.repository.EventRepository;

import main.java.ru.practicum.persistence.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.openapi.model.CategoryDto;
import ru.practicum.openapi.model.CompilationDto;
import ru.practicum.openapi.model.EventShortDto;
import ru.practicum.openapi.model.NewCompilationDto;
import ru.practicum.openapi.model.UpdateCompilationRequest;
import ru.practicum.openapi.model.UserShortDto;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServerImpl implements main.java.ru.practicum.service.CompilationServer {
    private final CompilationRepository compilationRepository;

    private final CategoryRepository categoryRepository;

    private final EventRepository eventRepository;

    private final UserRepository initiatorRepository;

    private final CompilationToEventsRepository compilationToEventsRepository;

    private final CompilationMapper compilationMapper;

    private final EventMapper eventMapper;

    private final CategoryMapper categoryMapper;

    private final UserMapper initiatorMapper;

    @Override
    public ResponseEntity<List<CompilationDto>> getCompilations(Boolean pinned, Integer from, Integer size) {
        log.info(Messages.GET_COMPILATIONS);

        List<CompilationDto> list = compilationRepository.getCompilations(pinned, from, size).stream()
                .map(compilationMapper::compilationToCompilationDto)
                .toList();

        if (!list.isEmpty()) {
            List<Event> events = eventRepository.getEventsByCompilationIds(list.stream()
                    .map(CompilationDto::getId).distinct().toArray(Long[]::new));
            List<CategoryDto> categories = getCategories(events.stream()
                    .map(Event::getCategory).distinct().toArray(Long[]::new));
            List<UserShortDto> users = getUsers(events.stream()
                    .map(Event::getInitiator).distinct().toArray(Long[]::new));
            List<CompilationToEvents> compilationToEvents =
                    compilationToEventsRepository.getCompilationToEventsByIdsCompilation(list.stream()
                            .map(CompilationDto::getId)
                            .distinct()
                            .toArray(Long[]::new));

            list.forEach(item -> item
                    .events(completionCompilationDto(item, events, compilationToEvents, categories, users)));
        }

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @Override
    public ResponseEntity<CompilationDto> getCompilation(Long compId) {
        log.info(Messages.GET_COMPILATION, compId);

        CompilationDto compilationDto =
                compilationMapper.compilationToCompilationDto(compilationRepository.getCompilation(compId)
                        .orElseThrow(() -> new NotFoundCompletion(Exceptions.NOT_FOUND_COMPLETION)));

        List<Event> events = eventRepository.getEventsByCompilationIds(new Long[] {compId});

        List<CategoryDto> categories = getCategories(events.stream()
                .map(Event::getCategory).distinct().toArray(Long[]::new));
        List<UserShortDto> users = getUsers(events.stream()
                .map(Event::getInitiator).distinct().toArray(Long[]::new));
        List<CompilationToEvents> compilationToEvents =
                compilationToEventsRepository.getCompilationToEventsByIdsCompilation(new Long[] {compilationDto.getId()});

        compilationDto.setEvents(completionCompilationDto(compilationDto, events,
                compilationToEvents, categories, users));

        return ResponseEntity.status(HttpStatus.OK).body(compilationDto);
    }

    @Override
    @Transactional
    public ResponseEntity<CompilationDto> saveCompilation(NewCompilationDto newCompilationDto) {
        log.info(Messages.SAVE_COMPILATION, newCompilationDto);

        CompilationDto compilationDto = compilationMapper.compilationToCompilationDto(compilationRepository
                .save(compilationMapper.newCompilationDtoToCompilation(newCompilationDto)));

        List<CompilationToEvents> compilationToEvents = compilationToEventsRepository
                .saveAll(newCompilationDto.getEvents().stream()
                        .map(i -> CompilationToEvents.builder().compilationId(compilationDto.getId()).eventId(i).build())
                        .toList());

        List<Event> events = eventRepository.getEventsByCompilationIds(newCompilationDto.getEvents().toArray(new Long[0]));

        List<CategoryDto> categories = getCategories(events.stream()
                .map(Event::getCategory).distinct().toArray(Long[]::new));
        List<UserShortDto> users = getUsers(events.stream()
                .map(Event::getInitiator).distinct().toArray(Long[]::new));

        compilationDto.setEvents(completionCompilationDto(compilationDto, events,
                compilationToEvents, categories, users));

        return ResponseEntity.status(HttpStatus.CREATED).body(compilationDto);
    }

    @Override
    @Transactional
    public ResponseEntity<CompilationDto> updateCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        log.info(Messages.UPDATE_COMPILATION, compId);

        Compilation compilation = compilationRepository.getCompilation(compId)
                .orElseThrow(() -> new NotFoundCompletion(Exceptions.NOT_FOUND_COMPLETION));

        compilationToEventsRepository.deleteCompilationToEventsByIdsCompilation(compId);
        compilationToEventsRepository.saveAll(updateCompilationRequest.getEvents().stream()
                .map(cr -> CompilationToEvents.builder().compilationId(compId).eventId(cr).build())
                .toList());

        compilationMapper.updateCompilationRequestToCompilation(compilation, updateCompilationRequest);

        compilationRepository.save(compilation);

        CompilationDto compilationDto = compilationMapper.compilationToCompilationDto(compilation);

        List<Event> events = eventRepository.getEventsByCompilationIds(new Long[] {compId});

        List<CategoryDto> categories = getCategories(events.stream()
                .map(Event::getCategory).distinct().toArray(Long[]::new));
        List<UserShortDto> users = getUsers(events.stream()
                .map(Event::getInitiator).distinct().toArray(Long[]::new));
        List<CompilationToEvents> compilationToEvents =
                compilationToEventsRepository.getCompilationToEventsByIdsCompilation(new Long[] {compilationDto.getId()});

        compilationDto.setEvents(completionCompilationDto(compilationDto, events,
                compilationToEvents, categories, users));

        return ResponseEntity.status(HttpStatus.OK).body(compilationDto);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteCompilation(Long compId) {
        log.info(Messages.DELETE_COMPILATION, compId);

        Compilation compilation = compilationRepository.getCompilation(compId)
                .orElseThrow(() -> new NotFoundCompletion(Exceptions.NOT_FOUND_COMPLETION));

        compilationToEventsRepository.deleteCompilationToEventsByIdsCompilation(compilation.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private List<CategoryDto> getCategories(Long[] categoryIds) {
        return categoryRepository.getCategoriesByIds(categoryIds).stream()
                .map(categoryMapper::toCategoryDto)
                .toList();
    }

    private List<UserShortDto> getUsers(Long[] initiatorIds) {
        return initiatorRepository.getInitiatorByCompilationIds(initiatorIds).stream()
                .map(initiatorMapper::userToUserShortDto)
                .toList();
    }

    private List<EventShortDto> completionCompilationDto(CompilationDto compilationDto,
                                                         List<Event> events,
                                                         List<CompilationToEvents> compilationToEvents,
                                                         List<CategoryDto> categories,
                                                         List<UserShortDto> users) {
        return events.stream()
                .filter(e -> compilationToEvents.stream()
                        .anyMatch(ce -> ce.getCompilationId()
                                .equals(compilationDto.getId()) && ce.getEventId().equals(e.getId())))
                .map(e -> eventMapper.eventToEventShortDto(e)
                        .category(categories.stream().filter(c -> c.getId()
                                .equals(e.getCategory())).findFirst().get())
                        .initiator(users.stream().filter(u -> u.getId()
                                .equals(e.getInitiator())).findFirst().get()))
                .toList();
    }
}
