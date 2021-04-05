package com.transporter.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.transporter.dao.TripDetailsDao;
import com.transporter.model.TripDetails;
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

	@Override
	public Integer getTotalDayAllRideNumber(Date startTime, Date endTime) {
		Session session = sessionFactory.getCurrentSession();

		StringBuilder sqlQuery = new StringBuilder(" select count(td.id)");
		sqlQuery.append(" FROM TripDetails td ");
		sqlQuery.append(" where td.deliveryStatus.id in ('2','5')");
		sqlQuery.append(" and td.tripStarttime between :startTime and :endTime ");

		Query query = session.createQuery(sqlQuery.toString());

		query.setParameter("startTime", startTime);
		query.setParameter("endTime", endTime);

		return ((Long) firstResult(query.list())).intValue();
	}

	@Override
	public Map<Integer, Long> getTopDriversForWeek(int limit, Date startTime, Date endTime) {
		Session session = sessionFactory.getCurrentSession();

		StringBuilder sqlQuery = new StringBuilder(" select count(*) as counts, td.driverDetails.id as driverId");
		sqlQuery.append(" FROM TripDetails td ");
		sqlQuery.append(" where td.deliveryStatus.id in ('5')");
		sqlQuery.append(" and td.tripStarttime between :startTime and :endTime ");
		sqlQuery.append(" GROUP BY td.driverDetails.id");
		sqlQuery.append(" ORDER BY counts DESC");
		Query query = session.createQuery(sqlQuery.toString());

		query.setParameter("startTime", startTime);
		query.setParameter("endTime", endTime);
		query.setMaxResults(limit);
		List list = query.list();

		Map<Integer, Long> tripCount = new HashMap<>();
		list.forEach(data -> {
			Object[] objects = (Object[]) data;
			tripCount.put((int) objects[1], (long) objects[0]);
		});

		return tripCount;
	}

	@Override
	public Map<Integer, Long> getTopCustomerForWeek(Integer limit, Date startTime, Date endTime) {
		Session session = sessionFactory.getCurrentSession();

		StringBuilder sqlQuery = new StringBuilder(" select count(*) as counts, td.customerDetails.id as customerId");
		sqlQuery.append(" FROM TripDetails td ");
		sqlQuery.append(" where td.deliveryStatus.id in ('5')");
		sqlQuery.append(" and td.tripStarttime between :startTime and :endTime ");
		sqlQuery.append(" GROUP BY td.customerDetails.id");
		sqlQuery.append(" ORDER BY counts DESC");
		Query query = session.createQuery(sqlQuery.toString());

		query.setParameter("startTime", startTime);
		query.setParameter("endTime", endTime);
		query.setMaxResults(limit);
		List list = query.list();

		Map<Integer, Long> tripCount = new HashMap<>();
		list.forEach(data -> {
			Object[] objects = (Object[]) data;
			tripCount.put((int) objects[1], (long) objects[0]);
		});

		return tripCount;
	}

	
	
	
	@Override
	public int saveTripRatings(int tripId, String ratings, String userType, String feedback) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder builder = new StringBuilder("UPDATE TripDetails SET");
		if(userType.equalsIgnoreCase("customer")) {
			builder.append(" ratings = :ratings,");
			builder.append(" customerFeedback = :feedback");
		} else if (userType.equalsIgnoreCase("driver")) {
			builder.append(" driverRatings = :ratings,");
			builder.append(" driverFeedback = :feedback");
		} else {
			builder.append(" ratings = :ratings,");
			builder.append(" customerFeedback = :feedback");
		}
		builder.append(" WHERE id = :tripId");
		Query query = session.createQuery(builder.toString());
		query.setParameter("ratings", ratings);
		query.setParameter("tripId", tripId);
		query.setParameter("feedback", feedback);
		return query.executeUpdate();
	}

	@Override
	public List<TripDetails> getDriversLastTripDetails(int driverId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "FROM TripDetails td WHERE td.driverDetails.id = :driverId ORDER BY td.id DESC";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("driverId", driverId);
		query.setMaxResults(1);
		List<TripDetails> tripDetails = (List<TripDetails>) query.list();
		return tripDetails;
	}

}
