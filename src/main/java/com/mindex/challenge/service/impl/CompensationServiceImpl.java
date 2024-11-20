package com.mindex.challenge.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.exception.FieldValidationException;
import com.mindex.challenge.exception.InvalidRequestArgumentException;
import com.mindex.challenge.exception.RequestNotFoundException;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.validation.CompensationFieldValidatorService;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    
    private final CompensationRepository compensationRepository;
    private final EmployeeRepository employeeRepository;
    private final CompensationFieldValidatorService compensationFieldValidatorService;
    
    @Autowired
    public CompensationServiceImpl(final CompensationRepository compensationRepository, final EmployeeRepository employeeRepository, final CompensationFieldValidatorService compensationFieldValidatorService) {
        this.compensationRepository = compensationRepository;
        this.employeeRepository = employeeRepository;
        this.compensationFieldValidatorService = compensationFieldValidatorService;
    }

    /*
     * Creates a Compensation to return as a response after field validation.
     * 
     * Effective date is a required field and an error is not thrown, but rather handled
     * for the user in this context. Further elaboration for this can be to add effective date
     * validation to the validator service, but for now we will handle it here.
     */
    @Override
    public Compensation create(Compensation compensation) {

        List<String> validateCompensationFields = compensationFieldValidatorService.validate(compensation);

        LOG.debug("Attempting to create compensation [{}]", compensation);

        if(!validateCompensationFields.isEmpty()) {
            throw new FieldValidationException(validateCompensationFields);
        }

        if(compensation.getEffectiveDate() == null) {
            compensation.setEffectiveDate(LocalDate.now());
        }
        
        String employeeId = compensation.getEmployee().getEmployeeId();
        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        if(employee == null) {
            throw new RequestNotFoundException(String.format("No Employee exists with the given id of: %s", employeeId));
        }

        LOG.debug("Creating compensation [{}]", compensation);

        compensationRepository.insert(compensation);
        return compensation;
    }

    /*
     * Retrieves Compensation from the db based on the given Employee id.
     * Will throw exceptions based on invalid parameter input or
     * if Compensation is not found given the non-null or blank Employee id.
     */
    @Override
    public Compensation read(String employeeId) {

        LOG.debug("Attempting to get Compensation for Employee: {}", employeeId);

        if(employeeId == null || employeeId == "") {
            throw new InvalidRequestArgumentException("Employee id must not be null or have an empty entry.");
        }
        Compensation compensation = compensationRepository.findByEmployeeEmployeeId(employeeId);

        if(compensation == null) {
            throw new RequestNotFoundException(String.format("Compensation for Employee: %s was not found.", employeeId));
        }

        LOG.debug("Compensation: [{}] found for Employee: {}", compensation, employeeId);

        return compensation;

    }

    

}
