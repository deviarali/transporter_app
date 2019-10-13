package com.transporter.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.transporter.dao.ReferralDetailDao;
import com.transporter.model.Coupon;
import com.transporter.model.ReferralDetail;

/**
 * @author SHARAN A
 */
@Repository
@Transactional
public class ReferralDetailDaoImpl extends GenericDaoImpl implements ReferralDetailDao {

	@Override
	public ReferralDetail saveReferralDetail(ReferralDetail referralDetail) {
		save(referralDetail);
		return referralDetail;
	}

	@Override
	public ReferralDetail updateReferralDetail(ReferralDetail referralDetail) {
		saveOrUpdate(referralDetail);
		return referralDetail;
	}

	@Override
	public List<ReferralDetail> getAllReferralDetail() {
		
		List<ReferralDetail> referrals = loadAll(ReferralDetail.class);
		if(!CollectionUtils.isEmpty(referrals)) {
			for (ReferralDetail detail : referrals) {
				Hibernate.initialize(detail.getReferredUser());
				Hibernate.initialize(detail.getReferreeUser());
			}
		}
		return referrals;
	}

	@Override
	public List<ReferralDetail> getAllActiveReferralDetail(boolean isActive) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "FROM ReferralDetail detail where detail.isActive = :isActive";
		
		Query query = session.createQuery(sqlQuery);
		
		query.setParameter("isActive", isActive);
		List<ReferralDetail> referrals = query.list();
		if(!CollectionUtils.isEmpty(referrals)) {
			for (ReferralDetail detail : referrals) {
				Hibernate.initialize(detail.getReferredUser());
				Hibernate.initialize(detail.getReferreeUser());
			}
		}
		return referrals;
	}

	@Override
	public int deleteReferralDetail(Integer referralDetailId) {
		Session session = sessionFactory.getCurrentSession();
		
		Query qry = session.createQuery("delete from ReferralDetail detail where detail.id = :referralDetailId");
		
		qry.setParameter("referralDetailId", referralDetailId);
		
		return qry.executeUpdate();
	}

	@Override
	public ReferralDetail isReferralDetailExist(Integer referralDetailId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "FROM ReferralDetail detail where detail.id= :referralDetailId";
		
		Query query = session.createQuery(sqlQuery);
		
		query.setParameter("referralDetailId", referralDetailId);
		
		ReferralDetail detail = (ReferralDetail) query.uniqueResult();
		if(detail != null) {
			Hibernate.initialize(detail.getReferreeUser());
		}

		return detail;
	}

	@Override
	public List<ReferralDetail> findReferralDetail(Integer referreredId) {
		Session session = sessionFactory.getCurrentSession();
		
		String sqlQuery = "select distinct detail FROM ReferralDetail detail "
				+ " inner join detail.referreeUser referree "
				+ " where detail.isActive = true "
				+ " and detail.referredUser.id = :referreredId ";
		
		Query query = session.createQuery(sqlQuery);
		query.setParameter("referreredId", referreredId);

		List<ReferralDetail> details = query.list();;
		if(!CollectionUtils.isEmpty(details)) {
			for (ReferralDetail detail : details) {
				Hibernate.initialize(detail.getReferredUser());
				Hibernate.initialize(detail.getReferreeUser());
			}
		}
		
		return query.list();
	}

	@Override
	public ReferralDetail getReferralDetail(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		
		return session.get(ReferralDetail.class, id);
		
	}

}
