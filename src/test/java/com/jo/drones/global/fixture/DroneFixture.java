package com.jo.drones.global.fixture;

import com.jo.drones.global.entity.Drone;
import com.jo.drones.global.entity.Medication;
import com.jo.drones.global.entity.enums.Model;
import com.jo.drones.global.entity.enums.State;

import java.util.ArrayList;
import java.util.List;

public class DroneFixture {

    public static Drone getDrone() {


        Drone drone = new Drone();
        drone.setSerialNumber("VVV215TVMT");
        drone.setId(1L);
        drone.setModel(Model.LightWeight);
        drone.setWeightLimit(50);
        drone.setBatteryCapacity(75);
        drone.setState(State.IDLE);

        return drone;
    }

    public static Drone getLoadedDrone() {


        Drone drone = new Drone();
        drone.setSerialNumber("VVV215TVMT");
        drone.setId(1L);
        drone.setModel(Model.LightWeight);
        drone.setWeightLimit(50);
        drone.setBatteryCapacity(75);
        drone.setState(State.LOADED);

        return drone;
    }

    public static Drone getDroneWithBatteryLowLevel() {


        Drone drone = new Drone();
        drone.setSerialNumber("VVV215TVMT");
        drone.setModel(Model.LightWeight);
        drone.setWeightLimit(50);
        drone.setBatteryCapacity(15);
        drone.setState(State.IDLE);

        return drone;
    }


    public static List<Medication> getMedications() {


        List<Medication> medicationSet = new ArrayList<>();
        Medication medication = new Medication();
        medication.setCode("MGKS");
        medication.setName("mnj_gvgv");
        medication.setWeight(21);
        medicationSet.add(medication);
        medication = new Medication();
        medication.setCode("ABN");
        medication.setName("coli_ri");
        medication.setWeight(40);


        return medicationSet;

    }

    public static List<Medication> getMedicationsWithHeavyWeight() {


        List<Medication> medicationSet = new ArrayList<>();
        Medication medication = new Medication();
        medication.setCode("MGKS");
        medication.setName("mnj_gvgv");
        medication.setWeight(80);
        medicationSet.add(medication);
        medication = new Medication();
        medication.setCode("ABN");
        medication.setName("coli_ri");
        medication.setWeight(40);


        return medicationSet;

    }

    public static List<Drone> getDrones() {
        List<Drone> drones = new ArrayList<>();
        Drone drone = new Drone();
        drone.setSerialNumber("VVV215TVMT");
        drone.setModel(Model.LightWeight);
        drone.setWeightLimit(50);
        drone.setBatteryCapacity(75);
        drone.setState(State.IDLE);

        drones.add(drone);

        drone = new Drone();
        drone.setSerialNumber("AMNK");
        drone.setModel(Model.HeavyWeight);
        drone.setWeightLimit(400);
        drone.setBatteryCapacity(85);
        drone.setState(State.IDLE);

        drones.add(drone);

        return drones;

    }
}
