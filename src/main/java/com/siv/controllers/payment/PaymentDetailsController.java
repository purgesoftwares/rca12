package com.siv.controllers.payment;

import java.util.Date;

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

import com.siv.model.payment.PaymentDetails;
import com.siv.repository.payment.PaymentDetailsRepository;

@Path("/secured/payment-detail")
public class PaymentDetailsController {
	
	@Autowired
	private PaymentDetailsRepository paymentDetailsRepository;
	
	@POST
	@Produces("application/json")
	public PaymentDetails create(PaymentDetails paymentDetails){
		paymentDetails.setCreateDate(new Date());
		paymentDetails.setLastUpdate(new Date());		
		return paymentDetailsRepository.save(paymentDetails);		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PaymentDetails findOne(@PathParam(value="id")String id){
		return paymentDetailsRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<PaymentDetails> findAll(Pageable pageble){
		return paymentDetailsRepository.findAll(pageble);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PaymentDetails update(@PathParam(value="id")String id, PaymentDetails paymentDetails){
		PaymentDetails prePayment = paymentDetailsRepository.findOne(id);
		paymentDetails.setId(id);
		paymentDetails.setCreateDate(prePayment.getCreateDate());
		paymentDetails.setLastUpdate(new Date());
		
		if(paymentDetails.getAccountNumber() == null) {
			paymentDetails.setAccountNumber(prePayment.getAccountNumber());
		} if(paymentDetails.getBankCode() ==null) {
			paymentDetails.setBankCode(prePayment.getBankCode());
		} if(paymentDetails.getBankName() == null) {
			paymentDetails.setBankName(prePayment.getBankName());
		} if(paymentDetails.getPaymentType() == null) {
			paymentDetails.setPaymentType(prePayment.getPaymentType());
		} if(paymentDetails.getUsername() == null) {
			paymentDetails.setUsername(prePayment.getUsername());
		}
		
			return paymentDetailsRepository.save(paymentDetails);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PaymentDetails delete(@PathParam(value="id")String id){
		PaymentDetails paymentDetails = paymentDetailsRepository.findOne(id);
		paymentDetailsRepository.delete(paymentDetails);
		return paymentDetails;
	}

}
