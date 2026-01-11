package main.java.ru.practicum.mapper;

import main.java.ru.practicum.persistence.entity.Request;
import org.mapstruct.Mapper;
import ru.practicum.openapi.model.ParticipationRequestDto;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    ParticipationRequestDto toParticipationRequestDto(Request request);
}
