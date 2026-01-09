package main.java.ru.practicum.mapper;

import main.java.ru.practicum.persistence.entity.Initiator;

import org.mapstruct.Mapper;

import ru.practicum.openapi.model.UserShortDto;

@Mapper(componentModel = "spring")
public interface InitiatorMapper {
    UserShortDto initiatorToUserShortDto(Initiator initiator);
}
