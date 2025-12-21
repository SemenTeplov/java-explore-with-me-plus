package main.java.ru.practicum.exception;

import constant.Messages;
import dto.ExceptionDto;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDto> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(Messages.MESSAGE_UNPROCESSABLE_ENTITY, e.getMessage());

        return new ResponseEntity<>(
                new ExceptionDto(Messages.EXCEPTION_UNPROCESSABLE_ENTITY,
                        HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        LocalDateTime.now()),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleGlobalException(Exception e) {
        log.error(Messages.MESSAGE_INTERNAL_SERVER, e.getMessage());

        return new ResponseEntity<>(
                new ExceptionDto(Messages.EXCEPTION_INTERNAL_SERVER,
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        LocalDateTime.now()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
