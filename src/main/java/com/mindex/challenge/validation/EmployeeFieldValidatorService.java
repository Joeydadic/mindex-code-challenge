package com.mindex.challenge.validation;

import org.springframework.stereotype.Service;

import com.mindex.challenge.data.Employee;

@Service
public class EmployeeFieldValidatorService extends AbstractFieldValidatorService<Employee> {

    // Global error messages to avoid repetition in constructing the messages.

    private static final String EMPLOYEE_ERROR_MSG_PREFIX = "Employee's";
    private static final String EMPLOYEE_ERROR_MSG = " must not be null or have an invalid character limit of 1 or less.";

    /*
     * Validate fields for Employee data object. Direct Reports can be empty or null.
     * 
     * Validations are assuming that abstract abbreviations are not allowed for names as well as
     * position and department, however, two characters can be applied to these fields for some cases.
     */

    @Override
    protected void validateFields(Employee employee) {

        if(employee == null) {
            addError("Employee can not be null.");
        } else {
            String firstName = employee.getFirstName();
            String lastName = employee.getLastName();
            String department = employee.getDepartment();
            String position = employee.getPosition();

            if(firstName == null || firstName.length() <= 1) {
                addError(EMPLOYEE_ERROR_MSG_PREFIX + " first name " + EMPLOYEE_ERROR_MSG);
            }

            if(lastName == null || lastName.length() <= 1) {
                addError(EMPLOYEE_ERROR_MSG_PREFIX + " last name " + EMPLOYEE_ERROR_MSG);
            }

            if(department == null || department.length() <= 1) {
                addError(EMPLOYEE_ERROR_MSG_PREFIX + " department " + EMPLOYEE_ERROR_MSG);
            }

            if(position == null || position.length() <= 1) {
                addError(EMPLOYEE_ERROR_MSG_PREFIX + " position " + EMPLOYEE_ERROR_MSG);
            }
        }   
    }
    
}
