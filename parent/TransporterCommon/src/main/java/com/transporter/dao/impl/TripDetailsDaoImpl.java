package com.transporter.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.transporter.dao.TripDetailsDao;
import com.transporter.enums.DeliveryStatusEnum;
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
			builder.append(" customerFeedback = :feedback,");
			builder.append(" deliveryStatus.id = :status");
		} else if (userType.equalsIgnoreCase("driver")) {
			builder.append(" driverRatings = :ratings,");
			builder.append(" driverFeedback = :feedback");
		} else {
			builder.append(" ratings = :ratings,");
			builder.append(" customerFeedback = :feedback,");
			builder.append(" deliveryStatus.id = :status");
		}
		builder.append(" WHERE id = :tripId");
		Query query = session.createQuery(builder.toString());
		query.setParameter("ratings", ratings);
		query.setParameter("tripId", tripId);
		query.setParameter("feedback", feedback);
		if(userType.equalsIgnoreCase("customer")) {
			query.setParameter("status", DeliveryStatusEnum.TRIPCOMPLETED.getId());
		}
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

	@Override
	public List<TripDetails> getCustomerLastTripDetails(int customerId) {
		Session session = sessionFactory.getCurrentSession();
		/*String sqlQuery = "FROM TripDetails td WHERE td.customerDetails.id = :customerId AND td.deliveryStatus.id != 5 "
				+ "AND td.deliveryStatus.id != 4 AND td.deliveryStatus.id != 3 ORDER BY td.id DESC";*/
		String sqlQuery = "FROM TripDetails td WHERE td.customerDetails.id = :customerId AND td.deliveryStatus.id IN(1, 2, 3, 4, 5, 6) ORDER BY td.id DESC ";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("customerId", customerId);
		List<TripDetails> tripDetails = (List<TripDetails>) query.list();
		return tripDetails;
	}
	
	@Override
	public List<TripDetails> getTripDetailsByCustomer(int customerId,int status){
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "FROM TripDetails td WHERE td.customerDetails.id = :customerId AND td.deliveryStatus.id = :status";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("customerId", customerId);
		query.setParameter("status", status);
		  List<TripDetails> tripDetails = (List<TripDetails>) query.list();
		  return tripDetails;
	}
	

	@Override
	public int updateTripStatusWithTime(int tripId, int status) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder builder = new StringBuilder("UPDATE TripDetails td SET td.deliveryStatus.id = :status, ");
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(status == DeliveryStatusEnum.TRIPACCEPTED.getId()) {
			parameters.put("status", DeliveryStatusEnum.TRIPACCEPTED.getId());
			builder.append("td.tripAccetRejectTime = :time ");
		} else if(status == DeliveryStatusEnum.TRIPREJECTED.getId()) {
			parameters.put("status", DeliveryStatusEnum.TRIPREJECTED.getId());
			builder.append("td.tripAccetRejectTime = :time ");
		} else if(status == DeliveryStatusEnum.DRIVERREACHEDPICKUPLOCATION.getId()) {
			parameters.put("status", DeliveryStatusEnum.DRIVERREACHEDPICKUPLOCATION.getId());
			builder.append("td.pickupLocationTime = :time ");
		} else if(status == DeliveryStatusEnum.ONGOING.getId()) {
			parameters.put("status", DeliveryStatusEnum.ONGOING.getId());
			builder.append("td.tripStarttime = :time ");
		} else if(status == DeliveryStatusEnum.DRIVERREACHEDDESTINATIONLOCATION.getId()) {
			parameters.put("status", DeliveryStatusEnum.DRIVERREACHEDDESTINATIONLOCATION.getId());
			builder.append("td.reachedLocationTime = :time ");
		} else if(status == DeliveryStatusEnum.TRIPENDED.getId()) {
			parameters.put("status", DeliveryStatusEnum.TRIPENDED.getId());
			builder.append("td.tripEndtime = :time ");
		} else if(status == DeliveryStatusEnum.CANCELLEDBYCUSTOMER.getId()) {
			parameters.put("status", DeliveryStatusEnum.CANCELLEDBYCUSTOMER.getId());
			builder.append("td.tripCancelledTime = :time ");
		} else if(status == DeliveryStatusEnum.CANCELLEDBYDRIVER.getId()) {
			parameters.put("status", DeliveryStatusEnum.CANCELLEDBYDRIVER.getId());
			builder.append("td.tripCancelledTime = :time ");
		}
		builder.append(" WHERE td.id = :tripId");
		parameters.put("tripId", tripId);
		parameters.put("time", new Date());
		String sqlQuery = builder.toString();
		Query query = session.createQuery(sqlQuery);
		Set<String> parameterSet = parameters.keySet();
        for (String string : parameterSet) {
            query.setParameter(string, parameters.get(string));
        }
		return query.executeUpdate();
	}

	@Override
	public List<TripDetails> getPassangerHistoryByStatus(int id, int tripStatus, String requestorType) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder builder = new StringBuilder("FROM TripDetails td ");
		if(requestorType.equalsIgnoreCase("customer")) {
			if(tripStatus == 8) {
				builder.append("WHERE td.customerDetails.id = :id AND (td.deliveryStatus.id = 6 OR td.deliveryStatus.id = 8) ");
			} else if(tripStatus == 4) {
				builder.append("WHERE td.customerDetails.id = :id AND (td.deliveryStatus.id = 2 OR td.deliveryStatus.id = 3 OR td.deliveryStatus.id = 4 OR td.deliveryStatus.id = 5) ");
			}
		} else if(requestorType.equalsIgnoreCase("driver")) {
			if(tripStatus == 8) {
				builder.append("WHERE td.driverDetails.id = :id AND (td.deliveryStatus.id = 6 OR td.deliveryStatus.id = 8) ");
			} else if(tripStatus == 4) {
				builder.append("WHERE td.driverDetails.id = :id AND (td.deliveryStatus.id = 2 OR td.deliveryStatus.id = 3 OR td.deliveryStatus.id = 4 OR td.deliveryStatus.id = 5) ");
			}
		}
		String sqlQuery = builder.toString();
		Query query = session.createQuery(sqlQuery);
		
		query.setParameter("id", id);
		List<TripDetails> tripDetails = (List<TripDetails>) query.list();
		return tripDetails;
	}

}
