package com.transporter.service;

import java.util.List;

import com.transporter.model.CouponCode;

public interface CouponCodeService {

	CouponCode saveCouponCode(CouponCode couponCode);

	CouponCode updateCouponCode(CouponCode couponCode);

	List<CouponCode> getAllCouponCode();

	List<CouponCode> getAllActiveCouponCode(boolean isActive);

	int deleteCouponCode(int couponId);

}
