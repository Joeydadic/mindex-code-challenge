package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    @PostMapping("/employee")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        Employee employeeResponse = employeeService.create(employee);
        return ResponseEntity.ok(employeeResponse);
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable String id) {
        LOG.debug("Received employee create request for id {}", id);

        return employeeService.read(id);
    }

    @PutMapping("/employee/{id}")
    public Employee updateEmployee(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee update request for id {} and employee [{}]", id, employee);

        /*
         * Custom ID's could potentially be an auth issue. More in depth project would account for user ID's 
         * when querying for Compensation. Commenting it out to preserve for Service layer.
         */
        // employee.setEmployeeId(id);

        return employeeService.update(employee, id);
    }
}
