package com.transporter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transporter.dao.ReferralDetailDao;
import com.transporter.dao.TripDetailsDao;
import com.transporter.exceptions.BusinessException;
import com.transporter.model.ReferralDetail;
import com.transporter.service.ReferralDetailService;
import com.transporter.utils.CalendarUtils;

/**
 * @author SHARAN A
 */
@Service
public class ReferralDetailServiceImpl implements ReferralDetailService {

	@Autowired
	private ReferralDetailDao referralDetailDao;

	@Autowired
	private TripDetailsDao tripDetailsDao;

	@Override
	public ReferralDetail saveReferralDetail(ReferralDetail referralDetail) {
		ReferralDetail code = referralDetailDao.isReferralDetailExist(referralDetail.getId());
		if (code == null) {
			referralDetail.setCreatedOn(CalendarUtils.getCurrentCalendar());
			referralDetailDao.saveReferralDetail(referralDetail);
		} else {
			throw new BusinessException("Error", "ReferralDetail code already available");
		}
		return referralDetail;
	}

	@Override
	public ReferralDetail updateReferralDetail(ReferralDetail referralDetail) {
		ReferralDetail code = referralDetailDao.isReferralDetailExist(referralDetail.getId());
		if (code == null) {
			throw new BusinessException("Error", "ReferralDetail code not available");
		} else {
			referralDetailDao.updateReferralDetail(referralDetail);
		}
		return referralDetail;
	}

	@Override
	public List<ReferralDetail> getAllReferralDetail() {
		return referralDetailDao.getAllReferralDetail();
	}

	@Override
	public List<ReferralDetail> getAllActiveReferralDetail(boolean isActive) {
		return referralDetailDao.getAllActiveReferralDetail(isActive);
	}

	@Override
	public int deleteReferralDetail(Integer referralDetailId) {
		int res = referralDetailDao.deleteReferralDetail(referralDetailId);
		if (res == 0) {
			throw new BusinessException("ReferralDetail code couldn't delete");
		}
		return res;
	}

	@Override
	public List<ReferralDetail> findReferralDetail(Integer referreredId) {
		return referralDetailDao.findReferralDetail(referreredId);
	}

	@Override
	public ReferralDetail getReferralDetail(Integer id) {
		return referralDetailDao.getReferralDetail(id);
	}
}
