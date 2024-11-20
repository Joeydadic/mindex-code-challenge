package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String employeeUrl;
    private String compensationUrl;
    private String employeeIdUrlForCompensation;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrlForCompensation = "http://localhost:" + port + "/compensation/{employeeId}";
        compensationUrl = "http://localhost:" + port + "/compensation";
    }

    /*
     * Test functionality of Create Compensation and Get Compensation.
     * Compares equality for both Employee objects stored on Compensation and
     * both Compensation objects (expected, value)
     */
    @Test
    public void Create_Compensation_Get_Compensation_Success() {

        Employee validEmployee = EmployeeBuilder.customEmployee()
        .setDepartment("IT")
        .setFirstName("Joseph")
        .setLastName("Dadic")
        .setPosition("Software Engineer")
        .build();

        // Create Employee with Post
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, validEmployee, Employee.class).getBody();

        Compensation validCompensation = CompensationBuilder.customCompensation()
        .setEmployee(createdEmployee)
        .setSalary(85000.00)
        .setEffectiveDate(LocalDate.now())
        .build();

        // Create Compensation for Employee with Post
        Compensation createCompensationResponse = restTemplate.postForEntity(compensationUrl, validCompensation, Compensation.class).getBody();
        assertCompensationEquivalence(validCompensation, createCompensationResponse);

        // Get Compensation for Employee
        Compensation getCompensationResponse = restTemplate.getForEntity(employeeIdUrlForCompensation, Compensation.class, createdEmployee.getEmployeeId()).getBody();
        assertCompensationEmployeeEquivalence(getCompensationResponse, createdEmployee);

    }

    /*
     * Create a Compensation with POST and use the Compensation Builder to create
     * a Compensation object with an invalid field. Salary being Zero will fail the validation
     * check, and ControllerExceptionHandler will handle FieldValidationException.
     * 
     * handleFieldValidationError returns a generic ResponseEntity<?> type as a response.
     * Supress warning to run assertions against response map.
     */
    @Test
    public void Create_Compensation_With_Invalid_Fields_Zero_Salary() {

        Employee validEmployee = EmployeeBuilder.customEmployee()
        .setDepartment("IT")
        .setFirstName("Joseph")
        .setLastName("Dadic")
        .setPosition("Software Engineer")
        .build();

        // Create Employee with Post
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, validEmployee, Employee.class).getBody();

        // Create Compensaton that will fail validation
        Compensation invalidCompensationZeroSalary = CompensationBuilder.customCompensation()
        .setEmployee(createdEmployee)
        .setSalary(0.0)
        .setEffectiveDate(LocalDate.now())
        .build();

        // Reponse should be a 400 BAD REQUEST
        ResponseEntity<Compensation> invalidFieldCompensation = restTemplate.postForEntity(compensationUrl, invalidCompensationZeroSalary, Compensation.class);

        // Invalid field will return a 400 BAD REQUEST response to the User with a detailed response.
        assertEquals(invalidFieldCompensation.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
    
    /*
     * Function to determine equivalence for Employee object values tied to the Compensation, and the
     * Employee built out from EmployeeBuilder in our GET test.
     */
    private static void assertCompensationEmployeeEquivalence(Compensation expected, Employee actual) {

        Employee employeeTiedToCompensation = expected.getEmployee();

        assertEquals(employeeTiedToCompensation.getFirstName(), actual.getFirstName());
        assertEquals(employeeTiedToCompensation.getLastName(), actual.getLastName());
        assertEquals(employeeTiedToCompensation.getDepartment(), actual.getDepartment());
        assertEquals(employeeTiedToCompensation.getPosition(), actual.getPosition());

    }

    /*
     * Determines all fields for built out, created Compensation object are equal to the Compensation object
     * returned in our POST test.
     */
    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        
        assertCompensationEmployeeEquivalence(expected, actual.getEmployee());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals(expected.getSalary(), actual.getSalary());

    }
}