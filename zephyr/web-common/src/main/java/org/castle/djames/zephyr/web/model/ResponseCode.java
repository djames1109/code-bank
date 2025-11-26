package org.castle.djames.zephyr.web.model;

import lombok.Getter;

public enum ResponseCode {
    SUCCESS("S000", "Success."),
    ERROR("E000", "Error encountered while processing request."),
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
