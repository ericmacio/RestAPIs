package com.eric.restapi.car_rental_service.model;

import org.springframework.http.HttpStatus;

public class ErrorMessage {

    private HttpStatus status;
    private String message;

    public ErrorMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
