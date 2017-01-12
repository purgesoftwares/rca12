package com.siv.controllers.coupon;

import java.util.Date;

import javax.validation.Valid;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.siv.model.coupon.Coupon;
import com.siv.repository.coupon.CouponRepository;

@Path("/secured/coupon")
public class CouponController {
	
	@Autowired
	private CouponRepository couponRepository;
	
	@POST
	@Produces("application/json")
	public Coupon create(Coupon coupon){
		coupon.setStartTime(new Date());
		coupon.setEndTime(new Date());		
		return couponRepository.save(coupon);		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon findOne(@PathParam(value="id")String id){
		return couponRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<Coupon> findAll(Pageable pageble){
		
		return new PageImpl<Coupon>(couponRepository.findAll(pageble).getContent(), pageble, 20);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon update(@PathParam(value="id")String id, @Valid Coupon coupon){
		Coupon preCoupon = couponRepository.findOne(id);
		coupon.setId(id);
		coupon.setStartTime(preCoupon.getStartTime());
		coupon.setEndTime(new Date());
		
		if(coupon.getCouponCode() == null){
			coupon.setCouponCode(preCoupon.getCouponCode());
		} if(coupon.getCouponNumber() == null){
			coupon.setCouponNumber(preCoupon.getCouponNumber());
		} if(coupon.getAvailability() == null){
			coupon.setAvailability(preCoupon.getAvailability());
		} if(coupon.getPrice() == null) {
			coupon.setPrice(preCoupon.getPrice());
		} if(coupon.getUsed() == null) {
			coupon.setUsed(preCoupon.getUsed());
		} if(coupon.getProviderId() == null){
			coupon.setProviderId(preCoupon.getProviderId());
		}
		return couponRepository.save(coupon);	
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon delete(@PathParam(value="id")String id){
		Coupon coupon = couponRepository.findOne(id);
		couponRepository.delete(coupon);
		return coupon;
	}

}
