package com.transporter.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.transporter.dao.VehicleDetailsDao;
import com.transporter.model.DriverDetails;
import com.transporter.model.VehicleDetails;
import com.transporter.vo.FetchSelectedVehiclesRequest;

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

	@Override
	public VehicleDetails isVehicleExistById(int id) {
		VehicleDetails vehicleDetails = null;
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "From VehicleDetails vd WHERE vd.id= :id";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("id",id);
		vehicleDetails = (VehicleDetails) query.uniqueResult();
		return vehicleDetails;
	}

	@Override
	public VehicleDetails getVehicleByDriverId(int driverId) {
		VehicleDetails vehicleDetails = null;
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "From VehicleDetails vd WHERE vd.driverDetails.id= :driverId";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("driverId",driverId);
		vehicleDetails = (VehicleDetails) query.uniqueResult();
		return vehicleDetails;
	}

	@Override
	public int updateLattitudeAndLongitude(int id, String lattitude, String longitude) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "update VehicleDetails vd set vd.currentLattitude= :lattitude, vd.currentLongitude= :longitude where vd.driverDetails.id= :id";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("id", id);
		query.setParameter("lattitude", Double.valueOf(lattitude));
		query.setParameter("longitude", Double.valueOf(longitude));
		return query.executeUpdate();
	}

	@Override
	public List<VehicleDetails> fetchSelectedVehicles(FetchSelectedVehiclesRequest fetchSelectedVehiclesRequest) {
		List<VehicleDetails> vehicleDetailsLIst = new ArrayList<VehicleDetails>();
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select *, ( 3959 * ACOS( COS( RADIANS(:lattitude) ) * COS( RADIANS( vd.current_lattitude ) ) \r\n" + 
				"    * COS( RADIANS( vd.current_longitude ) - RADIANS(:longitude) ) + SIN( RADIANS(:lattitude) ) * SIN(RADIANS(vd.current_lattitude)) ) ) AS distance \r\n" + 
				"FROM vehicledetails vd WHERE vd.vehicle_type = :vehicleType AND  vd.vehicle_verification_status=1\r\n" + 
				"HAVING distance < :distance \r\n" + 
				"ORDER BY distance";
	
		SQLQuery sqlQuery2 = session.createSQLQuery(sqlQuery);
		sqlQuery2.setDouble("lattitude", fetchSelectedVehiclesRequest.getLattitude());
		sqlQuery2.setDouble("longitude", fetchSelectedVehiclesRequest.getLongitude());
		sqlQuery2.setDouble("distance", fetchSelectedVehiclesRequest.getSurroudingDistance());
		sqlQuery2.setInteger("vehicleType", fetchSelectedVehiclesRequest.getVehicleType());
		List<Object[]> empData = sqlQuery2.list();
		for(Object[] obj : empData) {
			VehicleDetails details = new VehicleDetails();
			details.setId(Integer.valueOf(obj[0].toString()));
			vehicleDetailsLIst.add(details);
		}
		System.out.println("list : "+vehicleDetailsLIst);
		return vehicleDetailsLIst;
	}
}
