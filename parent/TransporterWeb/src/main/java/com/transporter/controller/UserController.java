package com.transporter.controller;

import javax.servlet.http.HttpServletRequest;

//import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.transporter.constants.WebConstants;
import com.transporter.exceptions.BusinessException;
import com.transporter.model.User;
import com.transporter.response.CommonResponse;
import com.transporter.service.UserService;
import com.transporter.utils.RestUtils;
import com.transporter.vo.UserVo;

@CrossOrigin("*")
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

			// TikaServerValidationUtils.validateImageFile(multipartFile.getInputStream());

			String updateProfilePicture = userService.updateProfilePicture(multipartFile, mobileNumber);
			if (!StringUtils.isBlank(updateProfilePicture)) {
				response = RestUtils.wrapObjectForSuccess(updateProfilePicture);
			} else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.NOT_UPDATED);
			}
		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
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
			if (!StringUtils.isBlank(success)) {
				response = RestUtils.wrapObjectForSuccess(success);
			} else {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, "not updated");
			}
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR, "internal server error");
			LOGGER.error("fcm token not updated for the user : " + id + " exception is : " + e.getMessage());
		}
		return response;
	}

	/* IntenalUser update API */
	@RequestMapping(value = "user/updateInternalUser/{id}", method = RequestMethod.PUT)
	public CommonResponse updateCustomer(@RequestBody UserVo userVo, @PathVariable int id) {
		CommonResponse response = null;
		try {
			User userId = userService.findById(id);

			if (userId == null) {
				response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
						WebConstants.WEB_RESPONSE_NO_RECORD_FOUND);
				return response;
			}
			userService.updateInternalUser(userVo, id);

			response = RestUtils.wrapObjectForSuccess(userVo);

		} catch (BusinessException be) {
			response = RestUtils.wrapObjectForFailure(null, be.getErrorCode(), be.getErrorMsg());
			LOGGER.error("Internal user not updated for the user:" + " exception : " + be.getErrorMsg());
		} catch (Exception e) {
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			LOGGER.error("Internal user not updated for the user : " + id + " exception is : " + e.getMessage());
		}
		return response;
	}

	/**
	 * Get total users count by role id
	 * 
	 * @author naveen
	 * @param roleId
	 * @return
	 */
	@GetMapping(path = "/v1/user/getUsersCountByRoleId/{roleId}")
	public CommonResponse getTotalUsersCountByRole(@PathVariable(name = "roleId") int roleId) {
		CommonResponse response = null;
		try {
			int totalUsersCountByRole = userService.getTotalUsersCountByRole(roleId);
			response = RestUtils.wrapObjectForSuccess(totalUsersCountByRole);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Internal error while getting user count for role id: " + roleId + " : Exception is: "
					+ e.getMessage());
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;
	}

	/**
	 * Below api to get the total no users registered for a day
	 * 
	 * @author naveen
	 * @param roleId
	 * @return
	 */
	@GetMapping(path = "/v1/user/getUsersCountForDay/{roleId}")
	public CommonResponse getTotalUsersCountForCurrentDay(@PathVariable(name = "roleId") int roleId) {
		CommonResponse response = null;
		try {
			int totalUsersCountByRole = userService.getTotalUsersCountForToday(roleId);
			response = RestUtils.wrapObjectForSuccess(totalUsersCountByRole);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Internal error while getting user count for current day role id: " + roleId
					+ " : Exception is: " + e.getMessage());
			response = RestUtils.wrapObjectForFailure(null, WebConstants.WEB_RESPONSE_ERROR,
					WebConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;
	}
	
	@PostMapping(path = "/login")
	public CommonResponse login(@RequestBody UserVo user) {
		CommonResponse response = null;
		user = userService.login(user);
		if(null != user)
			response = RestUtils.wrapObjectForSuccess(user);
		return response;
	}

}
