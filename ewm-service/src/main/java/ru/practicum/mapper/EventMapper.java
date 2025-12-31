package main.java.ru.practicum.mapper;

import main.java.ru.practicum.persistence.entity.Event;

import org.mapstruct.Mapper;

import ru.practicum.openapi.model.EventShortDto;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventShortDto eventToEventShortDto(Event event);
}
