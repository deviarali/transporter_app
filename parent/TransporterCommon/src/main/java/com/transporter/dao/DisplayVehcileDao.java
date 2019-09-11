package com.transporter.dao;

import java.util.List;

import com.transporter.dao.impl.GenericDaoImpl;
import com.transporter.model.VehicleType;
import com.transporter.vo.VehicleTypeVo;

public interface DisplayVehcileDao extends GenericDao {

	List<VehicleTypeVo> getAllDisplayVehicle();

	VehicleType deleteDisplayVehicle(VehicleType displayVehicle);

}
