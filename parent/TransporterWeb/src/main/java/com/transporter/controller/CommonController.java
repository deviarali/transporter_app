package com.transporter.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.transporter.constants.WebConstants;
import com.transporter.exceptions.BusinessException;
import com.transporter.response.CommonResponse;
import com.transporter.service.CommonService;
import com.transporter.utils.RestUtils;
import com.transporter.vo.InternalUserRoleMasterVo;

/**
 * @author Devappa.Arali
 *
 */

@RestController
public class CommonController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private CommonService commonService;
	
	@RequestMapping(value = "internalUserRoles", method = RequestMethod.GET)
	public CommonResponse getInternalUserRoles() {
		CommonResponse response = null;
		try {
			List<InternalUserRoleMasterVo> internalUserRoles  = commonService.getInternalUserRoles();
			response = RestUtils.wrapObjectForSuccess(internalUserRoles);
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, e.getMessage());
			LOGGER.error("Erroe while fetching internaluserroles api");		
		}
		return response;
	}
}
