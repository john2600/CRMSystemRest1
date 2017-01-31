package com.virtualpairprogrammers.integration.testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.virtualpairprogrammers.domain.Call;
import com.virtualpairprogrammers.domain.Customer;
import com.virtualpairprogrammers.domain.Action;
import com.virtualpairprogrammers.services.calls.CallHandlingService;
import com.virtualpairprogrammers.services.customers.CustomerManagementService;
import com.virtualpairprogrammers.services.customers.CustomerNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/daos.xml","/services.xml","/mix-beans.xml","/datasource-test.xml" } )
@Transactional
public class ManagerServiceIntegrationTesting {

	@Autowired
	private CustomerManagementService serviceCustomer;
	
	@Autowired
	private CallHandlingService callsService;

	
	@Test
	public void testCreatingACustomerRecord() {
		//given a have new customer
		Customer testCustomer = new Customer("9122","VPP","email","telephone","New Note");
		
		//when customer is saved into database
		serviceCustomer.newCustomer(testCustomer);
		
		//then a customer exists on the database
		List<Customer> customers = serviceCustomer.getAllCustomers();
		assertEquals(1,customers.size());
		
	}
	
	@Test
	public void testFindingACustomer()
	{
		Customer testCustomer = new Customer("9123","VPP","email","telephone","New Note");
		
		//when customer is saved into database
		serviceCustomer.newCustomer(testCustomer);
		
		try 
		{
			Customer foundCustomer = serviceCustomer.findCustomerById("9123");
			//System.out.println(foundCustomer.toString());
			assertEquals(testCustomer, foundCustomer);
		} 
		catch (CustomerNotFoundException e)  {
			fail("Customer not found");
		}
	}
	
	@Test
	public void testAddingACallToACustomer()
	{
		Customer testCustomer = new Customer("9191", "VPP", "email", "telephone","notes"); 
		serviceCustomer.newCustomer(testCustomer);
		
		Call testCall = new Call("Just a test call");
		List<Action> actions = new ArrayList<Action>();
		
		try 
		{
			callsService.recordCall("9191", testCall, actions);
			
			Customer foundCustomer = serviceCustomer.getFullCustomerDetail("9191");
			Call foundCall = foundCustomer.getCalls().get(0);
			assertEquals(testCall, foundCall);
		} 
		catch (CustomerNotFoundException e) 
		{
			fail("Customer was not found");
		}
	}


}
