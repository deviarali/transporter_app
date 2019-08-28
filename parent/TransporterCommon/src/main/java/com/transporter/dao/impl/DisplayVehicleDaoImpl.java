package com.transporter.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.dao.CancelReasonDao;
import com.transporter.dao.DisplayVehcileDao;
import com.transporter.model.CancelReasons;
import com.transporter.model.DisplayVehicle;
import com.transporter.model.User;
import com.transporter.vo.DisplayVehicleVo;

@Repository
@Transactional
public class DisplayVehicleDaoImpl extends GenericDaoImpl implements DisplayVehcileDao {

	@Override
	public List<DisplayVehicle> getAllDisplayVehicle() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "FROM DisplayVehicle displayVehicle";
		Query query = session.createQuery(sqlQuery);
		List<DisplayVehicle> displayVehicleList = query.list();
		return displayVehicleList;
	}

	@Override
	public DisplayVehicle updateDisplayVehicle(DisplayVehicle displayVehicle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DisplayVehicle deleteDisplayVehicle(DisplayVehicle displayVehicle) {
		return null;
	}

}
