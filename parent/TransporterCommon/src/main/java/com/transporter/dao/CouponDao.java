package com.transporter.dao;

import java.util.List;

import com.transporter.model.Coupon;

/**
 * @author SHARAN A
 */
public interface CouponDao extends GenericDao {

	Coupon saveCoupon(Coupon coupon);

	Coupon updateCoupon(Coupon coupon);

	List<Coupon> getAllCoupon();

	List<Coupon> getAllActiveCoupon(boolean isActive);

	int deleteCoupon(Integer couponId);
	
	Coupon isCouponExist(Integer code);
	
	public Coupon getFirstRideCoupon(String couponCode);

	public Coupon getCouponForUserAndCode(String couponCode);
	
	public List<Coupon> findActiveCoupons();
}
