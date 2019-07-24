package com.hsbc.challenge.integration;

import com.hsbc.challenge.employee.Employee;
import org.junit.Test;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * API endpoints integration testing
 */
public class UpdateEmployeeIntegrationTest extends BaseEmployeeIntegrationTest {

    @Test
    public void shouldUpdateEmployeeWhenEmployeeExistsInDatabase() {
        Employee employeeToUpdate = new Employee("Update", "Me", 2, BigInteger.valueOf(35123));
        employeeRepo.save(employeeToUpdate);
        String updatedName = "Updated";
        int updatedGrade = 2;
        employeeToUpdate.setName(updatedName);
        employeeToUpdate.setGrade(updatedGrade);

        long employeeIdToUpdate = employeeToUpdate.getId();
        Employee employeeNewDetails = new Employee(updatedName, null, updatedGrade, null);

        ResponseEntity<String> response = sendToUpdateEndpoint(employeeNewDetails, employeeIdToUpdate);

        assertThat(employeeRepo.findById(employeeIdToUpdate).get()).isEqualToComparingFieldByField(employeeToUpdate);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void shouldGetHttpNotFoundErrorWhenEmployeeIdDoesNotExistInDatabase() {
        Employee employeeToUpdate = new Employee("I_do_not_exist", "Surname", 3, BigInteger.valueOf(23535));

        Long idUsedForRequest = 125000L;
        ResponseEntity<String> response = sendToUpdateEndpoint(employeeToUpdate, idUsedForRequest);

        assertFalse(employeeRepo.findOne(Example.of(employeeToUpdate)).isPresent());
        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Employee id not found"));
        assertTrue(response.getBody().contains(idUsedForRequest.toString()));
    }

    private ResponseEntity<String> sendToUpdateEndpoint(Employee employee, Long id) {
        HttpEntity<Employee> entity = new HttpEntity<>(employee, headers);

        return restTemplate.exchange(
                createURLForEndpoint("//employee/" + id),
                HttpMethod.PUT, entity, String.class);
    }
}
