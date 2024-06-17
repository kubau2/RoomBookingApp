package com.jakurba.roomBookingApp;

import com.jakurba.roomBookingApp.exceptions.UserNotFoundException;
import com.jakurba.roomBookingApp.repository.EmployeeRepository;
import com.jakurba.roomBookingApp.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployeeMockTests {


    //Those tests work based on the preloaded data.sql

//    @Test
//    void findEmployeeByIdIs200() throws Exception {
//        //given
//        Employee emp = new Employee(1L, "test@email.mail", "name", "surname");
//        String requestBody = objectMapper.writeValueAsString(emp);
//
//        //when and then
//        mockMvc.perform(get("/api/employees/findById").contentType("application/json").content(requestBody)).andExpect(status().isOk());
//    }

    @Test
    void findEmployeeByIdIs404() throws Exception {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        when(employeeRepository.findById(any())).thenThrow(UserNotFoundException.class);

        EmployeeService employeeService = new EmployeeService(employeeRepository);

        //when and then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> employeeService.getEmployeeById(1L));
    }

}
