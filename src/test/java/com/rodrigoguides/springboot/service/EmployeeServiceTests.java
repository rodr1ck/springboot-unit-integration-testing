package com.rodrigoguides.springboot.service;

import com.rodrigoguides.springboot.model.Employee;
import com.rodrigoguides.springboot.repository.EmployeeRepository;
import com.rodrigoguides.springboot.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstName("Rodrigo")
                .lastName("Orellana")
                .email("rodrigo.orelana@gmail.com")
                .build();
    }

    //JUnit test for save Employee method
    @DisplayName("JUnit test for save Employee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        //given - precondition or setup

        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());

        given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        //when - action or the behaviour we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        //then - verify the output
        Assertions.assertThat(savedEmployee).isNotNull();
    }
}
