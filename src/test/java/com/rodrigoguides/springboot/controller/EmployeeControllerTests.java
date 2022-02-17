package com.rodrigoguides.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodrigoguides.springboot.model.Employee;
import com.rodrigoguides.springboot.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.*;
//import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.CoreMatchers.*;

@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    //JUnit test for EmployeeController createEmployee method
    @DisplayName("JUnit test for EmployeeController createEmployee method")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Domingo")
                .lastName("Rivera")
                .email("domingo.rivera@gmail.com")
                .build();
        given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when - action or the behaviour we are going to test
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    //JUnit test for EmployeeController getAllEmployees method
    @DisplayName("JUnit test for EmployeeController getAllEmployees method")
    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{
        //given - precondition or setup
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("Rodrigo").lastName("Orellana").email("rodrigo.orelana@gmail.com").build());
        listOfEmployees.add(Employee.builder().firstName("Domingo").lastName("Rivera").email("domingo.rivera@gmail.com").build());
        given(employeeService.getAllEmployees()).willReturn(listOfEmployees);

        //when - action or the behaviour we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listOfEmployees.size())));
    }

    //positive scenario - valid employee id
    //JUnit test for EmployeeController getEmployee by method
        @Test
        public void givenEmployeeId_whenGetEmployeeById_thenEmployeeObject() throws Exception{
            //given - precondition or setup
            long employeeId = 1L;
            Employee employee = Employee.builder()
                    .firstName("Domingo")
                    .lastName("Rivera")
                    .email("domingo.rivera@gmail.com")
                    .build();
            given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

            //when - action or the behaviour we are going to test
            ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

            //then - verify the output
            response.andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                    .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                    .andExpect(jsonPath("$.email", is(employee.getEmail())));
        }

    //negative scenario - invalid employee id
    //JUnit test for EmployeeController getEmployee by method
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenEmpty() throws Exception{
        //given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Domingo")
                .lastName("Rivera")
                .email("domingo.rivera@gmail.com")
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        //when - action or the behaviour we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

}
