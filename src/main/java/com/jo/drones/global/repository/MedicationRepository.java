package com.jo.drones.global.repository;

import com.jo.drones.global.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    List<Medication> findMedicationsByDroneId(long droneId);

}
