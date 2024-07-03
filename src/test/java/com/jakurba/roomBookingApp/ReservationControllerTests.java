package com.jakurba.roomBookingApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //Those tests work based on the preloaded data.sql

    @Test
    void createReservationIsOk() throws Exception {
        String requestBody = """
                {
                    "room_Id": 1,
                    "employee":{
                        "id":1
                    },
                    "room":{
                        "id":1
                    },
                    "reservationStart": "2024-02-05T08:25:43.511Z",
                    "reservationEnd": "2024-02-05T12:25:43.511Z"
                }""";
        String response = mockMvc.perform(post("/api/reservation/new").contentType("application/json").content(requestBody)).andReturn().getResponse().getContentAsString();
        Assertions.assertTrue(response.contains("Blue"));
        Assertions.assertTrue(response.contains("john.williams@test.com"));
        Assertions.assertTrue(response.contains("2024-02-05T12:25:43.511"));
    }

    @Test
    void createReservationIs400() throws Exception {
        String requestBody = """
                {
                    "room_Id": 1,
                    "employee":{
                        "id": InvalidID
                    },
                    "room":{
                        "id":1
                    },
                    "reservationStart": "2024-02-05T08:25:43.511Z",
                    "reservationEnd": "2024-02-05T12:25:43.511Z"
                }""";
        mockMvc.perform(post("/api/reservation/new").contentType("application/json").content(requestBody)).andExpect(status().isBadRequest()).andDo(print());
    }

    @Test
    void listAllReservations() throws Exception {
        createAnotherReservationIsOk();
        MockHttpServletResponse response = mockMvc.perform(get("/api/reservations").contentType("application/json")).andReturn().getResponse();
        String responseContent = response.getContentAsString();
        Assertions.assertTrue(responseContent.contains("Blue"));
    }

    @Test
    void deleteReservation() throws Exception {
        createAnotherReservationIsOk();
        String requestBody = """
                {
                    "reservationId": 1,
                    "employeeId": 1
                }""";
        MockHttpServletResponse response = mockMvc.perform(delete("/api/reservation").contentType("application/json").content(requestBody)).andReturn().getResponse();
        String responseContent = response.getContentAsString();
        Assertions.assertTrue(Boolean.parseBoolean(responseContent));
    }

    @Test
    void deleteReservationNotFound() throws Exception {
        String requestBody = """
                {
                    "reservationId": 1,
                    "employeeId": 1
                }""";
        mockMvc.perform(delete("/api/reservation").contentType("application/json").content(requestBody)).andExpect(status().isNotFound()).andDo(print());
    }

    private void createAnotherReservationIsOk() throws Exception {
        String requestBody = """
                {
                    "room_Id": 1,
                    "employee":{
                        "id":1
                    },
                    "room":{
                        "id":1
                    },
                    "reservationStart": "2024-02-05T14:25:43.511Z",
                    "reservationEnd": "2024-02-05T15:25:43.511Z"
                }""";
        mockMvc.perform(post("/api/reservation/new").contentType("application/json").content(requestBody)).andReturn().getResponse().getContentAsString();
    }

}
