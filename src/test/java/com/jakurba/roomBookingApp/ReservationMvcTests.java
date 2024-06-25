package com.jakurba.roomBookingApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jakurba.roomBookingApp.model.Employee;
import com.jakurba.roomBookingApp.model.Reservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //Those tests work based on the preloaded data.sql

    @Test
    void findEmployeeByIdIs404() throws Exception {
        Employee emp = new Employee(2L, "test@email.mail", "name", "surname");

        String requestBody = objectMapper.writeValueAsString(emp);

        mockMvc.perform(get("/api/employees/findById").contentType("application/json").content(requestBody)).andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    void listAllEmployees() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/api/employees").contentType("application/json")).andReturn().getResponse();
        String responseContent = response.getContentAsString();
        List<Employee> employeeList = new ArrayList<>();
        Gson g = new Gson();
        String[] responseSplitToArray = responseContent.split("\\[");
        for (int i = 1; i < responseSplitToArray.length; i++) {
            String employeeEntry = responseSplitToArray[i].replace("]", "");
            employeeList.add(g.fromJson(employeeEntry, Employee.class));
        }
        assertEquals("john.williams@test.com", employeeList.getFirst().getEmail());
    }
}
