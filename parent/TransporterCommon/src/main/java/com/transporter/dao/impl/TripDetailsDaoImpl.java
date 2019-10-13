package com.transporter.dao.impl;

import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.transporter.dao.TripDetailsDao;
import com.transporter.model.Coupon;
import com.transporter.utils.CalendarUtils;

/**
 * @author SHARAN A
 */
@Repository
@Transactional
public class TripDetailsDaoImpl extends GenericDaoImpl implements TripDetailsDao {

	@Override
	public Integer getTotalDayRideNumber(Integer userId, Date currentDate) {
		Session session = sessionFactory.getCurrentSession();

		StringBuilder sqlQuery = new StringBuilder(" select count(td.id)");
		sqlQuery.append("FROM TripDetails td ");
		sqlQuery.append(" inner join td.deliveryStatus ds ");
		sqlQuery.append(" inner join td.customerDetails cd ");

		sqlQuery.append("where ds.deliverystatus = 'COMPLETED' ");
		sqlQuery.append("and cd.user.id = :userId ");
		sqlQuery.append("and DAY(td.tripStarttime) = :currentDay ");

		Query query = session.createQuery(sqlQuery.toString());

		query.setParameter("userId", userId);
		query.setParameter("currentDay", CalendarUtils.getCurrentCalendar().get(Calendar.DAY_OF_MONTH));

		return ((Long) firstResult(query.list())).intValue();
	}

	@Override
	public Integer getTotalRideNumber(Integer userId) {
		Session session = sessionFactory.getCurrentSession();

		StringBuilder sqlQuery = new StringBuilder(" select count(td.id)");
		sqlQuery.append("FROM TripDetails td ");
		sqlQuery.append(" inner join td.deliveryStatus ds ");
		sqlQuery.append(" inner join td.customerDetails cd ");

		sqlQuery.append("where ds.deliverystatus = 'COMPLETED' ");
		sqlQuery.append("and cd.user.id = :userId ");

		Query query = session.createQuery(sqlQuery.toString());

		query.setParameter("userId", userId);

		return ((Long) firstResult(query.list())).intValue();
	}

}
