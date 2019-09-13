package com.transporter.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.transporter.dao.DriverDao;
import com.transporter.model.DriverDetails;
import com.transporter.model.TripDetails;

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

	@Override
	public int updateDriverDocuments(int userId, String generateFilePathAndStoreForAdhar,
			String generateFilePathAndStoreForDl) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "Update DriverDetails dd set dd.adharcardPictureUrl = '" + generateFilePathAndStoreForAdhar
				+ "' , dd.drivingLicencePictureUrl = '" + generateFilePathAndStoreForDl + "' where dd.user = " + userId;
		Query query = session.createQuery(sqlQuery);
		return query.executeUpdate();
	}

	@Override
	public DriverDetails findById(int driverId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "FROM DriverDetails dd where dd.id= :driverId";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("driverId", driverId);
		return (DriverDetails) query.uniqueResult();
	}
	
	@Override
	public List<DriverDetails> checkVehicleAvailability(String lattitude, String longitude, double distance ) {
		List<DriverDetails> driverDetails = null;
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select dd, ( 3959 * ACOS( COS( RADIANS(:lattitude) ) * COS( RADIANS( dd.current_lattitude ) ) \r\n" + 
				"    * COS( RADIANS( dd.current_longitude ) - RADIANS(:longitude) ) + SIN( RADIANS(:lattitude) ) * SIN(RADIANS(dd.current_lattitude)) ) ) AS distance \r\n" + 
				"FROM driverdetails dd where dd.on_road=1\r\n" + 
				"HAVING distance < :distance\r\n" + 
				"ORDER BY distance";
	
		SQLQuery sqlQuery2 = session.createSQLQuery(sqlQuery);
		sqlQuery2.setDouble("lattitude", Double.valueOf(lattitude));
		sqlQuery2.setDouble("longitude", Double.valueOf(longitude));
		sqlQuery2.setDouble("distance", distance);	
		List<Object[]> empData = sqlQuery2.list();
		for(Object[] obj : empData) {
			DriverDetails details = new DriverDetails();
			details.setId(Integer.valueOf(obj[0].toString()));
			details.setDriverName(obj[1].toString());
			driverDetails.add(details);
		}
		System.out.println("list : "+driverDetails);
		return driverDetails;
	}
}
