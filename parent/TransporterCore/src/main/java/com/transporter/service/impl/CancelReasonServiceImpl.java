package com.transporter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transporter.constants.WebConstants;
import com.transporter.dao.CancelReasonDao;
import com.transporter.model.CancelReasons;
import com.transporter.service.CancelReasonService;

@Service
public class CancelReasonServiceImpl implements CancelReasonService {

	@Autowired
	private CancelReasonDao cancelReasonDao;

	@Override
	public List<CancelReasons> cancelReasons() {

		List<CancelReasons> cancelReasons = cancelReasonDao.getCancelReason();

		return cancelReasons;
	}

	@Override
	public String addCancelReaseon(CancelReasons reason) {
		String status = "";
		try {
			cancelReasonDao.addCancelReason(reason);
			status = WebConstants.SUCCESS;
		}catch(Exception e) {
			status = WebConstants.FAILURE;
		}
		return status;
	}

	@Override
	public int deleteCancelReason(CancelReasons cancelReasons) {
		int status = cancelReasonDao.deleteCancelReason(cancelReasons.getId());
		return status;
	}

}
