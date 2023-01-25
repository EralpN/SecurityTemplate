package com.eralp.exceptions.custom;

/**
 * @author Eralp Nitelik
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
