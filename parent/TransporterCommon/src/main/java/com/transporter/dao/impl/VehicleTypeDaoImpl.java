package com.transporter.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.dao.CancelReasonDao;
import com.transporter.dao.VehcileTypeDao;
import com.transporter.model.CancelReasons;
import com.transporter.model.VehicleType;
import com.transporter.model.User;
import com.transporter.vo.VehicleTypeVo;

@Repository
@Transactional
public class VehicleTypeDaoImpl extends GenericDaoImpl implements VehcileTypeDao {

	@Override
	public List<VehicleTypeVo> getAllDisplayVehicle() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "FROM DisplayVehicle displayVehicle";
		Query query = session.createQuery(sqlQuery);
		List<VehicleTypeVo> displayVehicleList = query.list();
		return displayVehicleList;
	}

	@Override
	public VehicleType deleteDisplayVehicle(VehicleType displayVehicle) {
		return null;
	}

}
