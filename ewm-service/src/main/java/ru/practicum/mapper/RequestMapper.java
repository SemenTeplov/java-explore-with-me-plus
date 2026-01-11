package main.java.ru.practicum.mapper;

import main.java.ru.practicum.constant.Values;
import main.java.ru.practicum.persistence.entity.Request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.Named;
import ru.practicum.openapi.model.ParticipationRequestDto;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(Values.DATE_TIME_PATTERN);

    @Mapping(target = "created", source = "created", qualifiedByName = "toOffsetDateTime")
    ParticipationRequestDto toParticipationRequestDto(Request participationRequest);

    @Named("toOffsetDateTime")
    default String toOffsetDateTime(OffsetDateTime time) {
        if (time == null) {
            return null;
        }

        return FORMATTER.format(time);
    }
}
