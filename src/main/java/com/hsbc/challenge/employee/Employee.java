package com.hsbc.challenge.employee;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotNull
    //TODO: if we knew all possible values we could add enum to enumerate all possible grades, and add validation to capture valid range of values
    private Integer grade;
    @NotNull
    @Min(value=0)
    private BigInteger salary;

    public Employee() {}

    public Employee(String name, String surname, Integer grade, BigInteger salary) {
        this.name = name;
        this.surname = surname;
        this.grade = grade;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public BigInteger getSalary() {
        return salary;
    }

    public void setSalary(BigInteger salary) {
        this.salary = salary;
    }
}
