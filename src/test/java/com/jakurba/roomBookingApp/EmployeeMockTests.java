package com.jakurba.roomBookingApp;

import com.jakurba.roomBookingApp.exceptions.UserNotFoundException;
import com.jakurba.roomBookingApp.model.Employee;
import com.jakurba.roomBookingApp.repository.EmployeeRepository;
import com.jakurba.roomBookingApp.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmployeeMockTests {

    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    void findEmployeeByID() {
        //given
        Employee emp = new Employee(1L, "johny@email.com", "Johny", "Blaze");
        when(employeeRepository.findById(any())).thenReturn(Optional.of(emp));

        //when and then
        assertEquals(emp, employeeService.getEmployeeById(1L));
    }

    @Test
    void findEmployeeByID_UserNotFoundException() {
        //given
        when(employeeRepository.findById(any())).thenThrow(UserNotFoundException.class);

        //when and then
        assertThrows(UserNotFoundException.class, () -> employeeService.getEmployeeById(1L));
    }

    @Test
    void getAllEmployees() {
        // given
        List<Employee> employees = Arrays.asList(new Employee(1L, "johny@email.com", "Johny", "Blaze"), new Employee(2L, "jane@email.com", "Jane", "Doe"));
        when(employeeRepository.findAll()).thenReturn(employees);

        // when
        List<Employee> result = employeeService.getEmployees();

        // then
        assertEquals(employees.size(), result.size());
        assertEquals(employees.get(0), result.get(0));
        assertEquals(employees.get(1), result.get(1));
    }

    @Test
    void getAllEmployeesEmpty() {
        // given
        List<Employee> employees = Collections.emptyList();
        when(employeeRepository.findAll()).thenReturn(employees);

        // when
        List<Employee> result = employeeService.getEmployees();

        // then
        assertTrue(result.isEmpty());
    }


}
