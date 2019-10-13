package com.transporter.dao.impl;

import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.transporter.dao.CouponDao;
import com.transporter.model.Coupon;
import com.transporter.utils.CalendarUtils;

/**
 * @author SHARAN A
 */
@Repository
@Transactional
public class CouponDaoImpl extends GenericDaoImpl implements CouponDao {

	@Override
	public Coupon saveCoupon(Coupon coupon) {
		save(coupon);
		return coupon;
	}

	@Override
	public Coupon updateCoupon(Coupon coupon) {
		saveOrUpdate(coupon);
		return coupon;
	}

	@Override
	public List<Coupon> getAllCoupon() {
		return loadAll(Coupon.class);
	}

	@Override
	public List<Coupon> getAllActiveCoupon(boolean isActive) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "FROM Coupon code where code.isActive= :isActive";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("isActive", isActive);
		List<Coupon> couponLsit = (List<Coupon>) query.list();
		return couponLsit;
	}

	@Override
	public int deleteCoupon(Integer couponId) {
		Session session = sessionFactory.getCurrentSession();
		Query qry = session.createQuery("delete from Coupon code where code.couponId=:couponId");
		qry.setParameter("couponId", couponId);
		int res = qry.executeUpdate();

		return res;
	}

	@Override
	public Coupon isCouponExist(Integer couponId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "FROM Coupon code where code.id= :couponId";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("couponId", couponId);
		Coupon coupon = (Coupon) query.uniqueResult();
		return coupon;
	}

	@Override
	public boolean isCouponExist(Integer id, Calendar startDate, Calendar endDate) {
		Session session = sessionFactory.getCurrentSession();
		
		StringBuilder builder = new StringBuilder("select coupon.id ");
		builder.append(" from Coupon coupon ");
		builder.append(" where coupon.isActive = true");
		if(id  != null) {
			builder.append(" and coupon.id != :couponId");
		}
		builder.append(" and ((coupon.startDate <= :startDate and :startDate <= coupon.endDate)");
		builder.append(" 	or (coupon.startDate <= :endDate and :endDate <= coupon.endDate)) ");
		
		
		Query query = session.createQuery(builder.toString());
		
		if(id  != null) {
			query.setParameter("couponId", id);
		}
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
				
		List couponIds = query.list();
		
		if(!CollectionUtils.isEmpty(couponIds)) {
			return true;
		}
		return false;
	}

	@Override
	public Coupon getFirstRideCoupon(String couponCode) {
		Session session = sessionFactory.getCurrentSession();
		
		Calendar currentDate = CalendarUtils.getCurrentCalendar();
		
		String sqlQuery = "FROM Coupon code "
				+ "where code.isActive= true "
				+ "and code.firstRide = true "
				+ "and code.couponCode = :couponCode "
				+ "and (:currentDate between code.startDate and code.endDate) ";
		
		Query query = session.createQuery(sqlQuery);

		query.setParameter("currentDate", currentDate);
		query.setParameter("couponCode", couponCode);
		
		return (Coupon) firstResult(query.list());
	}
	
	public Coupon getCouponForUserAndCode(String couponCode) {
		Session session = sessionFactory.getCurrentSession();
		
		Calendar currentDate = CalendarUtils.getCurrentCalendar();
		
		StringBuilder sqlQuery = new StringBuilder("");
		sqlQuery.append("FROM Coupon code ");
		sqlQuery.append("where code.isActive= true ");
//		sqlQuery.append("and code.firstRide = false ");
		sqlQuery.append("and code.couponCode = :couponCode ");
		sqlQuery.append("and (:currentDate between code.startDate and code.endDate) ");
		
		Query query = session.createQuery(sqlQuery.toString());

		query.setParameter("currentDate", currentDate);
		query.setParameter("couponCode", couponCode);
		
		Coupon coupon = (Coupon) firstResult(query.list());;
		if(coupon != null) {
			Hibernate.initialize(coupon.getApplyUsers());
			Hibernate.initialize(coupon.getExludeUsers());
		}
		return coupon;
	}
	
	@Override
	public List<Coupon> findActiveCoupons() {
		Session session = sessionFactory.getCurrentSession();
		
		Calendar currentDate = CalendarUtils.getCurrentCalendar();
		
		StringBuilder sqlQuery = new StringBuilder("");
		sqlQuery.append("FROM Coupon code ");
		sqlQuery.append("where code.isActive= true ");
		sqlQuery.append("and code.firstRide = false ");
		sqlQuery.append("and (:currentDate between code.startDate and code.endDate) ");
		
		Query query = session.createQuery(sqlQuery.toString());

		query.setParameter("currentDate", currentDate);
		
		return query.list();
	}

}
