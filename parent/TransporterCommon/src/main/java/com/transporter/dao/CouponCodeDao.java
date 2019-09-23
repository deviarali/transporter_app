package com.transporter.dao;

import java.util.List;

import com.transporter.model.CouponCode;

public interface CouponCodeDao extends GenericDao {

	CouponCode saveCouponCode(CouponCode couponCode);

	CouponCode updateCouponCode(CouponCode couponCode);

	List<CouponCode> getAllCouponCode();

	List<CouponCode> getAllActiveCouponCode(boolean isActive);

	int deleteCouponCode(int couponId);
	
	CouponCode isCouponCodeExist(int code);
}
