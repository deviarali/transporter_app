package com.transporter.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.transporter.dao.VehicleTypeDao;
import com.transporter.model.VehicleType;
import com.transporter.vo.VehicleTypeVo;
import com.transporter.vo.VehiclesByOrderRequest;

@Repository
public class VehicleTypeDaoImpl extends GenericDaoImpl implements VehicleTypeDao {

	@Override
	public List<VehicleTypeVo> getAllVehicleTypes() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "FROM VehicleType vehicleType";
		Query query = session.createQuery(sqlQuery);
		List<VehicleTypeVo> displayVehicleList = query.list();
		return displayVehicleList;
	}

	@Override
	public VehicleType deleteDisplayVehicle(VehicleType displayVehicle) {
		return null;
	}

	@Override
	public List<VehicleType> fetchVehiclesByOrder(VehiclesByOrderRequest vehiclesByOrderRequest) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "FROM VehicleType vehicleType WHERE vehicleType.capacity>= :capacity";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("capacity", vehiclesByOrderRequest.getCapacity());
		List<VehicleType> vehicleTypeList = query.list();
		return vehicleTypeList;
	}

}
