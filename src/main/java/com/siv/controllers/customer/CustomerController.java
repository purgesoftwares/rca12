package com.siv.controllers.customer;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.siv.exceptions.AllPropertyRequiredException;
import com.siv.exceptions.UsernameIsNotAnEmailException;
import com.siv.model.customer.Customer;
import com.siv.repository.customer.CustomerRepository;

@Path("/secured/customer")
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@POST
	@Produces("application/json")
	public Customer create(Customer customer) throws UsernameIsNotAnEmailException{
		
		if(customer.getFirstName() == null || customer.getMainEmail() == null) {
			throw new AllPropertyRequiredException("First name and main email is required.");
		}
		
		boolean isValid = checkStringIsEmail(customer.getMainEmail());
		if(!isValid){
			throw new UsernameIsNotAnEmailException("Please input correct email type.");
		}
		
		customer.setCreateDate(new Date());
		customer.setLastUpdate(new Date());
		return customerRepository.save(customer);		
	}
	
	private boolean checkStringIsEmail(String username) {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(username);
		boolean matchFound = m.matches();
		if (matchFound) {
		    return true;
		}
		return false;
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Customer findOne(@PathParam("id") String id){
		return customerRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<Customer> findAll(Pageable pageble){
		return customerRepository.findAll(pageble);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer update(@PathParam(value="id")String id, Customer customer){
		Customer preCustomer = customerRepository.findOne(id);
		customer.setId(id);
		customer.setCreateDate(preCustomer.getCreateDate());
		customer.setLastUpdate(new Date());
		
		if(customer.getFirstName() == null) {
			customer.setFirstName(preCustomer.getFirstName());
		} if(customer.getLastName() == null) {
			customer.setLastName(preCustomer.getLastName());
		} if(customer.getAddressId() == null) {
			customer.setAddressId(preCustomer.getAddressId());
		} if(customer.getFullName() == null) {
			customer.setFullName(preCustomer.getFullName());
		} if(customer.getMainEmail() == null) {
			customer.setMainEmail(preCustomer.getMainEmail());
		}
		
		return customerRepository.save(customer);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer delete(@PathParam(value="id")String id){
		Customer customer = customerRepository.findOne(id);
		customerRepository.delete(customer);
		return customer;
	}

}
