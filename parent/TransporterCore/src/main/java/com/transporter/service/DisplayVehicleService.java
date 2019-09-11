package com.transporter.service;

import java.util.List;

import com.transporter.model.VehicleType;
import com.transporter.vo.VehicleTypeVo;

public interface DisplayVehicleService {

	List<VehicleTypeVo> getAllDisplayVehicle();

	VehicleTypeVo updateDisplayVehicle(VehicleTypeVo displayVehicleVo);

	VehicleTypeVo addDisplayVehicle(VehicleTypeVo displayVehicleVo);

	VehicleType deleteDisplayVehicle(VehicleType displayVehicle);

}
