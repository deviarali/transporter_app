package com.transporter.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;
import com.transporter.constants.WebConstants;
import com.transporter.exceptions.BusinessException;
import com.transporter.model.User;
import com.transporter.response.CommonResponse;
import com.transporter.service.UserService;
import com.transporter.utils.RestUtils;
import com.transporter.vo.UserVo;

@RestController
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "user/updateProfilePicture")
	public CommonResponse updateProfilePicture(HttpServletRequest req,
			@RequestParam(name = "file") MultipartFile multipartFile) {
		CommonResponse response = null;
		String mobileNumber = req.getParameter("mobileNumber");
		try {
			String updateProfilePicture = userService.updateProfilePicture(multipartFile, mobileNumber);
			if (!StringUtils.isNullOrEmpty(updateProfilePicture)) {
				response = RestUtils.wrapObjectForSuccess(updateProfilePicture);
			} else {
				response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.NOT_UPDATED);
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			LOGGER.error("Update profile picture error, mobile number : " + mobileNumber + " exception is : "
					+ e.getMessage());
		}

		return response;
	}

	@RequestMapping(value = "user/updateFcmToken", method = RequestMethod.POST)
	public CommonResponse updateFcmToken(@RequestParam(name = "id") int id,
			@RequestParam(name = "fcmToken") String fcmToken) {
		CommonResponse response = null;
		try {
			String success = userService.updateFcmToken(id, fcmToken);
			if (!StringUtils.isNullOrEmpty(success)) {
				response = RestUtils.wrapObjectForSuccess(success);
			} else {
				response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, WebConstants.WEB_RESPONSE_ERROR,
						"not updated");
			}
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, WebConstants.WEB_RESPONSE_ERROR,
					"internal server error");
			LOGGER.error("fcm token not updated for the user : " + id + " exception is : " + e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "user/updateInternalUser/{id}", method = RequestMethod.PUT)
	public CommonResponse updateCustomer(@RequestBody UserVo userVo, @PathVariable int id) {
		CommonResponse response = null;
		try {
			User userId = userService.findById(id);

			if (userId == null) {
				response = RestUtils.wrapObjectForFailure("user not found", WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.WEB_RESPONSE_NO_RECORD_FOUND);
				return response;
			} 
			User userInternal = userService.updateInternalUser(userVo, id);
			
			response = RestUtils.wrapObjectForSuccess(userVo);
			
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(WebConstants.FAILURE, WebConstants.WEB_RESPONSE_ERROR, WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			LOGGER.error("Internal user not updated for the user : "+id +" exception is : "+e.getMessage());
		}
		return response;
	}
}
