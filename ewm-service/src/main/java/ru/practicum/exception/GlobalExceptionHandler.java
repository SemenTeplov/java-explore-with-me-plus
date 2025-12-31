package main.java.ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;

import main.java.ru.practicum.constant.Messages;
import main.java.ru.practicum.constant.Values;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.openapi.model.ApiError;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        log.info("404 {}", e.getMessage(), e);
        return createApiError(
                ApiError.StatusEnum._404_NOT_FOUND,
                Messages.MESSAGE_NOT_FOUND,
                e.getMessage(),
                getStackTrace(e)
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleException(final Exception e) {
        log.info("500 {}", e.getMessage(), e);
        return createApiError(
                ApiError.StatusEnum._500_INTERNAL_SERVER_ERROR,
                Messages.EXCEPTION_INTERNAL_SERVER,
                e.getMessage(),
                getStackTrace(e)
        );
    }

    private ApiError createApiError(ApiError.StatusEnum status,
                                    String reason,
                                    String message,
                                    String stackTrace) {
        return new ApiError()
                .errors(Collections.emptyList())
                .message(message)
                .reason(reason)
                .status(status)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(Values.DATE_TIME_PATTERN)));
    }

    private String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
