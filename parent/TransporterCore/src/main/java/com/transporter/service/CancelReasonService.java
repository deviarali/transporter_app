package com.transporter.service;

import java.util.List;

import com.transporter.model.CancelReasons;

public interface CancelReasonService {
	List<CancelReasons> cancelReasons();
	String addCancelReaseon(CancelReasons cancelReasons);
	int deleteCancelReason(CancelReasons cancelReasons);
}
