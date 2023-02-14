package com.jo.drones.global.exception.custom;

public class AlreadyLoadedException extends RuntimeException {
    public AlreadyLoadedException(String message) {
        super(message);
    }

}
