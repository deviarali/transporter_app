package com.transporter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transporter.dao.CancelReasonDao;
import com.transporter.model.CancelReasons;
import com.transporter.service.CancelReasonService;
import com.transporter.vo.CancelReasonVo;

@Service
public class CancelReasonServiceImpl implements CancelReasonService {

	@Autowired
	private CancelReasonDao cancelReasonDao;

	@Override
	public List<CancelReasons> cancelReasons() {

		List<CancelReasons> cancelReasons = cancelReasonDao.getCancelReason();

		return cancelReasons;
	}

}
