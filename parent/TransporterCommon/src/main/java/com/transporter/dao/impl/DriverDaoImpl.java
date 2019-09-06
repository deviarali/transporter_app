package com.transporter.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.transporter.dao.DriverDao;

/**
 * @author Devappa.Arali
 *
 */

@Repository
public class DriverDaoImpl extends GenericDaoImpl implements DriverDao {

	@Override
	public int updateLattitudeAndLongitude(int id, String lattitude, String longitude) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "update DriverDetails dd set dd.currentLattitude= :lattitude, dd.currentLongitude= :longitude where dd.id= :id";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("id", id);
		query.setParameter("lattitude", lattitude);
		query.setParameter("longitude", longitude);
		return query.executeUpdate();
	}

}
