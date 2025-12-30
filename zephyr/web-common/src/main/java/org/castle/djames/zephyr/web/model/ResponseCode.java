package org.castle.djames.zephyr.web.model;

import lombok.Getter;

public enum ResponseCode {
    SUCCESS("S000", "Success."),
    ERROR("E000", "Error encountered while processing request."),
    VALIDATION_ERROR("VE001", "Validation Error encountered while processing request."),
    BUSINESS_ERROR("BE001", "Business Error encountered while processing request."),
    SERVICE_ERROR("SE001", "Service Error encountered while processing request."),
    ;

    @Getter
    private final String code;
    @Getter
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
