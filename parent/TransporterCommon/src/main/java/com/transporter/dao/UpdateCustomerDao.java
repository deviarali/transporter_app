package com.transporter.dao;

import com.transporter.model.User;
import com.transporter.vo.UserVo;

public interface UpdateCustomerDao extends GenericDao {
	boolean isUserExists(String mobileNumber);
	User updateCustomerDetails(UserVo userVo);

}
