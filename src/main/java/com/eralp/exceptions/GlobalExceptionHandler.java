package com.eralp.exceptions;

import com.eralp.configuration.locale.LocaleSelector;
import com.eralp.dto.ApiResponse;
import com.eralp.exceptions.custom.RequestNotValidException;
import com.eralp.exceptions.custom.UserAlreadyExistsException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.eralp.exceptions.ExceptionType.*;

/**
 * This class is a global exception handler. It is responsible for handling exceptions thrown by the application's REST controllers.
 * Each exception handler method returns an {@link ApiResponse} containing an {@link ExceptionData} object inside of {@link ResponseEntity}.
 *
 * @author Eralp Nitelik
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ApiResponse apiResponse;

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiResponse> handleAllExceptions(Exception exception) {
        log.error("Unhandled error occurred!", exception);
        return createExceptionResponse(UNEXPECTED_ERROR, exception);
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException exception) {
        log.error("Unhandled runtime error occurred!", exception);
        return createExceptionResponse(UNEXPECTED_ERROR, exception);
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.warn("Invalid json. {}", exception.getMessage());
        return createExceptionResponse(BAD_REQUEST_ERROR, exception);
    }

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(UsernameNotFoundException exception) {
        log.warn("User does not exist or deleted. {}", exception.getMessage());
        return createExceptionResponse(LOGIN_ERROR_USERNAME_DOES_NOT_EXIST, exception);
    }

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCredentialsException(BadCredentialsException exception) {
        log.warn("Authentication information does not match. {}", exception.getMessage());
        return createExceptionResponse(LOGIN_ERROR_WRONG_PASSWORD, exception);
    }

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        log.warn("Unique key already exists on database. {}", exception.getMessage());
        return createExceptionResponse(REGISTER_ERROR_DATA_EXISTS, exception);
    }

    // default validation exception
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.warn("Incoming data validation failed. {}", exception.getMessage());
        return createExceptionResponse(DATA_NOT_VALID, exception);
    }

    // custom validation exception
    @ResponseBody
    @ExceptionHandler(RequestNotValidException.class)
    public ResponseEntity<ApiResponse> handleRequestNotValidException(RequestNotValidException exception) {
        log.warn("Incoming data validation failed. {}", exception.getMessage());
        return createExceptionResponse(DATA_NOT_VALID, exception);
    }

    // security exceptions can be handled here thanks to
    // HandlerExceptionResolver being injected to AuthenticationEntryPoint and AccessDeniedHandler
    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse> handleAllAuthenticationException(AuthenticationException exception) {
        log.error("Insufficient privileges to access this resource.", exception);
        return createExceptionResponse(UNEXPECTED_AUTHENTICATION_ERROR, exception);
    }

    @ResponseBody
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ApiResponse> handleInsufficientAuthenticationException(InsufficientAuthenticationException exception) {
        log.warn("Insufficient privileges to access this resource. {}", exception.getMessage());
        return createExceptionResponse(ACCESS_PRIVILEGE_INSUFFICIENT, exception);
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException exception) {
        log.warn("Authorization is required to access this resource. {}", exception.getMessage());
        return createExceptionResponse(AUTHORIZATION_REQUIRED, exception);
    }

    // Jwt related exceptions are handled thanks to FilterChainExceptionHandler
    @ResponseBody
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse> handleMalformedJwtException(JwtException exception) {
        log.warn("Invalid token.", exception);
        return createExceptionResponse(INVALID_TOKEN_DETECTED, exception);
    }

    /**
     * This method helps cast predefined {@link ExceptionType}'s to {@link ExceptionData} so they can be sent to client inside of {@link ApiResponse}.
     *
     * @param exceptionType the predefined exception
     * @return A new {@link ApiResponse} with {@link ExceptionData} as its error field
     */
    private ResponseEntity<ApiResponse> createExceptionResponse(ExceptionType exceptionType, Exception exception) {
        return apiResponse
                .createErrorResponse(
                        exceptionType,
                        ExceptionData.builder()
                                .exceptionCode(exceptionType.getCode())
                                .defaultMessage(LocaleSelector.withCode(exceptionType.getLocaleMessageCode()))
                                .errorMessage(exception.getMessage())
                                .build()
                );
    }
}
