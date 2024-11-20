package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.exception.FieldValidationException;
import com.mindex.challenge.exception.InvalidRequestArgumentException;
import com.mindex.challenge.exception.RequestNotFoundException;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.validation.EmployeeFieldValidatorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private static final String EMPLOYEE_NOT_FOUND = "Employee was not found for the given id.";

    private final EmployeeRepository employeeRepository;
    private final EmployeeFieldValidatorService employeeFieldValidatorService;

    @Autowired
    public EmployeeServiceImpl(final EmployeeRepository employeeRepository, final EmployeeFieldValidatorService employeeFieldValidatorService) {
        this.employeeRepository = employeeRepository;
        this.employeeFieldValidatorService = employeeFieldValidatorService;
    }

    /*
     * Creates Employee object from request to save to our db and returns Employee response.
     * Field validation used to insure all fields have been filled with appropriate data.
     */
    @Override
    public Employee create(Employee employee) {

        List<String> validateEmployeeFields = employeeFieldValidatorService.validate(employee);

        if(!validateEmployeeFields.isEmpty()) {
            throw new FieldValidationException(validateEmployeeFields);
        }

        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    /*
     * Retrieves Employee from db based on given Employee id with common exception handling
     * for invalid id parameter input, or if Employee is not found based on given Employee id.
     */
    @Override
    public Employee read(String employeeId) {

        if(employeeId == null || employeeId == "") {
            throw new InvalidRequestArgumentException("Employee Id must not be null or blank.");
        }
        
        LOG.debug("Attempting to retrieve Employee with id of: {}", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        if (employee == null) {
            throw new RequestNotFoundException(String.format("Invalid id: %s. " + EMPLOYEE_NOT_FOUND , employeeId));
        }

        return employee;
    }

    /*
     * Updates existing Employee with new field values provided on request input.
     * If Employee Id is changed during the update, log that it occurred and set employee in this function.
     */
    @Override
    public Employee update(Employee employee, String employeeId) {

        List<String> validateEmployeeFields = employeeFieldValidatorService.validate(employee);

        if(!validateEmployeeFields.isEmpty()) {
            throw new FieldValidationException(validateEmployeeFields);
        }

        Employee existingEmployee = employeeRepository.findByEmployeeId(employeeId);

        if(existingEmployee == null) {
            throw new RequestNotFoundException(String.format("Invalid id: %s. " + EMPLOYEE_NOT_FOUND, employeeId));
        }

        LOG.debug("Updating employee [{}]", employee);

        if(employeeId != existingEmployee.getEmployeeId() && employeeId != null && employeeId != "") {
            LOG.debug("Employee Id for Employee: {} has been updated to: {}", existingEmployee.getEmployeeId(), employeeId);
            employee.setEmployeeId(employeeId);
        }

        return employeeRepository.save(employee);
    }
}
