package org.castle.djames.validator;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.castle.djames.exception.ValidationException;

@AllArgsConstructor
@ApplicationScoped
public class RequestValidator {

    private final Validator validator;

    public void validate(Object request, Class<?>... groups) {
        var violations = validator.validate(request, groups);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations.toString());
        }
    }

}
