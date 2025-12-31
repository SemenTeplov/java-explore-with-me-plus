package main.java.ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;

import main.java.ru.practicum.constant.Exceptions;
import main.java.ru.practicum.constant.Messages;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import ru.practicum.openapi.model.ApiError;

import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException e) {
        log.info(Messages.MESSAGE_NOT_FOUND, e.getMessage());

        ApiError error = ApiError.builder()
                .errors(Arrays.stream(e.getStackTrace()).map(String::valueOf).toList())
                .reason(Messages.MESSAGE_NOT_FOUND)
                .message(Exceptions.EXCEPTION_NOT_FOUND)
                .status(ApiError.StatusEnum._404_NOT_FOUND)
                .timestamp(LocalDateTime.now().toString())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e) {
        log.info(Messages.MESSAGE_INTERNAL_SERVER, e.getMessage(), e);

        ApiError error = ApiError.builder()
                .errors(Arrays.stream(e.getStackTrace()).map(String::valueOf).toList())
                .reason(Messages.MESSAGE_INTERNAL_SERVER)
                .message(Exceptions.EXCEPTION_INTERNAL_SERVER)
                .status(ApiError.StatusEnum._500_INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now().toString())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
