package com.jo.drones.global.exception.custom;

public class WeightLimitExceededException extends RuntimeException{
    public WeightLimitExceededException(String message) {
        super(message);
    }

}
