package org.castle.djames.zephyr.customerservice.exception;

import java.util.List;
import lombok.Getter;

public class ValidationException extends BaseZephyrException {

    @Getter
    private final List<String> violations;

    public ValidationException(List<String> violations) {
        super(" " + violations.stream().reduce("", (a, b) -> a + b + ","));
        this.violations = violations;
    }
}
