package com.jo.drones.global.configuration;

import com.jo.drones.global.entity.Drone;
import com.jo.drones.global.entity.Medication;
import com.jo.drones.global.entity.enums.Model;
import com.jo.drones.global.entity.enums.State;
import com.jo.drones.global.repository.DroneRepository;
import com.jo.drones.global.repository.MedicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(DroneRepository droneRepository, MedicationRepository medicationRepository) {

        return args -> {


            Drone drone = new Drone();
            drone.setSerialNumber("VVVTVmt");
            drone.setModel(Model.LightWeight);
            drone.setWeightLimit(50);
            drone.setBatteryCapacity(75);
            drone.setState(State.IDLE);

            Drone drone2 = new Drone();
            drone2.setSerialNumber("ABCD123");
            drone2.setModel(Model.MiddleWeight);
            drone2.setWeightLimit(200);
            drone2.setBatteryCapacity(80);
            drone2.setState(State.DELIVERING);

            Drone drone3 = new Drone();
            drone3.setSerialNumber("ABCD123");
            drone3.setModel(Model.MiddleWeight);
            drone3.setWeightLimit(500);
            drone3.setBatteryCapacity(80);
            drone3.setState(State.LOADED);


            Medication medication = new Medication();
            medication.setCode("ABCD");
            medication.setName("name1");
            medication.setWeight(21);
            medication.setDrone(drone2);

            Medication medication2 = new Medication();
            medication2.setCode("MJOI");
            medication2.setName("name2");
            medication2.setWeight(50);
            medication2.setDrone(drone2);

            Medication medication3 = new Medication();
            medication3.setCode("MJIO");
            medication3.setName("name3");
            medication3.setWeight(70);
            medication3.setDrone(drone3);

            Medication medication4 = new Medication();
            medication4.setCode("XZY");
            medication4.setName("name4");
            medication4.setWeight(180);
            medication4.setDrone(drone3);


            List<Medication> medicationList2 = new ArrayList<>();
            medicationList2.add(medication2);
            medicationList2.add(medication);

            List<Medication> medicationList3 = new ArrayList<>();
            medicationList3.add(medication3);
            medicationList3.add(medication3);

            drone2.setMedications(medicationList2);
            drone3.setMedications(medicationList3);

            log.info("Preloading " + droneRepository.save(drone));
            log.info("Preloading " + droneRepository.save(drone2));
            log.info("Preloading " + droneRepository.save(drone3));
            log.info("Preloading " + medicationRepository.save(medication));
            log.info("Preloading " + medicationRepository.save(medication2));
            log.info("Preloading " + medicationRepository.save(medication3));
            log.info("Preloading " + medicationRepository.save(medication4));


        };
    }
}

