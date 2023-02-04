package com.eralp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

/**
 * This enumeration represents different types of exceptions that can occur in the application.
 * This class also helps to configure all exceptions from one place.
 *
 * @author Eralp Nitelik
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionType {
    /*
        Unknown errors.
     */
    UNEXPECTED_ERROR(9000, "exception.general.unexpected", INTERNAL_SERVER_ERROR),

    /*
        General errors.
     */
    INTERNAL_ERROR(9001, "exception.general.internal_server", INTERNAL_SERVER_ERROR),
    BAD_REQUEST_ERROR(9002, "exception.general.bad_request", BAD_REQUEST),

    /*
        Validation errors.
     */
    DATA_NOT_VALID(1001, "exception.validation.unexpected", BAD_REQUEST),

    /*
        Authentication errors.
     */
    UNEXPECTED_AUTHENTICATION_ERROR(2000, "exception.authentication.unexpected", INTERNAL_SERVER_ERROR),
    LOGIN_ERROR_USERNAME_DOES_NOT_EXIST(2001, "exception.authentication.login.not_exists", BAD_REQUEST),
    LOGIN_ERROR_WRONG_PASSWORD(2002, "exception.authentication.login.wrong_pass", BAD_REQUEST),
    ACCESS_PRIVILEGE_INSUFFICIENT(2003, "exception.authentication.privilege_insufficient", FORBIDDEN),
    AUTHORIZATION_REQUIRED(2004, "exception.authentication.not_logged_in", UNAUTHORIZED),
    INVALID_TOKEN_DETECTED(2005, "exception.authentication.invalid_token", BAD_REQUEST),

    /*
        Register errors.
     */
    REGISTER_ERROR_DATA_EXISTS(3001, "exception.authentication.register.exists", BAD_REQUEST);


    private int code;
    private String localeMessageCode;
    private HttpStatus httpStatus;
}
