package com.eralp.dto;

import com.eralp.exceptions.ExceptionData;
import com.eralp.exceptions.ExceptionType;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * This class represents a general response for the API.
 *
 * @author Eralp Nitelik
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class ApiResponse {
    private Object data;
    private ExceptionData error;
    private int status;
    private boolean isSuccessful;
    private Date timestamp;

    /**
     * Creates a {@link ResponseEntity} with {@link ApiResponse} as the body and 200 as the status code.
     * As the response is successful, it includes a null error field.
     *
     * @param responseData The data to be included in the response
     * @return A {@link ResponseEntity} object with {@link ApiResponse} that contains the response data
     * @author Eralp Nitelik
     */
    public ResponseEntity<ApiResponse> createOkResponse(Object responseData) {
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .data(responseData)
                        .status(HttpStatus.OK.value())
                        .isSuccessful(true)
                        .timestamp(new Date())
                        .build()
                , HttpStatus.OK
        );
    }

    /**
     * Creates a {@link ResponseEntity} with {@link ApiResponse} as the body and a specified HTTP status code.
     * As the request failed, it includes a null data field.
     *
     * @param exceptionType The enumerated type of exception to be included in the response
     * @param exceptionData The error data to be included in the response
     * @return A {@link ResponseEntity} object with {@link ApiResponse} that contains exception data
     * @author Eralp Nitelik
     */
    public ResponseEntity<ApiResponse> createErrorResponse(ExceptionType exceptionType, ExceptionData exceptionData) {
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .error(exceptionData)
                        .status(exceptionType.getHttpStatus().value())
                        .isSuccessful(false)
                        .timestamp(new Date())
                        .build()
                , exceptionType.getHttpStatus()
        );
    }
}
