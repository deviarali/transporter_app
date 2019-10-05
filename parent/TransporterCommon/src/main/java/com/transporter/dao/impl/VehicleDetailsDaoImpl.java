package com.transporter.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.transporter.dao.VehicleDetailsDao;
import com.transporter.model.VehicleDetails;
import com.transporter.vo.FetchSelectedVehiclesRequest;

/**
 * @author Devappa.Arali
 *
 */

@Repository
public class VehicleDetailsDaoImpl extends GenericDaoImpl implements VehicleDetailsDao {

	@Autowired
    EntityManager em;
	
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

	/*
	 * @Override public List<VehicleDetails>
	 * fetchSelectedVehicles(FetchSelectedVehiclesRequest
	 * fetchSelectedVehiclesRequest) { List<VehicleDetails> vehicleDetailsLIst = new
	 * ArrayList<VehicleDetails>(); Session session =
	 * sessionFactory.getCurrentSession(); String sqlQuery =
	 * "select *, ( 3959 * ACOS( COS( RADIANS(:lattitude) ) * COS( RADIANS( vd.current_lattitude ) ) \r\n"
	 * +
	 * "    * COS( RADIANS( vd.current_longitude ) - RADIANS(:longitude) ) + SIN( RADIANS(:lattitude) ) * SIN(RADIANS(vd.current_lattitude)) ) ) AS distance \r\n"
	 * +
	 * "FROM vehicledetails vd INNER JOIN driverdetails dr ON (vd.driver_id = dr.id AND dr.driver_verification_status = 'completed') WHERE vd.vehicle_type = :vehicleType AND  vd.vehicle_verification_status=1\r\n"
	 * + "HAVING distance < :distance \r\n" + "ORDER BY distance";
	 * 
	 * SQLQuery sqlQuery2 = session.createSQLQuery(sqlQuery);
	 * sqlQuery2.setDouble("lattitude",
	 * fetchSelectedVehiclesRequest.getLattitude());
	 * sqlQuery2.setDouble("longitude",
	 * fetchSelectedVehiclesRequest.getLongitude()); sqlQuery2.setDouble("distance",
	 * fetchSelectedVehiclesRequest.getSurroudingDistance());
	 * sqlQuery2.setInteger("vehicleType",
	 * fetchSelectedVehiclesRequest.getVehicleType()); List<Object[]> empData =
	 * sqlQuery2.list(); for(Object[] obj : empData) { VehicleDetails details = new
	 * VehicleDetails(); details.setId(Integer.valueOf(obj[0].toString()));
	 * vehicleDetailsLIst.add(details); }
	 * System.out.println("list : "+vehicleDetailsLIst); return vehicleDetailsLIst;
	 * }
	 */
	
	@Override
	public List<VehicleDetails> fetchSelectedVehicles(FetchSelectedVehiclesRequest fetchSelectedVehiclesRequest) {
		List<VehicleDetails> vehicleDetailsLIst = new ArrayList<VehicleDetails>();

		javax.persistence.Query sqlQuery2 = em.createNativeQuery(
				"select vd.id as id, ( 3959 * ACOS( COS( RADIANS(:lattitude) ) * COS( RADIANS( vd.current_lattitude ) ) \r\n"
						+ "  * COS( RADIANS( vd.current_longitude ) - RADIANS(:longitude) ) + SIN( RADIANS(:lattitude) ) * SIN(RADIANS(vd.current_lattitude)) ) ) AS distance \r\n"
						+ "FROM vehicledetails vd INNER JOIN driverdetails dr ON (vd.driver_id = dr.id AND dr.driver_verification_status = 'completed') WHERE vd.vehicle_type = :vehicleType AND  vd.vehicle_verification_status=1\r\n"
						+ "HAVING distance < :distance \r\n" + "ORDER BY distance");

		sqlQuery2.setParameter("lattitude", fetchSelectedVehiclesRequest.getLattitude());
		sqlQuery2.setParameter("longitude", fetchSelectedVehiclesRequest.getLongitude());
		sqlQuery2.setParameter("distance", fetchSelectedVehiclesRequest.getSurroundingDistance());
		sqlQuery2.setParameter("vehicleType", fetchSelectedVehiclesRequest.getVehicleType());
		List<Object[]> resultList = sqlQuery2.getResultList();

		for (Object[] obj : resultList) {
			VehicleDetails details = new VehicleDetails();
			details.setId((int) obj[0]);
			vehicleDetailsLIst.add(details);
		}
		return vehicleDetailsLIst;
	}
}
