package com.jo.drones.global.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jo.drones.global.DronesAppApplication;
import com.jo.drones.global.TestUtils;
import com.jo.drones.global.entity.Drone;
import com.jo.drones.global.entity.Medication;
import com.jo.drones.global.exception.ApplicationExceptionHandler;
import com.jo.drones.global.service.DroneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.jo.drones.global.fixture.DroneFixture.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = DronesAppApplication.class)
@AutoConfigureMockMvc
public class DroneControllerTest {

    public static final String POST_DRONE_REQUEST_URL = "/api/drone";
    public static final String POST_LOAD_DRONE_REQUEST_URL = "/api/loadDrone";

    public static final String GET_MEDICATIONS_LOADED_REQUEST_URL = "/api/medication";

    public static final String GET_AVAILABLE_DRONES_REQUEST_URL = "/api/availableDrones";
    public static final String GET_BATTERY_LEVEL_REQUEST_URL = "/api/batteryLevel";


    private MockMvc mockMvc;

    @MockBean
    private DroneService droneService;

    @Autowired
    private DroneController classUnderTest;

    @BeforeEach
    public void init() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest)
                .setControllerAdvice(new ApplicationExceptionHandler()).build();
    }

    @Test
    void givenValidDroneAsBody_whenRequestIsReceived_ThenStatusIs201() throws Exception {

        Drone drone = getDrone();
        doNothing().when(droneService).registerDrone(drone);
        String requestBody = TestUtils.getMapper()
                .writeValueAsString(drone);

        mockMvc.perform(post(POST_DRONE_REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    void givenEmptyBody_whenRequestIsReceived_ThenStatusIs400() throws Exception {

        mockMvc.perform(post(POST_DRONE_REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenValidMedicationAndSerialNumber_whenRequestIsReceived_ThenStatusIs201() throws Exception {


        List<Medication> medications = getMedications();
        String serialNumber = "ABCD1234";
        doNothing().when(droneService).loadDroneWithMedicationItems(serialNumber, medications);
        String requestBody = TestUtils.getMapper()
                .writeValueAsString(medications);

        mockMvc.perform(post(POST_LOAD_DRONE_REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("droneSerialNumber", serialNumber)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    void givenEmptyBody_whenLoadDroneRequestIsReceived_ThenStatusIs400() throws Exception {
        String serialNumber = "ABCD1234";

        mockMvc.perform(post(POST_LOAD_DRONE_REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("droneSerialNumber", serialNumber)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void GivenEmptyDroneSerialNumber_whenLoadDroneRequestIsReceived_ThenStatusIs400() throws Exception {

        List<Medication> medications = getMedications();
        String requestBody = TestUtils.getMapper()
                .writeValueAsString(medications);
        mockMvc.perform(post(POST_LOAD_DRONE_REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenDroneSerialNumber_whenGetMedicationsLoadedRequestIsReceived_ThenStatusIs201AndListOfMedicationsReturned() throws Exception {
        String serialNumber = "ABCD1234";
        List<Medication> medications = getMedications();
        when(droneService.checkLoadedMedicationByDroneSerialNumber(serialNumber)).thenReturn(medications);


        MvcResult mvcResult = mockMvc.perform(get(GET_MEDICATIONS_LOADED_REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("droneSerialNumber", serialNumber))
                .andExpect(status().isOk())
                .andReturn();


        ObjectMapper objectMapper = TestUtils.getMapper();
        List<Medication> actualResponseBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Medication>>() {
        });
        assertNotNull(actualResponseBody);
    }

    @Test
    void GivenEmptyDroneSerialNumber_whenGetMedicationsLoadedRequestIsReceived_ThenStatusIs400() throws Exception {

        mockMvc.perform(get(GET_MEDICATIONS_LOADED_REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void whenGetAvailableDronesRequestIsReceived_ThenStatusIs201AndReturnedListOfDrones() throws Exception {
        List<Drone> drones = getDrones();
        when(droneService.getAllAvailableDronesForLoading()).thenReturn(drones);


        MvcResult mvcResult = mockMvc.perform(get(GET_AVAILABLE_DRONES_REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();


        ObjectMapper objectMapper = TestUtils.getMapper();
        List<Drone> actualResponseBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Drone>>() {
        });
        assertNotNull(actualResponseBody);
    }

    @Test
    void givenDroneSerialNumber_whenGetBatteryLevelRequestIsReceived_ThenStatusIs201AndReturnedBatteryLevel() throws Exception {
        String serialNumber = "ABCD123";
        when(droneService.checkDroneBatteryLevel("")).thenReturn(40);


        MvcResult mvcResult = mockMvc.perform(get(GET_BATTERY_LEVEL_REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("droneSerialNumber", serialNumber)
                )
                .andExpect(status().isOk())
                .andReturn();
        ObjectMapper objectMapper = TestUtils.getMapper();
        double actualResponseBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(actualResponseBody);
    }

    @Test
    void NotGivenDroneSerialNumber_whenGetBatteryLevelRequestIsReceived_ThenStatusIs400() throws Exception {

        mockMvc.perform(get(GET_BATTERY_LEVEL_REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }




 /*   @Test
    void givenValidUserName_whenRequestIsReceived_ThenStatusIs200() throws Exception {
        doReturn(TaskFixture.getListOfRepoResponse()).when(taskService).getAllGitHubReposByUserName(anyString());

        mockMvc.perform(get(GET_REPOS_BY_USERNAME_REQUEST_URL_EXAMPLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk());
    }

    @Test
    void givenValidUserNameAnd_whenRequestIsReceived_ThenStatusIs200() throws Exception {
        doReturn(TaskFixture.getListOfRepoResponse()).when(taskService).getAllGitHubReposByUserName(anyString());

        mockMvc.perform(get(GET_REPOS_BY_USERNAME_REQUEST_URL_EXAMPLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_XML)
                        .content(""))
                .andExpect(status().isNotAcceptable());
    }

*/
}

