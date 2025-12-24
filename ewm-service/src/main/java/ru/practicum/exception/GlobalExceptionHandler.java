package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.constant.Messages;
import ru.practicum.dto.ExceptionDto;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException e) {
        log.error(Messages.EXCEPTION_NOT_FOUND, e.getMessage());

        return new ResponseEntity<>(
                new ExceptionDto(e.getMessage(),
                        HttpStatus.NOT_FOUND.value(),
                        LocalDateTime.now()),
                HttpStatus.NOT_FOUND);
    }
}
