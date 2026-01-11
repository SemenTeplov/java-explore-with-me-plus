package main.java.ru.practicum.controller;

import lombok.RequiredArgsConstructor;

import main.java.ru.practicum.service.CompilationServer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ru.practicum.openapi.api.CompilationApi;
import ru.practicum.openapi.model.CompilationDto;
import ru.practicum.openapi.model.NewCompilationDto;
import ru.practicum.openapi.model.UpdateCompilationRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CompilationController implements CompilationApi {
    private final CompilationServer  compilationServer;

    @Override
    public ResponseEntity<Void> _deleteCompilation(Long compId) {
        return compilationServer.deleteCompilation(compId);
    }

    @Override
    public ResponseEntity<CompilationDto> _getCompilation(Long compId) {
        return compilationServer.getCompilation(compId);
    }

    @Override
    public ResponseEntity<List<CompilationDto>> _getCompilations(Boolean pinned, Integer from, Integer size) {
        return compilationServer.getCompilations(pinned, from, size);
    }

    @Override
    public ResponseEntity<CompilationDto> _saveCompilation(NewCompilationDto newCompilationDto) {
        return compilationServer.saveCompilation(newCompilationDto);
    }

    @Override
    public ResponseEntity<CompilationDto> _updateCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        return compilationServer.updateCompilation(compId, updateCompilationRequest);
    }
}
