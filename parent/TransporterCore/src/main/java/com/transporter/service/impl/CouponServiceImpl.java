package com.transporter.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;


import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.transporter.dao.CouponDao;
import com.transporter.dao.TripDetailsDao;
import com.transporter.exceptions.BusinessException;
import com.transporter.model.Coupon;
import com.transporter.model.CouponDiscountType;
import com.transporter.model.ReferralDetail;
import com.transporter.model.User;
import com.transporter.service.CouponService;
import com.transporter.service.ReferralDetailService;
import com.transporter.utils.CalendarUtils;
import com.transporter.vo.CouponResponseVO;

/**
 * @author SHARAN A
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CouponServiceImpl implements CouponService {

	@Autowired
	private CouponDao couponDao;

	@Autowired
	private TripDetailsDao tripDetailsDao;

	@Autowired
	private ReferralDetailService referralDetailService;

	@Override
	public Coupon saveCoupon(Coupon coupon) {
		boolean isExists = couponDao.isCouponExist(coupon.getId(), coupon.getStartDate(), coupon.getEndDate());
		if (!isExists) {
			coupon.setCreatedOn(CalendarUtils.getCurrentCalendar());
			couponDao.saveCoupon(coupon);
		} else {
			throw new BusinessException("Error", "Coupon code already available");
		}
		return coupon;
	}

	@Override
	public Coupon updateCoupon(Coupon coupon) {
		boolean isExists = couponDao.isCouponExist(coupon.getId(), coupon.getStartDate(), coupon.getEndDate());
		if (!isExists) {
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

		Coupon coupon = couponDao.getCouponForUserAndCode(couponCode);
		
		// if it is first ride coupon
		if (coupon != null && BooleanUtils.isTrue(coupon.getFirstRide())) {
			boolean isFirstRide = isThisFirstRide(userId);
			if(!isFirstRide) {
				coupon = null;
				throw new BusinessException("Error", "Coupon is not valid for first ride");
			}
		}
		// if it is day coupon
		else if(coupon != null && BooleanUtils.isTrue(coupon.getIsDayCoupon())) {
			boolean isValidRideCoupon = isValidDayRideNumberCouponForUser(coupon, userId);
			if(!isValidRideCoupon) {
				coupon = null;
				throw new BusinessException("Error", "Coupon is not valid for day ride");
			}
		}
		// if it is ride number coupon
		else if(coupon != null && coupon.getRideNumber() != null) {
			boolean isValidRideCoupon = isValidRideNumberCouponForUser(coupon, userId);
			if(!isValidRideCoupon) {
				coupon = null;
				throw new BusinessException("Error", "Coupon is not valid for ride");
			}
		}
		// if it is referral coupon
		else if(coupon != null && BooleanUtils.isTrue(coupon.getReferral())) {
			boolean isReferreeCompletedFirstRide = validateIsReferreeCompletedFirstRide(userId);
			if(!isReferreeCompletedFirstRide) {
				coupon = null;
				throw new BusinessException("Error", "Coupon is not valid. Referree has to complete his first ride");
			}
			if (coupon != null && !CollectionUtils.isEmpty(coupon.getExludeUsers())) {
				boolean isValidReferralCoupon = coupon.getExludeUsers().stream().anyMatch(u -> u.getId() == userId);

				if (isValidReferralCoupon) {
					coupon = null;
					throw new BusinessException("Error", "Coupon already applied");
				}
			}
		}
		// if it is not referral coupon
		else if (coupon != null && BooleanUtils.isFalse(coupon.getReferral())) {
			
			if (!CollectionUtils.isEmpty(coupon.getApplyUsers())) {
				boolean isCouponAppliedToUsers = coupon.getApplyUsers().stream().anyMatch(u -> u.getId() == userId);

				if (!isCouponAppliedToUsers) {
					coupon = null;
					throw new BusinessException("Error", "Coupon is not applicable");
				}
			}
			if (!CollectionUtils.isEmpty(coupon.getExludeUsers())) {
				boolean vehiclesContainDodge = coupon.getExludeUsers().stream().anyMatch(u -> u.getId() == userId);

				if (vehiclesContainDodge) {
					coupon = null;
					throw new BusinessException("Error", "Coupon is not valid for ride");
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
	
	private boolean validateIsReferreeCompletedFirstRide(Integer userId) {
		
		List<ReferralDetail> referrals = referralDetailService.findReferralDetail(userId);
		
		if(!CollectionUtils.isEmpty(referrals)) {
			for (ReferralDetail referralDetail : referrals) {
				Integer referreeRideNumber = tripDetailsDao.getTotalRideNumber(referralDetail.getReferreeUser().getId());
				if(referreeRideNumber >= 1) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isValidRideNumberCouponForUser(Coupon coupon, Integer userId) {

		Integer rideNumber = tripDetailsDao.getTotalRideNumber(userId);
		
		if(rideNumber == coupon.getRideNumber()) {
			return true;
		}
		return false;
	}

	private boolean isValidDayRideNumberCouponForUser(Coupon coupon, Integer userId) {
		
		Integer rideNumber = tripDetailsDao.getTotalDayRideNumber(userId, CalendarUtils.getCurrentDate());
		
		if(rideNumber == coupon.getRideNumber()) {
			return true;
		}
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

	@Override
	public boolean isCouponExist(Integer id, Calendar startDate, Calendar endDate) {
		return couponDao.isCouponExist(id, startDate, endDate);
	}
}
