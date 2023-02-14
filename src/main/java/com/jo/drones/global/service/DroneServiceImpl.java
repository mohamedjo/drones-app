package com.jo.drones.global.service;

import com.jo.drones.global.entity.Drone;
import com.jo.drones.global.entity.Medication;
import com.jo.drones.global.entity.enums.State;
import com.jo.drones.global.exception.custom.AlreadyLoadedException;
import com.jo.drones.global.exception.custom.BatteryLowException;
import com.jo.drones.global.exception.custom.DroneNotFoundException;
import com.jo.drones.global.exception.custom.WeightLimitExceededException;
import com.jo.drones.global.repository.DroneRepository;
import com.jo.drones.global.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneServiceImpl implements DroneService {
    @Autowired
    DroneRepository droneRepository;

    @Autowired
    MedicationRepository medicationRepository;

    @Override
    public void registerDrone(Drone drone) {
        droneRepository.save(drone);
    }

    @Override
    public void loadDroneWithMedicationItems(String droneSerialNumber, List<Medication> medications) {
        Drone drone = droneRepository.findDroneBySerialNumber(droneSerialNumber);

        if (drone == null) {
            throw new DroneNotFoundException("there is no drone with this SerialNumber " + droneSerialNumber);
        } else if (drone.getState() != State.IDLE) {
            throw new AlreadyLoadedException("This Drone  is Loaded with other medications");
        } else if (drone.getWeightLimit() < calculateWeight(medications)) {
            throw new WeightLimitExceededException("total weight of  medications exceeded WeightLimit of the drone");
        } else if (drone.getBatteryCapacity() < 25) {
            throw new BatteryLowException("Drone Battery Level Lower Than 25");
        }

        medications.stream().forEach((medication) -> savaMedication(medication, drone));
        drone.setMedications(medications);
        drone.setState(State.LOADED);
        droneRepository.save(drone);

    }

    @Override
    public List<Medication> checkLoadedMedicationByDroneSerialNumber(String droneSerialNumber) {
        Drone drone = getDroneBySerialNumber(droneSerialNumber);
        if (drone == null) {
            throw new DroneNotFoundException("there is no drone with this SerialNumber " + droneSerialNumber);
        }

        long droneId = drone.getId();
        return medicationRepository.findMedicationsByDroneId(droneId);
    }

    @Override
    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }

    @Override
    public List<Drone> getAllAvailableDronesForLoading() {
        return droneRepository.findDroneByState(State.IDLE);
    }

    @Override
    public Drone getDroneBySerialNumber(String droneSerialNumber) {
        return droneRepository.findDroneBySerialNumber(droneSerialNumber);
    }

    @Override
    public int checkDroneBatteryLevel(String droneSerialNumber) {
        Drone drone = getDroneBySerialNumber(droneSerialNumber);
        if (drone == null) {
            throw new DroneNotFoundException("there is no drone with this SerialNumber " + droneSerialNumber);
        }

        return drone.getBatteryCapacity();
    }

    private void savaMedication(Medication medication, Drone drone) {
        medication.setDrone(drone);
        medicationRepository.save(medication);
    }

    private double calculateWeight(List<Medication> medications) {
        return medications.stream().mapToDouble(Medication::getWeight).sum();
    }
}
