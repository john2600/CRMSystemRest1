package com.virtualpairprogrammers.webcontroller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.virtualpairprogrammers.domain.Customer;
import com.virtualpairprogrammers.services.customers.CustomerManagementService;

@Controller
public class ManageCustomersController {
	private final Logger logger = LoggerFactory.getLogger(ManageCustomersController.class);
	
	//@Autowired
	@Resource(name="customerService")
	private CustomerManagementService customers;
	
	@RequestMapping(value="/all-customers")
	public ModelAndView displayAllCustomersOnWebPage()
	{
		List<Customer> allCustomers = customers.getAllCustomers();
		//List<Customer> allCustomers = new ArrayList<Customer>();
		logger.debug("Show was executed !!!"+allCustomers);
		for(Customer customer:allCustomers){
			logger.debug("Show was executed !!!"+customer.toString());
		
		}
		/*
		List<Customer> allCustomers = new ArrayList<Customer>();
		Customer testCustomer1 = new Customer("9122","VPP","email","telephone","New Note1");
		Customer testCustomer2 = new Customer("9123","VPP","email","telephone","New Note2");
		Customer testCustomer3 = new Customer("9124","VPP","email","telephone","New Note3");
		Customer testCustomer4 = new Customer("9125","VPP","email","telephone","New Note4");
		allCustomers.add(testCustomer1);
		allCustomers.add(testCustomer2);
		allCustomers.add(testCustomer3);
		allCustomers.add(testCustomer4);
		logger.debug("imprimo Custommer ");
		*/
		
		ModelAndView model = new ModelAndView();
		model.setViewName("show");
		
		model.addObject("customers", allCustomers);
		
		return model;
		
	}

}
