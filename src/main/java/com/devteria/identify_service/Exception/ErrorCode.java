package com.devteria.identify_service.Exception;

public enum ErrorCode {
    USER_EXISTED(1001, "User already exists"),
    USERNAME_INVALID(1002, "Username must be at least 3 characters"),
    INVALID_PASSWORD(1003, "Password must be at least 3 characters"),
    INVALID_KEY(1004, "Invalid key"),
    USER_NOT_EXISTED(1005, "User not already exists"),
    ;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
