package com.jakurba.roomBookingApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jakurba.roomBookingApp.model.Employee;
import com.jakurba.roomBookingApp.model.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoomMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //Those tests work based on the preloaded data.sql

    @Test
    void listAllRooms() throws Exception {
        //when and then
        MockHttpServletResponse response = mockMvc.perform(get("/api/rooms").contentType("application/json")).andReturn().getResponse();
        String responseContent = response.getContentAsString();
        assertTrue(responseContent.contains("Blue"));
    }

    @Test
    void findRoomById() throws Exception {
        //when and then
        Room room = new Room(1L, null, null, null, null, false, false);
        String requestBody = objectMapper.writeValueAsString(room);
        MockHttpServletResponse response = mockMvc.perform(get("/api/rooms/findById").contentType("application/json").content(requestBody)).andReturn().getResponse();
        String responseContent = response.getContentAsString();
        assertTrue(responseContent.contains("Blue"));
    }

}
