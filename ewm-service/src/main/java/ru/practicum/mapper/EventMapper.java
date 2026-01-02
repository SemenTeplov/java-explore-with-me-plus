package main.java.ru.practicum.mapper;

import main.java.ru.practicum.persistence.entity.Event;
import main.java.ru.practicum.persistence.entity.Request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.practicum.openapi.model.EventFullDto;
import ru.practicum.openapi.model.NewEventDto;
import ru.practicum.openapi.model.ParticipationRequestDto;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event newEventDtoToEvent(NewEventDto newEventDto);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "location", ignore = true)
    EventFullDto eventToEventFullDto(Event event);

    ParticipationRequestDto requestToParticipationRequestDto(Request requests);
}
