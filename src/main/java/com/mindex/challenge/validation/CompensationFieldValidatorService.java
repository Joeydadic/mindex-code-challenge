package com.mindex.challenge.validation;

import org.springframework.stereotype.Service;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

@Service
public class CompensationFieldValidatorService extends AbstractFieldValidatorService<Compensation> {

    /*
     * Validations can be expanded upon or changed based on requirements.
     * We want to validate salary having somewhat of a valid input, as well as Employee -> EmployeeId
     * containing a value to have a coupling between Compensation creation to a valid existing Employee.
     */
    @Override
    protected void validateFields(Compensation compensation) {
        if(compensation == null) {
            addError("Compensation must not be null.");
        } else {
            Employee employee = compensation.getEmployee();
            String employeeId = employee.getEmployeeId();
            Double salary = compensation.getSalary();

            /*
             * Salary field just to show intent. Business requirement discussion would be had to determine what the lowest salary allowed should be.
             */
            if(salary == null || salary <= Double.valueOf(1.0)) {
                addError("Salary must be provided in order to create a compensation with a value greater than zero.");
            }

            if(employee == null || employeeId == null || employeeId == "") {
                addError("Employee and Employee id can not be null. A valid Employee id must be provided to create compensation.");
            }
        }
    }
    
}
