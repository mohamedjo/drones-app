package com.jo.drones.global.task;

import com.jo.drones.global.entity.Drone;
import com.jo.drones.global.service.DroneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DronePeriodicTask {

    @Autowired
    DroneService droneService;


    @Scheduled(fixedDelay = 600000)
    public void execute() {

        List<Drone> drones = droneService.getAllDrones();

        drones.stream().forEach((drone) -> log.info("drone with serial number {} have battery capacity {}", drone.getSerialNumber(),
                drone.getBatteryCapacity()));

    }

}
