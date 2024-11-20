package com.mindex.challenge.service.impl;

import java.util.List;

import com.mindex.challenge.data.Employee;

/*
 * EmployeeBuilder class following Builder Pattern to allow for instantiation of
 * the Employee domain object and customization for test case creation.
 */

public class EmployeeBuilder {
    private Employee employee;
    
    public EmployeeBuilder() {
        employee = new Employee();
    }

    public static EmployeeBuilder customEmployee() {
        return new EmployeeBuilder();
    }

    public EmployeeBuilder setFirstName(String firstName) {
        employee.setFirstName(firstName);
        return this;
    }

    public EmployeeBuilder setLastName(String lastName) {
        employee.setLastName(lastName);
        return this;
    }

    public EmployeeBuilder setDepartment(String department) {
        employee.setDepartment(department);
        return this;
    }

    public EmployeeBuilder setPosition(String position) {
        employee.setPosition(position);
        return this;
    }

    public EmployeeBuilder setDirectReports(List<Employee> directReports) {
        employee.setDirectReports(directReports);
        return this;
    }

    public EmployeeBuilder setEmployeeId(String employeeId) {
        employee.setEmployeeId(employeeId);
        return this;
    }

    public Employee build() {
        return employee;
    }
}
