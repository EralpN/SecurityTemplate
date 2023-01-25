package com.eralp.exceptions;

import com.eralp.exceptions.custom.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.eralp.exceptions.ExceptionType.*;

/**
 * This class is a global exception handler. It is responsible for handling exceptions thrown by the application's REST controllers.
 * Each exception handler method takes a single argument,
 * an instance of the exception being handled,
 * and returns a {@link ResponseEntity} containing an {@link ExceptionResponse} object.
 *
 * @author Eralp Nitelik
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception) {
        log.error("Unhandled error occurred!", exception);
        return createExceptionInfoResponse(UNEXPECTED_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException exception) {
        log.error("Unhandled runtime error occurred!", exception);
        return createExceptionInfoResponse(UNEXPECTED_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(UsernameNotFoundException exception) {
        log.warn("Auth does not exist or deleted.", exception);
        return createExceptionInfoResponse(LOGIN_ERROR_USERNAME_DOES_NOT_EXIST);
    }

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException exception) {
        log.warn("Authentication information does not match. {}", exception.getMessage());
        return createExceptionInfoResponse(LOGIN_ERROR_WRONG_PASSWORD);
    }

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        log.warn("Unique key already exists on database. {}", exception.getMessage());
        return createExceptionInfoResponse(REGISTER_ERROR_DATA_EXISTS);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.warn("Incoming data validation failed. {}", exception.getMessage());
        return createExceptionInfoResponse(DATA_NOT_VALID);
    }

    /**
     * This method helps cast predefined {@link ExceptionType}'s to {@link ExceptionResponse} so they can be sent to client.
     *
     * @param exceptionType the predefined exception
     * @return A new {@link ResponseEntity} with {@link ExceptionResponse} as its body
     */
    private ResponseEntity<ExceptionResponse> createExceptionInfoResponse(ExceptionType exceptionType) {
        return new ResponseEntity<>(ExceptionResponse.builder()
                .exceptionCode(exceptionType.getCode())
                .customMessage(exceptionType.getMessage())
                .httpStatus(exceptionType.getHttpStatus().value())
                .build(), exceptionType.getHttpStatus());
    }
}
