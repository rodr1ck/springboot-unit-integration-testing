package com.rodrigoguides.springboot.repository;

import com.rodrigoguides.springboot.model.Employee;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    //JUnit test for save operation
    @DisplayName("JUnit test for save operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Rodrigo")
                .lastName("Orellana")
                .email("rodrigo.orelana@gmail.com")
                .build();

        //when - action or the behaviour we are going to test
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    //JUnit test for get all employees operation
    @DisplayName("JUnit test for get all employees operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenEmployeesList() {
        //given - precondition or setup

        Employee employee = Employee.builder()
                .firstName("Rodrigo")
                .lastName("Orellana")
                .email("rodrigo.orelana@gmail.com")
                .build();

        Employee employee1 = Employee.builder()
                .firstName("John")
                .lastName("Cena")
                .email("john.cena@gmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when - action or the behaviour we are going to test
        List<Employee> employeeList = employeeRepository.findAll();


        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

    }

    //JUnit test for get employee by id operation
    @DisplayName("JUnit test for get employee by id operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Rodrigo")
                .lastName("Orellana")
                .email("rodrigo.orelana@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour we are going to test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();

    }

    //JUnit test for get employee by email operation
    @DisplayName("JUnit test for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Rodrigo")
                .lastName("Rivera")
                .email("rodrigo.rivera@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour we are going to test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //JUnit test for update employee operation
    @DisplayName("JUnit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Rodrigo")
                .lastName("Rivera")
                .email("rodrigo.rivera@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour we are going to test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("rivera.rodrigo@gmail.com");
        savedEmployee.setFirstName("Domingo");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("rivera.rodrigo@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Domingo");
    }

    //JUnit test for delete employee operation
    @DisplayName("JUnit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Rodrigo")
                .lastName("Rivera")
                .email("rodrigo.rivera@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or the behaviour we are going to test
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        //then - verify the output
        assertThat(employeeOptional).isEmpty();
    }

    //JUnit test for custom query using JPQL with index
    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Rodrigo")
                .lastName("Rivera")
                .email("rodrigo.rivera@gmail.com")
                .build();
        employeeRepository.save(employee);
        String firstName = "Rodrigo";
        String lastName = "Rivera";

        //when - action or the behaviour we are going to test
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }


    //JUnit test for custom query using JPQL with named params
    @DisplayName("JUnit test for custom query using JPQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {
        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Rodrigo")
                .lastName("Rivera")
                .email("rodrigo.rivera@gmail.com")
                .build();
        employeeRepository.save(employee);
        String firstName = "Rodrigo";
        String lastName = "Rivera";

        //when - action or the behaviour we are going to test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

}
