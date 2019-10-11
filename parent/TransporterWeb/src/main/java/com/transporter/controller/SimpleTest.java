package com.transporter.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.Lists;
import com.google.api.client.util.Sets;
import com.google.gson.Gson;
import com.transporter.model.Coupon;
import com.transporter.model.CouponDiscountType;
import com.transporter.utils.CalendarUtils;
import com.transporter.vo.CouponVo;
import com.transporter.vo.UserVo;

public class SimpleTest {

	public static void main(String[] args) {

		Gson gson = new Gson();
		
		CouponVo couponVo = new CouponVo();
		
		couponVo.setId(3);
		couponVo.setCouponCode("OCT20");
		couponVo.setDiscountType(CouponDiscountType.PERCENTAGE);
		couponVo.setAmountOrPercentage(new BigDecimal("20.00"));
		couponVo.setIsActive(Boolean.TRUE);
		couponVo.setRideNumber(null);
		couponVo.setFirstRide(Boolean.FALSE);
		couponVo.setReferral(Boolean.TRUE);
		couponVo.setStartDate(CalendarUtils.getCurrentCalendar());
		couponVo.setEndDate(CalendarUtils.getCurrentCalendar());;
		couponVo.setUpdatedOn(CalendarUtils.getCurrentCalendar());;
		couponVo.setCreatedOn(CalendarUtils.getCurrentCalendar());;
		
		UserVo userVo17 = new UserVo();
		userVo17.setId(17);
		UserVo userVo18 = new UserVo();
		userVo18.setId(18);
		UserVo userVo19 = new UserVo();
		userVo19.setId(19);
		UserVo userVo20 = new UserVo();
		userVo20.setId(20);
		
		Set<UserVo> applyUsers = Sets.newHashSet();
		applyUsers.add(userVo17);
		applyUsers.add(userVo18);
		Set<UserVo> excludeUsers = Sets.newHashSet();
		excludeUsers.add(userVo19);
		excludeUsers.add(userVo20);
		
		couponVo.setApplyUsers(applyUsers);
		couponVo.setExludeUsers(excludeUsers);

		ObjectMapper mapper = new ObjectMapper();
		
		String json = null;
		try {
			json = mapper.writeValueAsString(couponVo);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//.toJson(gson.toJson(couponVo), CouponVo.class);
		System.out.println(json);

	}

}
