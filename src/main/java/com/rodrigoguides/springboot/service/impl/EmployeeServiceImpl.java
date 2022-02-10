package com.rodrigoguides.springboot.service.impl;

import com.rodrigoguides.springboot.exception.ResourceNotFoundException;
import com.rodrigoguides.springboot.model.Employee;
import com.rodrigoguides.springboot.repository.EmployeeRepository;
import com.rodrigoguides.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {

        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()) {
            throw new ResourceNotFoundException("Employee already exists with given email: " + employee.getEmail());
        }

        return employeeRepository.save(employee);
    }
}
