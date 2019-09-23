package com.transporter.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.transporter.dao.CouponCodeDao;
import com.transporter.model.CouponCode;

@Repository
@Transactional
public class CouponCodeDaoImpl extends GenericDaoImpl implements CouponCodeDao {

	@Override
	public CouponCode saveCouponCode(CouponCode couponCode) {
		save(couponCode);
		return couponCode;
	}

	@Override
	public CouponCode updateCouponCode(CouponCode couponCode) {
		saveOrUpdate(couponCode);
		return couponCode;
	}

	@Override
	public List<CouponCode> getAllCouponCode() {
		return loadAll(CouponCode.class);
	}

	@Override
	public List<CouponCode> getAllActiveCouponCode(boolean isActive) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "FROM CouponCode code where code.isActive= :isActive";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("isActive", isActive);
		List<CouponCode> couponCodeLsit = (List<CouponCode>) query.list();
		return couponCodeLsit;
	}

	@Override
	public int deleteCouponCode(int couponCodeId) {
		Session session = sessionFactory.getCurrentSession();
		Query qry = session.createQuery("delete from CouponCode code where code.couponCodeId=:couponCodeId");
		qry.setParameter("couponCodeId", couponCodeId);
		int res = qry.executeUpdate();

		return res;
	}

	@Override
	public CouponCode isCouponCodeExist(int couponCodeId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "FROM CouponCode code where code.couponCodeId= :couponCodeId";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("couponCodeId", couponCodeId);
		CouponCode couponCode = (CouponCode) query.uniqueResult();
		return couponCode;
	}

}
