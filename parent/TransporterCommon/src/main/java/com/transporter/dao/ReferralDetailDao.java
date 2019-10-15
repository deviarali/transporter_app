package com.transporter.dao;

import java.util.List;

import com.transporter.model.ReferralDetail;

/**
 * @author SHARAN A
 */
public interface ReferralDetailDao extends GenericDao {

	public ReferralDetail saveReferralDetail(ReferralDetail referralDetail);

	public ReferralDetail updateReferralDetail(ReferralDetail referralDetail);

	public List<ReferralDetail> getAllReferralDetail();

	public List<ReferralDetail> getAllActiveReferralDetail(boolean isActive);

	public int deleteReferralDetail(Integer referralDetailId);
	
	public ReferralDetail isReferralDetailExist(Integer code);

	public List<ReferralDetail> findReferralDetail(Integer referreredId);

	public ReferralDetail getReferralDetail(Integer id);
}
