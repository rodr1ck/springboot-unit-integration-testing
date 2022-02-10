package com.rodrigoguides.springboot.repository;

import com.rodrigoguides.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
