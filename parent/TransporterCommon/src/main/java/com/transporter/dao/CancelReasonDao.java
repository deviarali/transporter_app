package com.transporter.dao;

import java.util.List;

import com.transporter.model.CancelReasons;


public interface CancelReasonDao extends GenericDao{
	
	List<CancelReasons> getCancelReason();
	
}
