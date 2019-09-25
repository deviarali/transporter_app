package com.transporter.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.transporter.dao.CouponCodeDao;
import com.transporter.exceptions.BusinessException;
import com.transporter.model.CouponCode;
import com.transporter.service.CouponCodeService;

@Service
public class CouponCodeServiceImpl implements CouponCodeService {

	@Autowired
	CouponCodeDao couponCodeDao;

	@Override
	public CouponCode saveCouponCode(CouponCode couponCode) {
		CouponCode code = couponCodeDao.isCouponCodeExist(couponCode.getCouponCodeId());
		if (code == null) {
			couponCode.setCouponCodeCreatedAt(new Date().toString());
			couponCodeDao.saveCouponCode(couponCode);
		} else {
			throw new BusinessException("Error", "Coupon code already available");
		}
		return couponCode;
	}

	@Override
	public CouponCode updateCouponCode(CouponCode couponCode) {
		CouponCode code = couponCodeDao.isCouponCodeExist(couponCode.getCouponCodeId());
		if (code == null) {
			throw new BusinessException("Error", "Coupon code not available");
		} else {
			couponCode.setCouponCodeUpdatedAt(new Date().toString());
			couponCodeDao.updateCouponCode(couponCode);
		}
		return couponCode;
	}

	@Override
	public List<CouponCode> getAllCouponCode() {
		return couponCodeDao.getAllCouponCode();
	}

	@Override
	public List<CouponCode> getAllActiveCouponCode(boolean isActive) {
		return couponCodeDao.getAllActiveCouponCode(isActive);
	}

	@Override
	public int deleteCouponCode(int couponId) {
		int res = couponCodeDao.deleteCouponCode(couponId);
		if (res == 0) {
			throw new BusinessException("Coupon code couldn't delete");
		}
		return res;
	}

}
