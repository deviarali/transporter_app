package com.transporter.service;

import java.math.BigDecimal;
import java.util.List;

import com.transporter.model.Coupon;
import com.transporter.vo.CouponResponseVO;

/**
 * @author SHARAN A
 */
public interface CouponService {

	Coupon saveCoupon(Coupon coupon);

	Coupon updateCoupon(Coupon coupon);

	List<Coupon> getAllCoupon();

	List<Coupon> getAllActiveCoupon(boolean isActive);

	int deleteCoupon(Integer couponId);
	
	public CouponResponseVO applyCoupon(String couponCode, Integer userId, BigDecimal amount);
	
	public List<Coupon> findActiveCouponsForUser(Integer userId);

}
