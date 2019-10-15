package com.transporter.dao;

import java.util.Calendar;
import java.util.List;

import com.transporter.model.Coupon;

/**
 * @author SHARAN A
 */
public interface CouponDao extends GenericDao {

	public Coupon saveCoupon(Coupon coupon);

	public Coupon updateCoupon(Coupon coupon);

	public List<Coupon> getAllCoupon();

	public List<Coupon> getAllActiveCoupon(boolean isActive);

	public int deleteCoupon(Integer couponId);
	
	public Coupon isCouponExist(Integer code);

	public boolean isCouponExist(Integer id, Calendar startDate, Calendar endDate);
	
	public Coupon getFirstRideCoupon(String couponCode);

	public Coupon getCouponForUserAndCode(String couponCode);
	
	public List<Coupon> findActiveCoupons();
}
