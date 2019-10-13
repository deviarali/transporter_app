package com.transporter.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import com.transporter.model.Coupon;
import com.transporter.vo.CouponResponseVO;

/**
 * @author SHARAN A
 */
public interface CouponService {

	public Coupon saveCoupon(Coupon coupon);

	public Coupon updateCoupon(Coupon coupon);

	public List<Coupon> getAllCoupon();

	public List<Coupon> getAllActiveCoupon(boolean isActive);

	public int deleteCoupon(Integer couponId);
	
	public boolean isCouponExist(Integer id, Calendar startDate, Calendar endDate);
	
	public CouponResponseVO applyCoupon(String couponCode, Integer userId, BigDecimal amount);
	
	public List<Coupon> findActiveCouponsForUser(Integer userId);

}
