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

    public void update(Long id, Employee employee) {
        Optional<Employee> employeeOpt = employeeRepo.findById(id);
        if (employeeOpt.isPresent()) {
            Employee employeeToUpdate = employeeOpt.get();
            employeeToUpdate.setName(getNewProperty(employeeToUpdate.getName(), employee.getName()));
            employeeToUpdate.setSurname(getNewProperty(employeeToUpdate.getSurname(), employee.getSurname()));
            Integer newGrade = employee.getGrade() == null ? employeeToUpdate.getGrade() : employee.getGrade();
            employeeToUpdate.setGrade(newGrade);
            BigInteger newSalary = employee.getSalary() == null ? employeeToUpdate.getSalary() : employee.getSalary();
            employeeToUpdate.setSalary(newSalary);
            save(employeeToUpdate);
        } else {
            throw new EmployeeNotFoundException(id);
        }
    }

    private String getNewProperty(String currentProperty, String updatedProperty) {
        return updatedProperty == null ? currentProperty : updatedProperty;
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
