package com.transporter.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.transporter.model.VehicleType;
import com.transporter.vo.VehicleTypeVo;

public interface VehicleTypeService {

	List<VehicleTypeVo> getAllDisplayVehicle();

	VehicleTypeVo updateDisplayVehicle(VehicleTypeVo displayVehicleVo);

	VehicleTypeVo addDisplayVehicle(VehicleTypeVo displayVehicleVo,MultipartFile multipartFile, MultipartFile multipartFile2);

	VehicleType deleteDisplayVehicle(VehicleType displayVehicle);

}
