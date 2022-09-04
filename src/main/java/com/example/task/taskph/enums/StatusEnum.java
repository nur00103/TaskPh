package com.example.task.taskph.enums;

public enum StatusEnum {

    STATUS_ONLINE("Online"),
    STATUS_OFFLINE("Ofline");

    private final String message;

    StatusEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
