package com.transporter.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ErrorCoded;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.transporter.constants.WebConstants;
import com.transporter.exceptions.BusinessException;
import com.transporter.exceptions.ErrorCodes;
import com.transporter.model.CouponCode;
import com.transporter.response.CommonResponse;
import com.transporter.service.CouponCodeService;
import com.transporter.utils.RestUtils;
import com.transporter.vo.CouponCodeVo;
import com.transporter.vo.CustomerDetailsVo;
import com.transporter.vo.GenericSuccessMessage;

@RestController
public class CouponCodeController {

	@Autowired
	CouponCodeService couponCodeService;
	@Autowired
	Gson gson;

	@RequestMapping(value = "coupon/saveCouponCode", method = RequestMethod.POST)
	public CommonResponse saveCouponCode(@RequestBody CouponCodeVo couponCodeVo) {
		CommonResponse response = null;
		try {
			CouponCode couponCode = gson.fromJson(gson.toJson(couponCodeVo), CouponCode.class);
			CouponCode code = couponCodeService.saveCouponCode(couponCode);
			if (code != null) {
				response = RestUtils.wrapObjectForSuccess(code);
			} else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, e.getMessage());
		}

		return response;
	}

	@RequestMapping(value = "coupon/updateCouponCode", method = RequestMethod.PATCH)
	public CommonResponse updateCouponCode(@RequestBody CouponCodeVo couponCodeVo) {
		CommonResponse response = null;
		try {
			CouponCode couponCode = gson.fromJson(gson.toJson(couponCodeVo), CouponCode.class);
			CouponCode code = couponCodeService.updateCouponCode(couponCode);
			if (code != null) {
				response = RestUtils.wrapObjectForSuccess(code);
			} else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, e.getMessage());
		}

		return response;
	}

	@RequestMapping(value = "coupon/getAllCouponCode", method = RequestMethod.GET)
	public CommonResponse getAllCouponCode() {
		CommonResponse response = null;
		try {
			List<CouponCode> couponCodeList = couponCodeService.getAllCouponCode();
			if (couponCodeList != null) {
				response = RestUtils.wrapObjectForSuccess(couponCodeList);
			} else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						"No coupon codes available at this moment");
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, e.getMessage());
		}

		return response;
	}

	@RequestMapping(value = "coupon/getAllActiveCouponCode", method = RequestMethod.GET)
	public CommonResponse getAllActiveCouponCode() {
		CommonResponse response = null;
		try {
			List<CouponCode> couponCodeList = couponCodeService.getAllActiveCouponCode(true);
			if (couponCodeList != null) {
				response = RestUtils.wrapObjectForSuccess(couponCodeList);
			} else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						"No coupon codes available at this moment");
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, e.getMessage());
		}

		return response;
	}

	@RequestMapping(value = "coupon/deleteCouponCode/{couponCodeId}", method = RequestMethod.DELETE)
	public CommonResponse deleteCouponCode(@PathVariable int couponCodeId) {
		CommonResponse response = null;
		try {
			int res = couponCodeService.deleteCouponCode(couponCodeId);
			if (res != 0) {
				response = RestUtils.wrapObjectForSuccess(
						GenericSuccessMessage.Builder.newInstance().setCode(HttpStatus.OK.toString())
								.setMessage("Coupon delete successfully").setStatus(ErrorCodes.SUCCESS.value()));
			} else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						"couln't delete coupon code");
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, e.getMessage());
		}

		return response;
	}

}
