package com.transporter.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.transporter.dao.CouponDao;
import com.transporter.exceptions.BusinessException;
import com.transporter.model.Coupon;
import com.transporter.model.CouponDiscountType;
import com.transporter.model.User;
import com.transporter.service.CouponService;
import com.transporter.utils.CalendarUtils;
import com.transporter.vo.CouponResponseVO;

/**
 * @author SHARAN A
 */
@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	CouponDao couponDao;

	@Override
	public Coupon saveCoupon(Coupon coupon) {
		Coupon code = couponDao.isCouponExist(coupon.getId());
		if (code == null) {
			coupon.setCreatedOn(CalendarUtils.getCurrentCalendar());
			couponDao.saveCoupon(coupon);
		} else {
			throw new BusinessException("Error", "Coupon code already available");
		}
		return coupon;
	}

	@Override
	public Coupon updateCoupon(Coupon coupon) {
		Coupon code = couponDao.isCouponExist(coupon.getId());
		if (code == null) {
			throw new BusinessException("Error", "Coupon code not available");
		} else {
			coupon.setUpdatedOn(CalendarUtils.getCurrentCalendar());
			couponDao.updateCoupon(coupon);
		}
		return coupon;
	}

	@Override
	public List<Coupon> getAllCoupon() {
		return couponDao.getAllCoupon();
	}

	@Override
	public List<Coupon> getAllActiveCoupon(boolean isActive) {
		return couponDao.getAllActiveCoupon(isActive);
	}

	@Override
	public int deleteCoupon(Integer couponId) {
		int res = couponDao.deleteCoupon(couponId);
		if (res == 0) {
			throw new BusinessException("Coupon code couldn't delete");
		}
		return res;
	}

	@Override
	public CouponResponseVO applyCoupon(String couponCode, Integer userId, BigDecimal amount) {

		BigDecimal calculatedAmount = BigDecimal.ZERO;
		CouponResponseVO counponResponse = null;
		Coupon coupon = null;

		coupon = getCouponForUserAndCode(couponCode, userId);

		if (coupon != null) {
			counponResponse = calculateAmountWithCoupon(coupon, amount);
		} else {
			counponResponse = new CouponResponseVO(couponCode, BigDecimal.ZERO, amount, "FAIL");
		}

		return counponResponse;
	}

	private Coupon getCouponForUserAndCode(String couponCode, Integer userId) {

		boolean isFirstRide = isThisFirstRide(userId);

		Coupon coupon = null;
		
		if (isFirstRide) {
			coupon = getFirstRideCoupon(couponCode);
		} else {
			coupon = couponDao.getCouponForUserAndCode(couponCode);
		}

		// if it is ride number coupon
		if(coupon != null && coupon.getRideNumber() != null) {
			boolean isValidRideCoupon = isValidRideNumberCouponForUser(userId);
			if(!isValidRideCoupon) {
				coupon = null;
			}
		}
		// if it is referral coupon
		if(coupon != null && BooleanUtils.isTrue(coupon.getReferral())) {
			if (!CollectionUtils.isEmpty(coupon.getExludeUsers())) {
				boolean isValidReferralCoupon = coupon.getExludeUsers().stream().anyMatch(u -> u.getId() == userId);

				if (isValidReferralCoupon) {
					coupon = null;
				}
			}
		}
		
		// if it is not referral coupon
		if (coupon != null && BooleanUtils.isFalse(coupon.getReferral())) {
			
			if (!CollectionUtils.isEmpty(coupon.getApplyUsers())) {
				boolean vehiclesContainDodge = coupon.getApplyUsers().stream().anyMatch(u -> u.getId() == userId);

				if (!vehiclesContainDodge) {
					coupon = null;
				}
			}
			if (!CollectionUtils.isEmpty(coupon.getExludeUsers())) {
				boolean vehiclesContainDodge = coupon.getExludeUsers().stream().anyMatch(u -> u.getId() == userId);

				if (vehiclesContainDodge) {
					coupon = null;
				}
			}
		}
		
		// excluding user to apply multiple times
		if(coupon != null) {
			User user = new User();
			user.setId(userId);
			coupon.getExludeUsers().add(user);
			couponDao.updateCoupon(coupon);

		}

		return coupon;

	}

	private boolean isValidReferralCouponForUser(Integer userId) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isValidRideNumberCouponForUser(Integer userId) {
		return false;
	}

	private Coupon getFirstRideCoupon(String couponCode) {
		return couponDao.getFirstRideCoupon(couponCode);
	}

	private boolean isThisFirstRide(Integer userId) {

		return false;
	}

	private CouponResponseVO calculateAmountWithCoupon(Coupon coupon, BigDecimal amount) {
		CouponResponseVO counponResponse = null;
		BigDecimal calculatedAmount = BigDecimal.ZERO;
		BigDecimal totamAmountAfterDiscount = BigDecimal.ZERO;
		
		if (CouponDiscountType.FLAT.equals(coupon.getDiscountType())) {
			if (coupon.getAmountOrPercentage().compareTo(amount) >= 0) {
				totamAmountAfterDiscount = BigDecimal.ZERO;
			} else {
				totamAmountAfterDiscount = coupon.getAmountOrPercentage();
			}
		} else {
			totamAmountAfterDiscount = calculateDiscountAmount(coupon.getAmountOrPercentage(), amount);

		}

		calculatedAmount = amount.subtract(totamAmountAfterDiscount);

		counponResponse = new CouponResponseVO(coupon.getCouponCode(), totamAmountAfterDiscount, calculatedAmount, "SUCCESS");

		return counponResponse;
	}

	private BigDecimal calculateDiscountAmount(BigDecimal percentage, BigDecimal amount) {
		BigDecimal amountToDiscount = amount.multiply(percentage).divide(new BigDecimal(100), RoundingMode.HALF_UP);
		
		if(amountToDiscount.signum() == -1) {
			return amount;
		}
		return amountToDiscount;
		
	}

	public List<Coupon> findActiveCouponsForUser(Integer userId) {
		List<Coupon> coupons = Lists.newArrayList();

		List<Coupon> couponList = couponDao.findActiveCoupons();

		if (!CollectionUtils.isEmpty(couponList)) {
			for (Coupon coupon : couponList) {
				if (!CollectionUtils.isEmpty(coupon.getApplyUsers())) {
					boolean vehiclesContainDodge = coupon.getApplyUsers().stream().anyMatch(u -> u.getId() == userId);

					if (!vehiclesContainDodge) {
						continue;
					}
				}
				if (!CollectionUtils.isEmpty(coupon.getExludeUsers())) {
					boolean vehiclesContainDodge = coupon.getExludeUsers().stream().anyMatch(u -> u.getId() == userId);

					if (vehiclesContainDodge) {
						continue;
					}
				}

				coupons.add(coupon);
			}

		}

		return coupons;
	}

}
