package com.hsbc.challenge.controller;

import com.hsbc.challenge.employee.Employee;
import com.hsbc.challenge.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@RestController
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/{id}")
    public Employee findEmployeeById(@PathVariable @NotNull Long id) {
        return employeeService.findById(id);
    }

    @PostMapping("/employee")
    public ResponseEntity<?> addNewEmployee(@RequestBody Employee employee) {
        employeeService.save(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable @NotNull Long id,
                                            @RequestParam String name,
                                            @RequestParam String surname,
                                            @RequestParam Integer grade,
                                            @RequestParam BigInteger salary) {
        employeeService.update(id, name, surname, grade, salary);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable @NotNull Long id) {
        employeeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/employee")
    public Page<Employee> findEmployees(@RequestParam(required = false) String name,
                                        @RequestParam(required = false) String surname,
                                        @RequestParam(required = false) Integer grade,
                                        @RequestParam(required = false) BigInteger salary,
                                        Pageable pageable) {
        return employeeService.findEmployees(name, surname, grade, salary, pageable);
    }
}
