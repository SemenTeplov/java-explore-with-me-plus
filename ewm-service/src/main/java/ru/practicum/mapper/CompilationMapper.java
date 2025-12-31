package main.java.ru.practicum.mapper;

import main.java.ru.practicum.persistence.entity.Compilation;

import org.mapstruct.Mapper;

import ru.practicum.openapi.model.CompilationDto;
import ru.practicum.openapi.model.NewCompilationDto;
import ru.practicum.openapi.model.UpdateCompilationRequest;

@Mapper(componentModel = "spring")
public interface CompilationMapper {
    CompilationDto compilationToCompilationDto(Compilation compilation);

    Compilation newCompilationDtoToCompilation(NewCompilationDto newCompilationDto);

    Compilation updateCompilationRequestToCompilation(UpdateCompilationRequest updateCompilationRequest);
}
