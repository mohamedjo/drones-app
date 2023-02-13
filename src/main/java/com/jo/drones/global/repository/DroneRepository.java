package com.jo.drones.global.repository;

import com.jo.drones.global.entity.Drone;
import com.jo.drones.global.entity.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {

    Drone findDroneBySerialNumber(String serialNumber);

    List<Drone> findDroneByState(State State);

}
