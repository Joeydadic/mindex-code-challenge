package com.mindex.challenge.service.impl;

import java.time.LocalDate;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

/*
 * CompensationBuilder class following BuilderPattern to allow for instantiation of
 * the Compensation domain object and customization for test case creation.
 */
public class CompensationBuilder {

    private Compensation compensation;
    
    public CompensationBuilder() {
        compensation = new Compensation();
    }

    public static CompensationBuilder customCompensation() {
        return new CompensationBuilder();
    }

    public CompensationBuilder setEmployee(Employee employee) {
        this.compensation.setEmployee(employee);
        return this;
    }

    public CompensationBuilder setEmployeeId(String employeeId) {
        this.compensation.getEmployee().setEmployeeId(employeeId);
        return this;
    }

    public CompensationBuilder setSalary(Double salary) {
        this.compensation.setSalary(salary);
        return this;
    }

    public CompensationBuilder setEffectiveDate(LocalDate effectiveDate) {
        this.compensation.setEffectiveDate(effectiveDate);
        return this;
    }

    public Compensation build() {
        return compensation;
    }
}