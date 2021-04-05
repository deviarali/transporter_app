package com.transporter.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.dao.GoodsTypeDao;

@Repository
@Transactional
public class GoodsTypeDaoImpl extends GenericDaoImpl implements GoodsTypeDao {

	@Override
	public int updateGoodsStatus(int id, String status) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "UPDATE GoodsType gt SET gt.status = :status WHERE gt.id = :id";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("id", id);
		query.setParameter("status", status);
;		return query.executeUpdate();
	}

}
