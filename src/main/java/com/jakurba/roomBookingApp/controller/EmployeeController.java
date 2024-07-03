package com.jakurba.roomBookingApp.controller;

import com.jakurba.roomBookingApp.model.Employee;
import com.jakurba.roomBookingApp.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/employees")
    public List<Employee> readEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping(value = "/employees/findById")
    public Employee findEmployeeById(@RequestBody Employee employee) {
        return employeeService.getEmployeeById(employee.getId());
    }

}
