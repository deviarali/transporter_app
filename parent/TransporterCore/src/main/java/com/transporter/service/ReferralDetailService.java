package com.transporter.service;

import java.util.List;

import com.transporter.model.ReferralDetail;

/**
 * @author SHARAN A
 */
public interface ReferralDetailService {

	public ReferralDetail saveReferralDetail(ReferralDetail referralDetail);

	public ReferralDetail updateReferralDetail(ReferralDetail referralDetail);

	public ReferralDetail getReferralDetail(Integer id);

	public List<ReferralDetail> getAllReferralDetail();

	public List<ReferralDetail> getAllActiveReferralDetail(boolean isActive);

	public int deleteReferralDetail(Integer referralDetailId);

	public List<ReferralDetail> findReferralDetail(Integer referreredId);

}
