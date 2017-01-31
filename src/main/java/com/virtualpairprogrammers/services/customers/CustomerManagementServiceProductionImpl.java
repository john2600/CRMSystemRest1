package com.virtualpairprogrammers.services.customers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.virtualpairprogrammers.dataaccess.CustomerDao;
import com.virtualpairprogrammers.dataaccess.RecordNotFoundException;
import com.virtualpairprogrammers.domain.Call;
import com.virtualpairprogrammers.domain.Customer;

@Transactional
@Component
public class CustomerManagementServiceProductionImpl implements
		CustomerManagementService 
{

	@Autowired
	private CustomerDao dao;
	
	
	public CustomerManagementServiceProductionImpl(CustomerDao dao){
		this.dao = dao;
	}
	
	
	@Transactional
	public Customer newCustomer(Customer newCustomer) 
	{
		if(newCustomer.getCustomerId() == null) {
			String newCustomerId = java.util.UUID.randomUUID().toString();
			newCustomer.setCustomerId(newCustomerId);
		}
		dao.create(newCustomer);
		
		return newCustomer;
	}

	@Override
	public void updateCustomer(Customer changedCustomer) throws CustomerNotFoundException
	{
		try
		{
			dao.update(changedCustomer);
		}
		catch (RecordNotFoundException e)
		{
			throw new CustomerNotFoundException();
		}
	}

	@Override
	public void deleteCustomer(Customer oldCustomer) throws CustomerNotFoundException 
	{
		try
		{
			dao.delete(oldCustomer);
		}
		catch (RecordNotFoundException e)
		{
			throw new CustomerNotFoundException();
		}
	}

	
	public Customer findCustomerById(String customerId) throws CustomerNotFoundException 
	{
		try
		{
			return dao.getById(customerId);
		}
		catch (RecordNotFoundException e)
		{
			throw new CustomerNotFoundException();
		}
	}

	@Override
	public List<Customer> findCustomersByName(String name) 
	{
		return dao.getByName(name);
	}

	@Override
	public List<Customer> getAllCustomers() 
	{
		return dao.getAllCustomers();
	}

	@Override
	public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
		try
		{
			return dao.getFullCustomerDetail(customerId);			
		}
		catch (RecordNotFoundException e)
		{
			throw new CustomerNotFoundException();
		}
	}
	

	@Transactional
	public void recordCall(String customerId, Call callDetails)
			throws CustomerNotFoundException 
	{
		try
		{
			dao.addCall(callDetails, customerId);			
		}
		catch (RecordNotFoundException e)
		{
			throw new CustomerNotFoundException();
		}
	}

}
