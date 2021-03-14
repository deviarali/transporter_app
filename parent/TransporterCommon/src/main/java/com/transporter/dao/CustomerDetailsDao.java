package com.transporter.dao;

import com.transporter.model.CustomerDetails;
import com.transporter.vo.CustomerDetailsVo;

public interface CustomerDetailsDao extends GenericDao {

	/*int updateCustomer(CustomerDetails customerDetails);*/

	CustomerDetails findCustomerByUserId(int id);

	int updateCustomer(CustomerDetailsVo customerDetailsVo);

}
