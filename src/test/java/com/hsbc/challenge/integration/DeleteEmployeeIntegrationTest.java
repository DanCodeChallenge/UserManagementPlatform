package com.hsbc.challenge.integration;

import com.hsbc.challenge.employee.Employee;
import org.junit.Test;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Testing API Endpoint for deleting employees
 */
public class DeleteEmployeeIntegrationTest extends BaseEmployeeIntegrationTest {

    @Test
    public void shouldDeleteEmployeeWhenEmployeeIsInDatabase() {
        Employee employeeToDelete = new Employee("Test", "Deletion", 1, BigInteger.valueOf(50000));
        employeeRepo.save(employeeToDelete);
        long employeeIdToDelete = employeeToDelete.getId();

        ResponseEntity<String> response = sendToDeleteEndpoint(employeeIdToDelete);

        assertFalse(employeeRepo.findById(employeeIdToDelete).isPresent());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void shouldGetHttpErrorWhenEmployeeDoesNotExistInDatabase() {
        ResponseEntity<String> response = sendToDeleteEndpoint(ThreadLocalRandom.current().nextLong(100000L, Long.MAX_VALUE));

        assertEquals(500, response.getStatusCodeValue());
    }

    private ResponseEntity<String> sendToDeleteEndpoint(Long id) {
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                createURLForEndpoint("//employee/" + id),
                HttpMethod.DELETE, entity, String.class);
    }
}
