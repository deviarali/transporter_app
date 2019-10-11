package com.transporter.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.transporter.constants.WebConstants;
import com.transporter.exceptions.BusinessException;
import com.transporter.exceptions.ErrorCodes;
import com.transporter.model.Coupon;
import com.transporter.response.CommonResponse;
import com.transporter.service.CouponService;
import com.transporter.utils.RestUtils;
import com.transporter.vo.CouponResponseVO;
import com.transporter.vo.CouponVo;
import com.transporter.vo.GenericSuccessMessage;

@RestController
@RequestMapping(value = "coupon")
public class CouponController {

	@Autowired
	private CouponService couponService;
	
	@Autowired
	private Gson gson;

	@RequestMapping(value = "coupon/saveCoupon", method = RequestMethod.POST)
	public CommonResponse saveCoupon(@RequestBody CouponVo couponVo) {
		CommonResponse response = null;
		try {
			Coupon coupon = gson.fromJson(gson.toJson(couponVo), Coupon.class);
			Coupon code = couponService.saveCoupon(coupon);
			if (code != null) {
				response = RestUtils.wrapObjectForSuccess(code);
			} 
			else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			}
		} 
		catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} 
		catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, e.getMessage());
		}

		return response;
	}

	@RequestMapping(value = "coupon/updateCoupon", method = RequestMethod.PATCH)
	public CommonResponse updateCoupon(@RequestBody CouponVo couponVo) {
		CommonResponse response = null;
		try {
			Coupon coupon = gson.fromJson(gson.toJson(couponVo), Coupon.class);
			Coupon code = couponService.updateCoupon(coupon);
			if (code != null) {
				response = RestUtils.wrapObjectForSuccess(code);
			} 
			else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			}
		} 
		catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} 
		catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, e.getMessage());
		}

		return response;
	}

	@RequestMapping(value = "coupon/getAllCoupon", method = RequestMethod.GET)
	public CommonResponse getAllCoupon() {
		CommonResponse response = null;
		try {
			List<Coupon> couponList = couponService.getAllCoupon();
			if (couponList != null) {
				response = RestUtils.wrapObjectForSuccess(couponList);
			} 
			else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						"No coupon codes available at this moment");
			}
		} 
		catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} 
		catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, e.getMessage());
		}

		return response;
	}

	@RequestMapping(value = "coupon/getAllActiveCoupon", method = RequestMethod.GET)
	public CommonResponse getAllActiveCoupon() {
		CommonResponse response = null;
		try {
			List<Coupon> couponList = couponService.getAllActiveCoupon(true);
			if (couponList != null) {
				response = RestUtils.wrapObjectForSuccess(couponList);
			} 
			else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						"No coupon codes available at this moment");
			}
		} 
		catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} 
		catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, e.getMessage());
		}

		return response;
	}

	@RequestMapping(value = "coupon/deleteCoupon/{couponId}", method = RequestMethod.DELETE)
	public CommonResponse deleteCoupon(@PathVariable int couponId) {
		CommonResponse response = null;
		try {
			int res = couponService.deleteCoupon(couponId);
			if (res != 0) {
				GenericSuccessMessage successMessage = GenericSuccessMessage.Builder.newInstance()
						.setCode(HttpStatus.OK.value()).setMessage("Coupon delete successfully")
						.setStatus(ErrorCodes.SUCCESS.value()).build();
				response = RestUtils.wrapObjectForSuccess(successMessage);
			} 
			else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						"couln't delete coupon code");
			}
		} 
		catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} 
		catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, e.getMessage());
		}

		return response;
	}
	
	@RequestMapping(value = "coupon/applyCoupon/{code}/{userId}/{amount}", method = RequestMethod.GET)
	public CommonResponse applyCoupon(@PathVariable String code, @PathVariable Integer userId, @PathVariable BigDecimal amount) {
		CommonResponse response = null;
		try {
			
			CouponResponseVO couponResponseVO = couponService.applyCoupon(code, userId, amount);
			if("FAIL".equals(couponResponseVO.getStatus())) {
				response = RestUtils.wrapObjectForFailure(couponResponseVO, WebConstants.WEB_RESPONSE_FAILURE, "Coupon not applied");
			}
			else {
				response = RestUtils.wrapObjectForFailure(couponResponseVO, WebConstants.WEB_RESPONSE_SUCCESS, "Coupon applied successfully");
			}
		} 
		catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} 
		catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, e.getMessage());
		}

		return response;
	}
	
	@RequestMapping(value = "coupon/findActiveCoupons/{userId}", method = RequestMethod.GET)
	public CommonResponse findActiveCoupons(@PathVariable Integer userId) {
		CommonResponse response = null;
		try {
			
			List<Coupon> coupons = couponService.findActiveCouponsForUser(userId);
			
			response = RestUtils.wrapObjectForFailure(coupons, WebConstants.WEB_RESPONSE_SUCCESS, "Coupons find");
		} 
		catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} 
		catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, e.getMessage());
		}

		return response;
	}

}
