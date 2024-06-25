package com.eric.restapi.user_service.model;

public enum Constants {
    EMAIL_REGEXP("^(.+)@(.+)$");

    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
