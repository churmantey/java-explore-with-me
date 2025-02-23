package ru.practicum.ewm.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        log.error("Got 404 status Not found {}", e.getMessage(), e);
        return new ErrorResponse(HttpStatus.NOT_FOUND.name(), "Not found", e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleNotFoundException(ValidationException e) {
        log.error("Got 409 status conflicting data {}", e.getMessage(), e);
        return new ErrorResponse(HttpStatus.CONFLICT.name(), "Conflicting data",
                e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(MalformedDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMalformedDataException(MalformedDataException e) {
        log.error("Got 400 status malformed data {}", e.getMessage(), e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.name(), "Malformed data",
                e.getMessage(), LocalDateTime.now());
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(RuntimeException e) {
        log.error("Got 500 status, server error {}", e.getMessage(), e);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), "Server error",
                e.getMessage(), LocalDateTime.now());
    }

}