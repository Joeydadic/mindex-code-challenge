package com.mindex.challenge.data;

import java.util.List;

/*
 * POJO to detail Employee model.
 */
public class Employee {

    private String employeeId;

    private String firstName;

    private String lastName;

    private String position;

    private String department;

    private List<Employee> directReports;

    public Employee() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Employee> getDirectReports() {
        return directReports;
    }

    public void setDirectReports(List<Employee> directReports) {
        this.directReports = directReports;
    }

    /*
     * Override toString() method for Employee as it is used frequently
     * in logging statements.
     */
    @Override
    public String toString() {
        return "Employee {" +
            "employeeId=" + getEmployeeId() + "" +
            ", firstName=" + getFirstName() + "" +
            ", lastName=" + getLastName() + "" +
            ", position=" + getPosition() + "" +
            ", department=" + getDepartment() + "" +
            ", directReports=" + getDirectReports() + "" +
            "}";
    }

}
