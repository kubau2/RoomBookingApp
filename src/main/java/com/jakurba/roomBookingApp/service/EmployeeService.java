package com.jakurba.roomBookingApp.service;

import com.jakurba.roomBookingApp.model.Employee;
import com.jakurba.roomBookingApp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;


    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }



}
