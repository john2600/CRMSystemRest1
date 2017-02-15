package com.virtualpairprogrammers.restcontrollers;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.virtualpairprogrammers.domain.Customer;

@Component
public class CustomerValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) 
	{
		return clazz.equals(Customer.class);
	}

	@Override
	public void validate(Object actualObject, Errors errors)
	{
		// "       "
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyName", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "notes", "required");
	}



}
