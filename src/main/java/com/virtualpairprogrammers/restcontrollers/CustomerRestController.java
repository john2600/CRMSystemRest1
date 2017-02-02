package com.virtualpairprogrammers.restcontrollers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sun.jndi.toolkit.url.Uri;
import com.virtualpairprogrammers.domain.Customer;
import com.virtualpairprogrammers.services.customers.CustomerManagementService;
import com.virtualpairprogrammers.services.customers.CustomerNotFoundException;

@RestController
public class CustomerRestController {

	@Autowired
	private CustomerManagementService service;

	// @ResponseStatus(value=HttpStatus.NOT_FOUND,reason="The Customer wasn't
	// found")
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ClientErrorInformation> rulesForCustomerNotFound(HttpServletRequest req, Exception e) {
		System.out.println("Handling the error");
		ClientErrorInformation error = new ClientErrorInformation(e.toString(), req.getRequestURI());
		return new ResponseEntity<ClientErrorInformation>(error, HttpStatus.NOT_FOUND);

	}

	// @RequestMapping(value="/customers/{id}", headers=
	// {"Accept=application/json,application/xml"}) No should go..
	@RequestMapping(value = "/customers/{id}")
	public Customer findCustomerById(@PathVariable("id") String id) throws CustomerNotFoundException {
		Customer findCustomer = null;
		findCustomer = service.getFullCustomerDetail(id);
		System.out.println(" Customer found!! " + findCustomer.toString());
		return findCustomer;
	}

	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	public CustomerCollectionRepresentation getAllCustomers(@RequestParam(required = false) Integer first,
			@RequestParam(required = false) Integer last) throws CustomerNotFoundException {
		List<Customer> customers = service.getAllCustomers();
		for (Customer customer : customers) {
			customer.setCalls(null);
			Link uri = linkTo(methodOn(CustomerRestController.class).findCustomerById(customer.getCustomerId())).withSelfRel();
			System.out.print(uri.toString());
			customer.add(uri);
		}
		if (first != null && last != null) {

			return new CustomerCollectionRepresentation(customers.subList(first - 1, last));
		}
		return new CustomerCollectionRepresentation(customers);

	}

	@RequestMapping(value = "/customers", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<Customer> createNewCustomer(@RequestBody Customer newCustomer)
			throws CustomerNotFoundException {
		HttpHeaders headers = new HttpHeaders();
		Customer createCustomer = service.newCustomer(newCustomer);
		/*
		 * try { headers.setLocation(new
		 * URI("http://localhost:8088/CRMSystemRest1-1.0/customers/"+
		 * createCustomer.getCustomerId())); } catch (URISyntaxException e) {
		 * throw new RuntimeException(); }
		 */
		// URI url =
		// ServletUriComponentsBuilder.fromCurrentContextPath().path("/customers/").path(createCustomer.getCustomerId()).build().toUri();
		/*
		 * 
		 * URI uri =
		 * MvcUriComponentsBuilder.fromMethodName(CustomerRestController.class,
		 * "findCustomerById", createCustomer.getCustomerId()).build().toUri();
		 * 
		 * URI uri =
		 * linkTo(methodOn(CustomerRestController.class).findCustomerById(
		 * createdCustomer.getCustomerId())).toUri();
		 */

		URI uri = linkTo(methodOn(CustomerRestController.class).findCustomerById(createCustomer.getCustomerId()))
				.toUri();
		headers.setLocation(uri);

		return new ResponseEntity<Customer>(createCustomer, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable String id) throws CustomerNotFoundException {
		Customer oldCustomer = service.findCustomerById(id);
		service.deleteCustomer(oldCustomer);
	}

	@RequestMapping(value = "/customer/{id}", method = RequestMethod.PUT)
	public void updateCustomerbyId(@RequestBody Customer customerToUpdate) throws CustomerNotFoundException {
		service.updateCustomer(customerToUpdate);
	}

	/*
	 * @RequestMapping(value="/customesXMLVersion/{id}", headers=
	 * {"Accept=application/xml"}) public Customer
	 * findCustomerByIdXMLVersion(@PathVariable("id") String id){ Customer
	 * findCustomer = null; try { findCustomer =
	 * service.getFullCustomerDetail(id);
	 * 
	 * System.out.println(" Customer found!! "+findCustomer.toString()); }catch
	 * (CustomerNotFoundException e){ throw new RuntimeException(e); } return
	 * findCustomer; }
	 */

}
