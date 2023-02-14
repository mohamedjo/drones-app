package com.jo.drones.global.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jo.drones.global.entity.Drone;
import com.jo.drones.global.entity.Medication;
import com.jo.drones.global.entity.enums.State;
import com.jo.drones.global.exception.custom.AlreadyLoadedException;
import com.jo.drones.global.exception.custom.BatteryLowException;
import com.jo.drones.global.exception.custom.DroneNotFoundException;
import com.jo.drones.global.exception.custom.WeightLimitExceededException;
import com.jo.drones.global.repository.DroneRepository;
import com.jo.drones.global.repository.MedicationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.jo.drones.global.fixture.DroneFixture.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DroneServiceImplTest {

    @Mock
    private DroneRepository droneRepository;
    @Mock
    private MedicationRepository medicationRepository;

    @InjectMocks
    private DroneServiceImpl classUnderTest;


    @Test
    void givenDrone_whenRegisterDrone_thenSaveIsCalled() throws JsonProcessingException {

        Drone drone = getDrone();
        when(droneRepository.save(drone)).thenReturn(drone);

        classUnderTest.registerDrone(drone);

        verify(droneRepository, times(1)).save(eq(drone));

    }

    @Test
    void givenValidDroneSerialNumberAndValidListOfMedications_whenLoadDroneWithMedicationItems_thenSaveIsCalled() throws JsonProcessingException {

        Drone drone = getDrone();
        List<Medication> medications = getMedications();
        String serialNumber = "ABCD";
        when(droneRepository.findDroneBySerialNumber(serialNumber)).thenReturn(drone);

        classUnderTest.loadDroneWithMedicationItems(serialNumber, medications);

        verify(droneRepository, times(1)).save(eq(drone));

    }

    @Test
    void givenNotExistSerialNumber_whenLoadDroneWithMedicationItems_thenDroneNotFoundExceptionThrown() throws JsonProcessingException {

        List<Medication> medications = getMedicationsWithHeavyWeight();
        String serialNumber = "ABCD";
        when(droneRepository.findDroneBySerialNumber(serialNumber)).thenReturn(null);


        DroneNotFoundException thrown = Assertions.assertThrows(DroneNotFoundException.class, () -> {
            classUnderTest.loadDroneWithMedicationItems(serialNumber, medications);
        }, "DroneNotFoundException was expected");

        Assertions.assertEquals("there is no drone with this SerialNumber " + serialNumber, thrown.getMessage());

    }

    @Test
    void givenLoadedDroneSerialNumber_whenLoadDroneWithMedicationItems_thenAlreadyLoadedExceptionThrown() throws JsonProcessingException {

        Drone drone = getLoadedDrone();
        List<Medication> medications = getMedicationsWithHeavyWeight();
        String serialNumber = "ABCD";
        when(droneRepository.findDroneBySerialNumber(serialNumber)).thenReturn(drone);


        AlreadyLoadedException thrown = Assertions.assertThrows(AlreadyLoadedException.class, () -> {
            classUnderTest.loadDroneWithMedicationItems(serialNumber, medications);
        }, "AlreadyLoadedException was expected");

        Assertions.assertEquals("This Drone  is Loaded with other medications", thrown.getMessage());

    }

    @Test
    void givenMedicationsWeighsBiggerThanDroneWeightLimit_whenLoadDroneWithMedicationItems_thenWeightLimitExceededExceptionThrown() throws JsonProcessingException {

        Drone drone = getDrone();
        List<Medication> medications = getMedicationsWithHeavyWeight();
        String serialNumber = "ABCD";
        when(droneRepository.findDroneBySerialNumber(serialNumber)).thenReturn(drone);


        WeightLimitExceededException thrown = Assertions.assertThrows(WeightLimitExceededException.class, () -> {
            classUnderTest.loadDroneWithMedicationItems(serialNumber, medications);
        }, "WeightLimitExceededException was expected");

        Assertions.assertEquals("total weight of  medications exceeded WeightLimit of the drone", thrown.getMessage());

    }

    @Test
    void givenSerialNumberForLowCapactyBatteryDrone_whenLoadDroneWithMedicationItems_thenDroneNotFoundExceptionThrown() throws JsonProcessingException {
        Drone drone = getDroneWithBatteryLowLevel();

        List<Medication> medications = getMedications();
        String serialNumber = "ABCD";
        when(droneRepository.findDroneBySerialNumber(serialNumber)).thenReturn(drone);


        BatteryLowException thrown = Assertions.assertThrows(BatteryLowException.class, () -> {
            classUnderTest.loadDroneWithMedicationItems(serialNumber, medications);
        }, "DroneNotFoundException was expected");

        Assertions.assertEquals("Drone Battery Level Lower Than 25", thrown.getMessage());

    }

    @Test
    void givenValidDroneSerialNumber_whenCheckLoadedMedicationByDroneSerialNumber_thenFindMedicationsByDroneIdsCalledAndListOfMedicationsReturned() throws JsonProcessingException {

        Drone drone = getDrone();

        List<Medication> medications = getMedications();
        when(droneRepository.findDroneBySerialNumber(drone.getSerialNumber())).thenReturn(drone);
        when(medicationRepository.findMedicationsByDroneId(1)).thenReturn(medications);

        List<Medication> actualResponse = classUnderTest.checkLoadedMedicationByDroneSerialNumber(drone.getSerialNumber());

        verify(medicationRepository, times(1)).findMedicationsByDroneId(1L);
        Assertions.assertNotNull(actualResponse);

    }

    @Test
    void givenNotValidDroneSerialNumber_whenCheckLoadedMedicationByDroneSerialNumber_thenDroneNotFoundExceptionThrown() throws JsonProcessingException {

        String serialNumber = "ABCD";
        when(droneRepository.findDroneBySerialNumber(serialNumber)).thenReturn(null);


        DroneNotFoundException thrown = Assertions.assertThrows(DroneNotFoundException.class, () -> {
            classUnderTest.checkLoadedMedicationByDroneSerialNumber(serialNumber);
        }, "DroneNotFoundException was expected");

        Assertions.assertEquals("there is no drone with this SerialNumber " + serialNumber, thrown.getMessage());

    }

    @Test
    void givenNothing_whenGetAllDrones_thenFindAllIsCalledAndListOfDronesReturned() throws JsonProcessingException {

        List<Drone> drones = getDrones();

        when(droneRepository.findAll()).thenReturn(drones);

        List<Drone> actualResponse = classUnderTest.getAllDrones();

        verify(droneRepository, times(1)).findAll();
        Assertions.assertNotNull(actualResponse);

    }

    @Test
    void givenNothing_whenGetAllAvailableDronesForLoading_thenFindDroneByStateIsCalledAndListOfDronesReturned() throws JsonProcessingException {

        List<Drone> drones = getDrones();

        when(droneRepository.findDroneByState(eq(State.IDLE))).thenReturn(drones);

        List<Drone> actualResponse = classUnderTest.getAllAvailableDronesForLoading();

        verify(droneRepository, times(1)).findDroneByState(eq(State.IDLE));
        Assertions.assertNotNull(actualResponse);

    }

    @Test
    void givenExistSerialNumber_whenGetDroneBySerialNumber_thenFindDroneByStateIsCalledAndListOfDronesReturned() throws JsonProcessingException {

        Drone drone = getDrone();

        when(droneRepository.findDroneBySerialNumber(drone.getSerialNumber())).thenReturn(drone);

        Drone actualResponse = classUnderTest.getDroneBySerialNumber(drone.getSerialNumber());

        verify(droneRepository, times(1)).findDroneBySerialNumber(eq(drone.getSerialNumber()));
        Assertions.assertNotNull(actualResponse);

    }

    @Test
    void givenExistSerialNumber_whenCheckDroneBatteryLevel_thenBatteryLevelReturned() throws JsonProcessingException {

        Drone drone = getDrone();

        when(droneRepository.findDroneBySerialNumber(drone.getSerialNumber())).thenReturn(drone);

        int actualResponse = classUnderTest.checkDroneBatteryLevel(drone.getSerialNumber());


        Assertions.assertEquals(drone.getBatteryCapacity(), actualResponse);

    }


    @Test
    void givenNotExistSerialNumber_whenCheckDroneBatteryLevel_thenDroneNotFoundExceptionThrown() throws JsonProcessingException {

        String serialNumber = "ABCD";

        when(droneRepository.findDroneBySerialNumber(serialNumber)).thenReturn(null);

        DroneNotFoundException thrown = Assertions.assertThrows(DroneNotFoundException.class, () -> {
            classUnderTest.checkDroneBatteryLevel(serialNumber);
        }, "DroneNotFoundException was expected");

        Assertions.assertEquals("there is no drone with this SerialNumber " + serialNumber, thrown.getMessage());


    }
}
