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
public class ExceptionData {
    private int exceptionCode;
    private String defaultMessage;
    private String errorMessage;
}
