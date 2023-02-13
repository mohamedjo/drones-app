package com.jo.drones.global.exception.custom;

public class BatteryLowException extends RuntimeException {
    public BatteryLowException(String message) {
        super(message);
    }

}
