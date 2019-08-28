package com.transporter.service;

import java.util.List;

import com.transporter.model.DisplayVehicle;
import com.transporter.vo.DisplayVehicleVo;

public interface DisplayVehicleService {

	List<DisplayVehicle> getAllDisplayVehicle();

	DisplayVehicle updateDisplayVehicle(DisplayVehicleVo displayVehicleVo);

	DisplayVehicle addDisplayVehicle(DisplayVehicleVo displayVehicleVo);

	DisplayVehicle deleteDisplayVehicle(DisplayVehicle displayVehicle);

}
