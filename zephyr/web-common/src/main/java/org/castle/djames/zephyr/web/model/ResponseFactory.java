package org.castle.djames.zephyr.web.model;

import java.util.List;

public final class ResponseFactory {
    private ResponseFactory() {
    }

    // ========== SUCCESS ==========

    public static <T> Response<T> success(T body) {
        return success(ResponseCode.SUCCESS, body);
    }

    public static <T> Response<T> success(ResponseCode responseCode, T body) {
        return new Response<>(
                ResponseStatus.SUCCESS,
                responseCode.getCode(),
                responseCode.getMessage(),
                body,
                List.of()
        );
    }

    public static <T> Response<T> success(ResponseCode responseCode, String message, T body) {
        return new Response<>(
                ResponseStatus.SUCCESS,
                responseCode.getCode(),
                message != null ? message : responseCode.getCode(),
                body,
                List.of()
        );
    }

    public static <T> Response<T> success(String code, String message, T body) {
        return new Response<>(
                ResponseStatus.SUCCESS,
                code,
                message,
                body,
                List.of()
        );
    }


    // ========== FAILURE ==========

    public static <T> Response<T> failure(ErrorDetail error) {
        return failure(ResponseCode.ERROR, List.of(error));
    }

    public static <T> Response<T> failure(ResponseCode def, ErrorDetail error) {
        return failure(def, List.of(error));
    }

    public static <T> Response<T> failure(List<ErrorDetail> errors) {
        return failure(ResponseCode.ERROR, errors);
    }

    public static <T> Response<T> failure(ResponseCode responseCode, List<ErrorDetail> errors) {
        return new Response<>(
                ResponseStatus.ERROR,
                responseCode.getCode(),
                responseCode.getMessage(),
                null,
                errors
        );
    }

    public static <T> Response<T> failure(ResponseCode responseCode, String message, List<ErrorDetail> errors) {
        return new Response<>(
                ResponseStatus.ERROR,
                responseCode.getMessage(),
                message != null ? message : responseCode.getMessage(),
                null,
                errors
        );
    }

    public static <T> Response<T> failure(String code, String message, List<ErrorDetail> errors) {
        return new Response<>(
                ResponseStatus.ERROR,
                code,
                message,
                null,
                errors
        );
    }
}
