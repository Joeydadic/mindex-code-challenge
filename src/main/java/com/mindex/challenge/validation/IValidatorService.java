package com.mindex.challenge.validation;

import java.util.List;

/*
 * Interface set up for validation for field objects
 */
public interface IValidatorService<T> {
    List<String> validate(T object);
}