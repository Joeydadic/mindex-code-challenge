package com.mindex.challenge.validation;

import java.util.ArrayList;
import java.util.List;

/*
 * Abstract Field Validator Service to create a list of validations based on the context of the implementing class.
 */
public abstract class AbstractFieldValidatorService<T> implements IValidatorService<T> {
    
    protected List<String> validationErrors = new ArrayList<>();

    public List<String> getValidationErrors() {
        return validationErrors;
    }

    protected void addError(String errorMessage) {
        validationErrors.add(errorMessage);
    }
    
    protected abstract void validateFields(T object);

    @Override
    public List<String> validate(T object) {
        validationErrors.clear(); 
        validateFields(object);  
        return validationErrors;
    }
}
