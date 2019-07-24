package com.hsbc.challenge.integration;

import com.hsbc.challenge.employee.Employee;
import org.junit.Test;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Testing API Endpoint for adding new employees
 */
public class CreateEmployeeIntegrationTest extends BaseEmployeeIntegrationTest {

    @Test
    public void shouldCreateEmployee() {
        Employee employeeToAdd = new Employee("Daniel", "J", 1, BigInteger.valueOf(50000));

        ResponseEntity<String> response = sendToCreateEndpoint(employeeToAdd);

        assertThat(employeeRepo.findOne(Example.of(employeeToAdd)).get()).isEqualToIgnoringGivenFields(employeeToAdd, "id");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void shouldGetHttpErrorWhenEmployeePropertiesAreIncorrect() {
        Employee employeeWithNoGrade = new Employee("John", "Apple", null, BigInteger.valueOf(100000));
        Employee employeeWithNegativeSalary = new Employee("John", "Apple", 4, BigInteger.valueOf(-100000));

        ResponseEntity<String> response = sendToCreateEndpoint(employeeWithNoGrade);
        ResponseEntity<String> response2 = sendToCreateEndpoint(employeeWithNegativeSalary);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals(500, response2.getStatusCodeValue());
        assertFalse(employeeRepo.findOne(Example.of(employeeWithNoGrade)).isPresent());
        assertFalse(employeeRepo.findOne(Example.of(employeeWithNegativeSalary)).isPresent());
    }

    private ResponseEntity<String> sendToCreateEndpoint(Employee employee) {
        HttpEntity<Employee> entity = new HttpEntity<>(employee, headers);
        return restTemplate.exchange(
                createURLForEndpoint("//employee"),
                HttpMethod.POST, entity, String.class);
    }
}
