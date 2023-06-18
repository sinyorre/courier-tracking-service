package com.one.couriertrackingservice.exception;

public class CourierNotFoundException extends RuntimeException{
    public CourierNotFoundException(String message) {
        super(message);
    }
}
