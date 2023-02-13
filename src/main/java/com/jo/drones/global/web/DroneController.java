package com.jo.drones.global.web;

import com.jo.drones.global.entity.Drone;
import com.jo.drones.global.entity.Medication;
import com.jo.drones.global.service.DroneService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api")
public class DroneController {

    @Autowired
    private DroneService droneService;

    @PostMapping("/drone")
    public ResponseEntity<Void> registerDrone(@RequestBody @NotNull Drone drone) {

        droneService.registerDrone(drone);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/loadDrone")
    public ResponseEntity<Void> loadingDroneWithMedication(@RequestBody List<Medication> medications,
                                                           @RequestParam("droneSerialNumber") String droneSerialNumber) {

        droneService.loadDroneWithMedicationItems(droneSerialNumber, medications);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/medication")
    public ResponseEntity<List<Medication>> checkLoadedMedicationByDroneSerialNumber(@RequestParam("droneSerialNumber") String droneSerialNumber) {

        List<Medication> medications = droneService.checkLoadedMedicationByDroneSerialNumber(droneSerialNumber);
        return new ResponseEntity<>(medications, HttpStatus.OK);
    }


    @GetMapping("/availableDrones")
    public ResponseEntity<List<Drone>> getAllAvailableDronesForLoading() {

        List<Drone> drones = droneService.getAllAvailableDronesForLoading();
        return new ResponseEntity<>(drones, HttpStatus.OK);

    }

    @GetMapping("/batteryLevel")
    public ResponseEntity<Integer> checkBatteryLevel(@RequestParam("droneSerialNumber") String droneSerialNumber) {

        int batteryLevel = droneService.checkDroneBatteryLevel(droneSerialNumber);
        return new ResponseEntity<>(batteryLevel, HttpStatus.OK);
    }
}
