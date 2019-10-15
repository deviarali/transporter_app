package com.transporter.controller;

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
import com.transporter.model.ReferralDetail;
import com.transporter.model.User;
import com.transporter.response.CommonResponse;
import com.transporter.service.ReferralDetailService;
import com.transporter.utils.RestUtils;
import com.transporter.vo.GenericSuccessMessage;
import com.transporter.vo.ReferralDetailVo;

/**
 * @author SHARAN A
 */
@RestController
public class ReferralDetailController {

	@Autowired
	private ReferralDetailService referralDetailService;
	
	@Autowired
	private Gson gson;

	@RequestMapping(value = "referralDetail/saveReferralDetail", method = RequestMethod.POST)
	public CommonResponse saveReferralDetail(@RequestBody ReferralDetailVo referralDetailVo) {
		CommonResponse response = null;
		try {
			ReferralDetail referralDetail = gson.fromJson(gson.toJson(referralDetailVo), ReferralDetail.class);
			ReferralDetail code = referralDetailService.saveReferralDetail(referralDetail);
			if (code != null) {
				response = RestUtils.wrapObjectForSuccess(code);
			} 
			else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
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

	@RequestMapping(value = "referralDetail/updateReferralDetail", method = RequestMethod.PATCH)
	public CommonResponse updateReferralDetail(@RequestBody ReferralDetailVo referralDetailVo) {
		CommonResponse response = null;
		try {
			ReferralDetail referralDetail = gson.fromJson(gson.toJson(referralDetailVo), ReferralDetail.class);
			ReferralDetail code = referralDetailService.updateReferralDetail(referralDetail);
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

	@RequestMapping(value = "referralDetail/getAllReferralDetail", method = RequestMethod.GET)
	public CommonResponse getAllReferralDetail() {
		CommonResponse response = null;
		try {
			List<ReferralDetail> referralDetailList = referralDetailService.getAllReferralDetail();
			if (referralDetailList != null) {
				response = RestUtils.wrapObjectForSuccess(referralDetailList);
			} 
			else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						"No referralDetail codes available at this moment");
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

	@RequestMapping(value = "referralDetail/getAllActiveReferralDetail", method = RequestMethod.GET)
	public CommonResponse getAllActiveReferralDetail() {
		CommonResponse response = null;
		try {
			List<ReferralDetail> referralDetailList = referralDetailService.getAllActiveReferralDetail(true);
			if (referralDetailList != null) {
				response = RestUtils.wrapObjectForSuccess(referralDetailList);
			} 
			else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						"No referralDetail codes available at this moment");
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

	@RequestMapping(value = "referralDetail/deleteReferralDetail/{referralDetailId}", method = RequestMethod.DELETE)
	public CommonResponse deleteReferralDetail(@PathVariable int referralDetailId) {
		CommonResponse response = null;
		try {
			int res = referralDetailService.deleteReferralDetail(referralDetailId);
			if (res != 0) {
				GenericSuccessMessage successMessage = GenericSuccessMessage.Builder.newInstance()
						.setCode(HttpStatus.OK.value()).setMessage("ReferralDetail delete successfully")
						.setStatus(ErrorCodes.SUCCESS.value()).build();
				response = RestUtils.wrapObjectForSuccess(successMessage);
			} 
			else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						"couln't delete referralDetail code");
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

	@RequestMapping(value = "referralDetail/updateReferreeToReferralDetail/{id}/{refereeId}", method = RequestMethod.PATCH)
	public CommonResponse updateReferreeToReferralDetail(@PathVariable Integer id, Integer refereeId) {
		CommonResponse response = null;
		try {
			ReferralDetail referralDetail = referralDetailService.getReferralDetail(refereeId);
			User user = new User();
			user.setId(refereeId);
			referralDetail.setReferreeUser(user);
			
			ReferralDetail code = referralDetailService.updateReferralDetail(referralDetail);
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

}
