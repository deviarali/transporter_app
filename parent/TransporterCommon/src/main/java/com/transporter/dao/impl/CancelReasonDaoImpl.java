package com.transporter.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.dao.CancelReasonDao;
import com.transporter.model.CancelReasons;
import com.transporter.model.User;

@Repository
@Transactional
public class CancelReasonDaoImpl extends GenericDaoImpl implements CancelReasonDao {

	@Override
	public List<CancelReasons> getCancelReason() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "FROM CancelReasons cancelReason";
		Query query = session.createQuery(sqlQuery);
		List<CancelReasons> reasons = query.list();
		return reasons;
	}

	@Override
	public void addCancelReason(CancelReasons reason) {
		saveOrUpdate(reason);
	}
	
	
	@Override
	public int deleteCancelReason(int id) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "delete from CancelReasons reason where reason.id=:id";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("id", id);
		int res = query.executeUpdate();
		return res;	
	}

}
