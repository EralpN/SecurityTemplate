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
    UNEXPECTED_ERROR(9000, "Unexpected Error! Please submit a bug report.", INTERNAL_SERVER_ERROR),

    /*
        General errors.
     */
    INTERNAL_ERROR(9001, "Internal Server Error", INTERNAL_SERVER_ERROR),
    BAD_REQUEST_ERROR(9002, "Invalid Parameter Error", BAD_REQUEST),

    /*
        Validation errors.
     */
    DATA_NOT_VALID(1001, "Data does not meet requirements", BAD_REQUEST),

    /*
        Authentication errors.
     */
    UNEXPECTED_AUTHENTICATION_ERROR(2000, "Unexpected Authentication Error! Please submit a bug report.", INTERNAL_SERVER_ERROR),
    LOGIN_ERROR_USERNAME_DOES_NOT_EXIST(2001, "Username does not exist.", BAD_REQUEST),
    LOGIN_ERROR_WRONG_PASSWORD(2002, "Wrong password.", BAD_REQUEST),
    ACCESS_PRIVILEGE_INSUFFICIENT(2003, "Insufficient privileges to access this resource.", FORBIDDEN),
    AUTHORIZATION_REQUIRED(2004, "Authorization required to access this resource.", UNAUTHORIZED),
    INVALID_TOKEN_DETECTED(2005, "Invalid token.", BAD_REQUEST),

    /*
        Register errors.
     */
    REGISTER_ERROR_DATA_EXISTS(3001, "Data already exists.", BAD_REQUEST);


    private int code;
    private String message;
    private HttpStatus httpStatus;
}
