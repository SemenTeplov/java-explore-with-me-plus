package ru.practicum.mapper;

import org.mapstruct.Mapper;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.model.EndpointHit;

@Mapper(componentModel = "spring")
public interface StatsMapper {
    EndpointHit endpointHitDtoToEndpointHit(EndpointHitDto hitDto);
}
