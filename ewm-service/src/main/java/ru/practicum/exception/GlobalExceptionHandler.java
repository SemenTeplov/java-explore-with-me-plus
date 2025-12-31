package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;

import main.java.ru.practicum.constant.Exceptions;
import main.java.ru.practicum.constant.Messages;
import main.java.ru.practicum.exception.NotFoundCompletion;

import org.hibernate.exception.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import ru.practicum.openapi.model.ApiError;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({NotFoundCompletion.class})
    public ResponseEntity<ApiError> handleNotFoundCompletion(Exception ex) {
        log.warn(Messages.NOT_FOUND_COMPLETION, ex);
        ApiError body = new ApiError(Arrays.stream(
                ex.getStackTrace()).map(String::valueOf).toList(),
                Exceptions.NOT_FOUND_COMPLETION,
                Messages.NOT_FOUND_COMPLETION,
                ApiError.StatusEnum._404_NOT_FOUND,
                LocalDateTime.now().toString());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(Exception ex) {
        log.warn(Messages.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION, ex);
        ApiError body = new ApiError(Arrays.stream(
                ex.getStackTrace()).map(String::valueOf).toList(),
                Exceptions.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION,
                Messages.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION,
                ApiError.StatusEnum._400_BAD_REQUEST,
                LocalDateTime.now().toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ApiError> handleConstraintViolationException(Exception ex) {
        log.warn(Messages.CONSTRAINT_VIOLATION_EXCEPTION, ex);
        ApiError body = new ApiError(Arrays.stream(
                ex.getStackTrace()).map(String::valueOf).toList(),
                Exceptions.CONSTRAINT_VIOLATION_EXCEPTION,
                Messages.CONSTRAINT_VIOLATION_EXCEPTION,
                ApiError.StatusEnum._409_CONFLICT,
                LocalDateTime.now().toString());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex) {
        log.error(Messages.EXCEPTION, ex);
        ApiError body = new ApiError(Arrays.stream(
                ex.getStackTrace()).map(String::valueOf).toList(),
                Exceptions.EXCEPTION,
                Messages.EXCEPTION,
                ApiError.StatusEnum._500_INTERNAL_SERVER_ERROR,
                LocalDateTime.now().toString());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
