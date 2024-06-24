package com.jakurba.roomBookingApp;

import com.jakurba.roomBookingApp.exceptions.UserNotFoundException;
import com.jakurba.roomBookingApp.model.Employee;
import com.jakurba.roomBookingApp.repository.EmployeeRepository;
import com.jakurba.roomBookingApp.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployeeMockTests {

    @Test
    void findEmployeeByIdIs200() {
        //given
        Employee emp = new Employee(1L, "johny@email.com", "Johny", "Blaze");

        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        when(employeeRepository.findById(any())).thenReturn(Optional.of(emp));

        EmployeeService employeeService = new EmployeeService(employeeRepository);

        //when and then
        assertEquals(emp, employeeService.getEmployeeById(1L));
    }

    @Test
    void findEmployeeByIdIs404() {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        when(employeeRepository.findById(any())).thenThrow(UserNotFoundException.class);

        EmployeeService employeeService = new EmployeeService(employeeRepository);

        //when and then
        assertThrows(UserNotFoundException.class, () -> employeeService.getEmployeeById(1L));
    }

}
