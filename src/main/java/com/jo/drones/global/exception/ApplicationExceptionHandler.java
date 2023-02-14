package com.jo.drones.global.exception;

import com.jo.drones.global.exception.custom.AlreadyLoadedException;
import com.jo.drones.global.exception.custom.BatteryLowException;
import com.jo.drones.global.exception.custom.DroneNotFoundException;
import com.jo.drones.global.exception.custom.WeightLimitExceededException;
import com.jo.drones.global.model.CustomErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.jo.drones.global.exception.constant.ExceptionHandlerConstants.*;


@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(DroneNotFoundException.class)
    public ResponseEntity<?> handleDroneNotFoundException(DroneNotFoundException droneNotFoundException, WebRequest request) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(USERNAME_NOT_EXIST_STATUS_CODE, droneNotFoundException.getMessage());
        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WeightLimitExceededException.class)
    public ResponseEntity<?> handleWeightLimitExceededException(WeightLimitExceededException weightLimitExceededException, WebRequest request) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(BAD_REQUEST_STATUS_CODE, weightLimitExceededException.getMessage());
        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BatteryLowException.class)
    public ResponseEntity<?> handleBatteryLowException(BatteryLowException batteryLowException, WebRequest request) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(DRONE_INTERNAL_ERROR_STATUS_CODE, batteryLowException.getMessage());
        return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AlreadyLoadedException.class)
    public ResponseEntity<?> handleAlreadyLoadedException(AlreadyLoadedException alreadyLoadedException, WebRequest request) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(DRONE_INTERNAL_ERROR_STATUS_CODE, alreadyLoadedException.getMessage());
        return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException constraintViolationException, WebRequest request) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(BAD_REQUEST_STATUS_CODE, constraintViolationException.getMessage());
        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }



}
