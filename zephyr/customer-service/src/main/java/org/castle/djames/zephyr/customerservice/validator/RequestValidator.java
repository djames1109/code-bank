package org.castle.djames.zephyr.customerservice.validator;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.castle.djames.zephyr.customerservice.exception.ValidationException;

@AllArgsConstructor
@ApplicationScoped
public class RequestValidator {

    private final Validator validator;

    public void validate(Object request, Class<?>... groups) {
        var violations = validator.validate(request, groups);
        if (!violations.isEmpty()) {
            var messages = violations.stream()
                .map(this::formatViolation)
                .toList();
            throw new ValidationException(String.join(", ", messages));
        }
    }

    private String formatViolation(ConstraintViolation<?> violation) {
        return "%s %s".formatted(
            violation.getPropertyPath(),
            violation.getMessage()
        );
    }
}
