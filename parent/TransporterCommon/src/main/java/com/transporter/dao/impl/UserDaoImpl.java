package com.transporter.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.transporter.dao.UserDao;
import com.transporter.model.User;
import com.transporter.vo.UserVo;

@Repository
public class UserDaoImpl extends GenericDaoImpl implements UserDao {

	@Override
	public User isUserExists(String mobileNumber) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "FROM User usr where usr.mobileNumber= :mobileNumber";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("mobileNumber", mobileNumber);
		User user = (User) query.uniqueResult();
		return user;
	}

	@Override
	public User login(UserVo userVo) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("mobileNumber", userVo.getMobileNumber()));
		criteria.add(Restrictions.eq("password", userVo.getPassword()));
		User User = (User) criteria.uniqueResult();
		return User;
	}

	@Override
	public int generateOtp(String mobile, String otp) {
		Session session = sessionFactory.getCurrentSession();
		int rows = 0;
		String sqlQuery = "Update User usr set usr.loginOtp= :otp where usr.userName= :mobileNumber";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("mobileNumber", mobile);
		query.setParameter("otp", otp);
		rows = query.executeUpdate();
		return rows;
	}

	@Override
	public User validateOtp(String mobile, String otp) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "From User usr where usr.userName= :mobileNumber and usr.loginOtp= :otp";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("mobileNumber", mobile);
		query.setParameter("otp", otp);
		return (User) query.uniqueResult();
		
	}

}
