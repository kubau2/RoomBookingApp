package com.jakurba.roomBookingApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakurba.roomBookingApp.controller.EmployeeController;
import com.jakurba.roomBookingApp.exceptions.UserNotFoundException;
import com.jakurba.roomBookingApp.model.Employee;
import com.jakurba.roomBookingApp.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService service;

    @Test
    void findEmployeeByIdIs200() throws Exception {
        Employee emp = new Employee(1L, "test@email.mail", "name", "surname");
        String requestBody = objectMapper.writeValueAsString(emp);

        //When an getEmployeeById is called, then emp will be returned
        when(service.getEmployeeById(1L)).thenReturn(emp);

        mockMvc.perform(get("/api/employees/findById").contentType("application/json").content(requestBody)).andExpect(status().isOk());
    }

    @Test
    void findEmployeeByIdIs404() throws Exception {
        Employee emp = new Employee(1L, "test@email.mail", "name", "surname");
        String requestBody = objectMapper.writeValueAsString(emp);

        //When getEmployeeById is called, then an error should be thrown
        when(service.getEmployeeById(1L)).thenThrow(UserNotFoundException.class);

        mockMvc.perform(get("/api/employees/findById").contentType("application/json").content(requestBody)).andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    void listAllEmployees() throws Exception {
        List<Employee> employeeList = new ArrayList<>();
        Employee emp1 = new Employee(1L, "test@email.mail", "name", "surname");
        Employee emp2 = new Employee(2L, "johny@email.mail", "Johny", "Blaze");
        employeeList.add(emp1);
        employeeList.add(emp2);

        when(service.getEmployees()).thenReturn(employeeList);

        mockMvc.perform(get("/api//employees").contentType("application/json")).andExpect(status().isOk()).andDo(print());
    }
}
