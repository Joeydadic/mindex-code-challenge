package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String employeeUrl;
    private String reportingStructureUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        reportingStructureUrl = "http://localhost:" + port + "/reporting/{employeeId}";
    }

    /**
     * Builder pattern with EmployeeBuilder to build employee structure, and return Reporting Structure for each
     * Employee built out.
     */
    @Test
    public void test_Read_ReportingStructure() {

        Employee testEmployeeSeniorManager = EmployeeBuilder.customEmployee()
        .setFirstName("John")
        .setLastName("Doe")
        .setDepartment("Engineering")
        .setPosition("Senior Engineering Manager")
        .build();

        Employee testEmployeeManager = EmployeeBuilder.customEmployee()
        .setFirstName("Timmy")
        .setLastName("Manageman")
        .setDepartment("Engineering")
        .setPosition("Engineering Manager")
        .build();

        Employee testEmployeeEngineerOne = EmployeeBuilder.customEmployee()
        .setFirstName("Eric")
        .setLastName("Someone")
        .setDepartment("Engineering")
        .setPosition("Engineer")
        .build();

        Employee testEmployeeEngineerTwo = EmployeeBuilder.customEmployee()
        .setFirstName("Bobby")
        .setLastName("Someone")
        .setDepartment("Engineering")
        .setPosition("Engineer")
        .build();

        /*
         * Add applicable direct reports for Manager
         */
        List<Employee> directReportsForManager = new ArrayList<Employee>();
        directReportsForManager.add(testEmployeeEngineerOne);
        directReportsForManager.add(testEmployeeEngineerTwo);
        testEmployeeManager.setDirectReports(directReportsForManager);

        /*
         * Add applicable direct reports for Senior Manager
         */
        List<Employee> directReportsForSeniorManager = new ArrayList<Employee>();
        directReportsForSeniorManager.add(testEmployeeManager);
        testEmployeeSeniorManager.setDirectReports(directReportsForSeniorManager);

        /*
         * Create all test Employee objects from POST.
         */
        Employee createTestEmployeeEngineerOne = restTemplate.postForEntity(employeeUrl, testEmployeeEngineerOne, Employee.class).getBody();
        Employee createTestEmployeeEngineerTwo = restTemplate.postForEntity(employeeUrl, testEmployeeEngineerTwo, Employee.class).getBody();
        Employee createTestEmployeeManager = restTemplate.postForEntity(employeeUrl, testEmployeeManager, Employee.class).getBody();
        Employee createTestEmployeeSeniorManager = restTemplate.postForEntity(employeeUrl, testEmployeeSeniorManager, Employee.class).getBody();

        /*
         * Peform Read (GET ReportingStructure) for all test employees based on their hierarchy 
         *                  Senior Manager
         *                        |
         *                     Manager
         *                      /    \
         *             Engineer One    Engineer Two
         */
        ReportingStructure readReportingStructureForSeniorManager = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, createTestEmployeeSeniorManager.getEmployeeId()).getBody();
        ReportingStructure readReportingStructureForManager = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, createTestEmployeeManager.getEmployeeId()).getBody();
        ReportingStructure readReportingStructureForEngineerOne = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, createTestEmployeeEngineerOne.getEmployeeId()).getBody();
        ReportingStructure readReportingStructureForEngineerTwo = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, createTestEmployeeEngineerTwo.getEmployeeId()).getBody();


        assertEquals(readReportingStructureForSeniorManager.getNumberOfReports(), 3);
        assertEquals(readReportingStructureForManager.getNumberOfReports(), 2);
        assertEquals(readReportingStructureForEngineerOne.getNumberOfReports(), 0);
        assertEquals(readReportingStructureForEngineerTwo.getNumberOfReports(), 0);
    }

}