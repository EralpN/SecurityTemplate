package com.eralp.exceptions.validators;

import com.eralp.exceptions.custom.RequestNotValidException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@link DtoValidator} class is a component for validating DTO objects.
 *
 * @author Eralp Nitelik
 */
@Component
public class DtoValidator {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    /**
     * Validates the given object. If there are any constraints violations, an instance of
     * {@link RequestNotValidException} is thrown with the constraint violation messages joined with a comma.
     *
     * @param dtoToValidate the object to be validated
     * @throws RequestNotValidException if there are any constraint violations on the given object
     * @author Eralp Nitelik
     */
    public void validate(Object dtoToValidate) {
        Set<ConstraintViolation<Object>> violations = validator.validate(dtoToValidate);
        if (!violations.isEmpty()) {
            throw new RequestNotValidException(
                    violations
                            .stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining(", "))
            );
        }
    }
}
