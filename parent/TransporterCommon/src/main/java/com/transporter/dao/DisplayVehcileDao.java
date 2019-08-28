package com.transporter.dao;

import java.util.List;

import com.transporter.dao.impl.GenericDaoImpl;
import com.transporter.model.DisplayVehicle;
import com.transporter.vo.DisplayVehicleVo;

public interface DisplayVehcileDao extends GenericDao {

	List<DisplayVehicle> getAllDisplayVehicle();

	DisplayVehicle updateDisplayVehicle(DisplayVehicle displayVehicle);

	DisplayVehicle deleteDisplayVehicle(DisplayVehicle displayVehicle);

}
