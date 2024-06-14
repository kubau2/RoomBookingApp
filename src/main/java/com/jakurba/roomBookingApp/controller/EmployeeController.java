package com.jakurba.roomBookingApp.controller;

import com.jakurba.roomBookingApp.exceptions.UserNotFoundException;
import com.jakurba.roomBookingApp.model.Employee;
import com.jakurba.roomBookingApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping(value = "/employees")
    public ResponseEntity<Object> readEmployees() {
        return ResponseEntity.ok(employeeService.getEmployees());
    }

    @GetMapping(value = "/employees/findById")
    public ResponseEntity<Object> findEmployeeById(@RequestBody Employee employee) {
        try {
            Employee emp = employeeService.getEmployeeById(employee.getId());
            return ResponseEntity.ok(emp);

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    @GetMapping(value = "/employees/findById")
    public Employee findEmployeeById(@RequestBody Employee employee) {
        return employeeService.getEmployeeById(employee.getId());
    }

}
