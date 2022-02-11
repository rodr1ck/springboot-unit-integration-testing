package com.rodrigoguides.springboot.service;

import com.rodrigoguides.springboot.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();
}
