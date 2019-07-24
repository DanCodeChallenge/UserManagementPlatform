package com.hsbc.challenge.controller;

import com.hsbc.challenge.employee.Employee;
import com.hsbc.challenge.employee.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeControllerTest {

    private EmployeeController employeeController;
    private EmployeeService employeeService;

    @Before
    public void setup() {
        employeeService = mock(EmployeeService.class);
        employeeController = new EmployeeController(employeeService);
    }

    @Test
    public void shouldReturnEmployeeWhenSearchingForEmployeeById() {
        long testId = 1;
        when(employeeService.findById(testId)).thenReturn(mock(Employee.class));
        Employee employee = employeeController.findEmployeeById(testId);
        assertNotNull(employee);
    }

    @Test
    public void shouldReturnOkResponseWhenAddingNewEmployee() {
        ResponseEntity<?> responseEntity = employeeController.addNewEmployee(mock(Employee.class));
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void shouldReturnOKResponseWhenUpdatingEmployee() {
        ResponseEntity<?> responseEntity = employeeController.updateEmployee(3L, "name", "surname", 1, BigInteger.ONE);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void shouldReturnOKResponseWhenDeletingEmployee() {
        ResponseEntity<?> responseEntity = employeeController.deleteEmployee(5L);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldReturnEmployeesWhenSearchingForEmployeesByAttributes() {
        when(employeeService.findEmployees(anyString(), anyString(), anyInt(), any(), any())).thenReturn(mock(Page.class));
        Page<Employee> employees = employeeController.findEmployees("name", "surname", 3, BigInteger.TEN, mock(Pageable.class));
        assertNotNull(employees);
    }
}