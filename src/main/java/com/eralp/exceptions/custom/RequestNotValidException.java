package com.eralp.exceptions.custom;

/**
 * @author Eralp Nitelik
 */
public class RequestNotValidException extends RuntimeException {
    public RequestNotValidException(String message) {
        super(message);
    }
}
