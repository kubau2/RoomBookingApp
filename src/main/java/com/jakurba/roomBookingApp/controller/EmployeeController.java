package com.jakurba.roomBookingApp.controller;

import com.jakurba.roomBookingApp.model.Employee;
import com.jakurba.roomBookingApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping(value = "/employee/new")
    public Employee createEmployee(@RequestBody Employee employee) {
        //TODO
        return null;
//        return employeeService.createClient(client);
    }

    @GetMapping(value = "/employees")
    public List<Employee> readEmployees() {
        return employeeService.getEmployees();
    }

    @PutMapping(value = "/employee/update")
    public Employee readEmployees(@RequestBody Employee employee) {
        //TODO
        return null;
//        return employeeService.updateClient(client);
    }
}
