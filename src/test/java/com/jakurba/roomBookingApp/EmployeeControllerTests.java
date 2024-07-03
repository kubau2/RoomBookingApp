package com.jakurba.roomBookingApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakurba.roomBookingApp.model.Employee;
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
class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //Those tests work based on the preloaded data.sql

    @Test
    void findEmployeeByIdIsOk() throws Exception {
        //given
        Employee emp = new Employee(1L, "test@email.mail", "name", "surname");
        String requestBody = objectMapper.writeValueAsString(emp);

        //when and then
        mockMvc.perform(get("/api/employees/findById").contentType("application/json").content(requestBody)).andExpect(status().isOk());
    }

    @Test
    void findEmployeeByIdIs404() throws Exception {
        Employee emp = new Employee(2L, "test@email.mail", "name", "surname");

        String requestBody = objectMapper.writeValueAsString(emp);

        mockMvc.perform(get("/api/employees/findById").contentType("application/json").content(requestBody)).andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    void listAllEmployees() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/api/employees").contentType("application/json"))
                .andReturn().getResponse();
        String responseContent = response.getContentAsString();
        Gson gson = new Gson();
        TypeToken<List<Employee>> token = new TypeToken<>() {};
        List<Employee> employeeList = gson.fromJson(responseContent, token.getType());

        assertEquals("john.williams@test.com", employeeList.getFirst().getEmail());
    }
}
