package com.transporter.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.transporter.model.VehicleType;
import com.transporter.vo.VehicleTypeVo;
import com.transporter.vo.VehiclesByOrderRequest;
import com.transporter.vo.VehiclesByOrderResponse;

public interface VehicleTypeService {

	List<VehicleTypeVo> getAllVehicleTypes();

	VehicleTypeVo updateDisplayVehicle(VehicleTypeVo displayVehicleVo);

	VehicleType deleteDisplayVehicle(VehicleType displayVehicle);

	String addVehicleType(VehicleTypeVo vehicleTypeVo);

	List<VehiclesByOrderResponse> fetchVehiclesByOrder(VehiclesByOrderRequest vehiclesByOrderRequest);

}
