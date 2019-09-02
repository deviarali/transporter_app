package com.transporter.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;
import com.transporter.constants.WebConstants;
import com.transporter.response.CommonResponse;
import com.transporter.service.UserService;
import com.transporter.utils.RestUtils;
import com.transporter.vo.UserVo;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(name = "/updateProfilePicture")
	public CommonResponse updateProfilePicture(HttpServletRequest req, @RequestParam(name = "file") MultipartFile multipartFile) {
		CommonResponse response = null;
		String mobileNumber = req.getParameter("mobileNumber");
		
		UserVo userVo = userService.isUserExists(mobileNumber);
		if(userVo == null) {
			response = RestUtils.wrapObjectForFailure("user not found", WebConstants.WEB_RESPONSE_ERROR, WebConstants.WEB_RESPONSE_NO_RECORD_FOUND);
			return response;
		}
		String updateProfilePicture = userService.updateProfilePicture(multipartFile, mobileNumber);
		if(!StringUtils.isNullOrEmpty(updateProfilePicture)) {
			response = RestUtils.wrapObjectForSuccess(updateProfilePicture);
		} else {
			response = RestUtils.wrapObjectForFailure("picture not updated", WebConstants.WEB_RESPONSE_ERROR, WebConstants.WEB_RESPONSE_ERROR);
		}
		
		return response;
	}
}
