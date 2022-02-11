package com.rodrigoguides.springboot.service;

import com.rodrigoguides.springboot.model.Employee;
import com.rodrigoguides.springboot.repository.EmployeeRepository;
import com.rodrigoguides.springboot.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Optional;

public class EmployeeServiceTests {
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    //JUnit test for save Employee method
    @DisplayName("JUnit test for save Employee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Rodrigo")
                .lastName("Orellana")
                .email("rodrigo.orelana@gmail.com")
                .build();
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());

        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        //when - action or the behaviour we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        //then - verify the output
        Assertions.assertThat(savedEmployee).isNotNull();
    }
}
