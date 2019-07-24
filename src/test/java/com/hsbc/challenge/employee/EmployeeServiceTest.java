package com.hsbc.challenge.employee;

import com.hsbc.challenge.exception.EmployeeNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;

    @Before
    public void setup() {
        employeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldFindByIdWhenEmployeeIdIsInDatabase() {
        long testId = 500;
        when(employeeRepository.findById(testId)).thenReturn(Optional.of(mock(Employee.class)));

        Employee employee = employeeService.findById(testId);

        assertNotNull(employee);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void shouldThrowErrorWhenCannotFindById() {
        long testId = 230;
        when(employeeRepository.findById(testId)).thenReturn(Optional.empty());

        employeeService.findById(testId);
    }

    @Test
    public void shouldAddEmployee() {
        employeeService.save(mock(Employee.class));
    }

    @Test
    public void shouldUpdateEmployeeWhenEmployeeIsInDatabase() {
        long testId = 26651;
        Employee employee = new Employee("name", "test", 2, BigInteger.TEN);
        when(employeeRepository.findById(testId)).thenReturn(Optional.of(employee));

        employeeService.update(testId, "name2", "test2", 2, BigInteger.TEN);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void shouldThrowErrorWhenUpdateFailsWhenEmployeeIsNotInDatabase() {
        long testId = Long.MAX_VALUE;
        employeeService.update(testId, "n", "t", 0, BigInteger.ONE);
    }

    @Test
    public void shouldDeleteEmployee() {
        employeeService.deleteById(50L);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldFindEmployees() {
        when(employeeRepository.findAll(any(), any(Pageable.class))).thenReturn(mock(Page.class));
        Page page = employeeService.findEmployees("na", "sur", 5, BigInteger.valueOf(300), mock(Pageable.class));
        assertNotNull(page);
    }
}
