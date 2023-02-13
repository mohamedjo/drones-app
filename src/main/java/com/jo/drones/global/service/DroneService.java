package com.jo.drones.global.service;

import com.jo.drones.global.entity.Drone;
import com.jo.drones.global.entity.Medication;

import java.util.List;

public interface DroneService {

    void registerDrone(Drone drone);

    void loadDroneWithMedicationItems(String droneSerialNumber, List<Medication> medications);

    List<Medication> checkLoadedMedicationByDroneSerialNumber(String droneSerialNumber);

    List<Drone> getAllDrones();

    List<Drone> getAllAvailableDronesForLoading();

    Drone getDroneBySerialNumber(String droneSerialNumber);

    int checkDroneBatteryLevel(String droneSerialNumber);
}


