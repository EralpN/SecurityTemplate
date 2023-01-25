package com.eralp.exceptions;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * The response object that will be sent in case of exception.
 *
 * @author Eralp Nitelik
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class ExceptionResponse {
    private int exceptionCode;
    private String customMessage;
    private int httpStatus;
}
