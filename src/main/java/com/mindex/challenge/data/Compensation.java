package com.mindex.challenge.data;

import java.time.LocalDate;

/*
 * POJO to detail Compensation model
 */
public class Compensation {

    private Employee employee;

    private Double salary;

    private LocalDate effectiveDate;


    public Compensation() {
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Double getSalary() {
        return this.salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public LocalDate getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /*
     * Override toString() method as Compensation object is used frequently
     * in logging statements.
     */
    @Override
    public String toString() {
        return "Compensation: {" +
            "employee=" + getEmployee() + "" +
            ", salary=" + getSalary() + "" +
            ", effectiveDate=" + getEffectiveDate() + "" +
            "}";
    }

}
