package com.transporter.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.transporter.dao.VehicleDetailsDao;
import com.transporter.model.VehicleDetails;

/**
 * @author Devappa.Arali
 *
 */

@Repository
public class VehicleDetailsDaoImpl extends GenericDaoImpl implements VehicleDetailsDao {

	@Override
	public VehicleDetails isVehicleExists(String vehicleNum) {
		VehicleDetails vehicleDetails = null;
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "From VehicleDetails vd WHERE vd.vehicleNum= :vehicleNum";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("vehicleNum", vehicleNum);
		vehicleDetails = (VehicleDetails) query.uniqueResult();
		return vehicleDetails;
	}

}
