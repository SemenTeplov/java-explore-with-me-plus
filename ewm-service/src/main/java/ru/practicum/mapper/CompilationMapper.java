package main.java.ru.practicum.mapper;

import main.java.ru.practicum.persistence.entity.Compilation;

import org.mapstruct.Mapper;

import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.openapi.model.CompilationDto;
import ru.practicum.openapi.model.NewCompilationDto;
import ru.practicum.openapi.model.UpdateCompilationRequest;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompilationMapper {
    CompilationDto compilationToCompilationDto(Compilation compilation);

    Compilation newCompilationDtoToCompilation(NewCompilationDto newCompilationDto);

    void updateCompilationRequestToCompilation(@MappingTarget Compilation compilation,
                                               UpdateCompilationRequest updateCompilationRequest);
}
