package com.hsbc.challenge.employee;

import com.hsbc.challenge.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public Employee findById(Long id) {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new EmployeeNotFoundException(id);
        }
    }

    public void save(Employee employee) {
        employeeRepo.save(employee);
    }

    public void update(Long id, String name, String surname, Integer grade, BigInteger salary) {
        Optional<Employee> employeeToUpdate = employeeRepo.findById(id);
        if (employeeToUpdate.isPresent()) {
            Employee employee = employeeToUpdate.get();
            employee.setName(name);
            employee.setSurname(surname);
            employee.setGrade(grade);
            employee.setSalary(salary);
            save(employee);
        } else {
            throw new EmployeeNotFoundException(id);
        }
    }

    public void deleteById(Long id) {
        employeeRepo.deleteById(id);
    }

    public Page<Employee> findEmployees(String name, String surname, Integer grade, BigInteger salary, Pageable pageable) {
        final Employee employee = new Employee(name, surname, grade, salary);
        final Example<Employee> example = Example.of(employee);
        return employeeRepo.findAll(example, pageable);
    }
}
