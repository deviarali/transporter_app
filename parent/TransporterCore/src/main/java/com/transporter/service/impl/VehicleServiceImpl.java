package com.transporter.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.dao.VehicleDetailsDao;
import com.transporter.exceptions.BusinessException;
import com.transporter.exceptions.ErrorCodes;
import com.transporter.model.DriverDetails;
import com.transporter.model.VehicleDetails;
import com.transporter.service.VehicleService;
import com.transporter.vo.DriverDetailsVo;
import com.transporter.vo.VehicleDetailsVo;

/**
 * @author Devappa.Arali
 *
 */

@Service
public class VehicleServiceImpl implements VehicleService {

	@Autowired
	private VehicleDetailsDao vehicleDetailsDao;
	
	@Override
	@Transactional
	public String registerVehicle(VehicleDetailsVo vehicleDetailsVo) {
		
		VehicleDetails details = vehicleDetailsDao.isVehicleExists(vehicleDetailsVo.getVehicleNum());
		if(null != details) {
			new BusinessException(ErrorCodes.VEHICLEEXISTS.name(), ErrorCodes.VEHICLEEXISTS.value());
		}
		VehicleDetails vehicleDetails = new VehicleDetails();
		vehicleDetails.setCreatedBy(vehicleDetailsVo.getCreatedBy());
		vehicleDetails.setCreatedOn(new Date());
		DriverDetailsVo driverDetailsVo = vehicleDetailsVo.getDriverDetails();
		DriverDetails driverDetails = new DriverDetails();
		driverDetails.setId(driverDetailsVo.getId());
		vehicleDetails.setDriverDetails(driverDetails);
		vehicleDetails.setVehicleColor(vehicleDetailsVo.getVehicleColor());
		vehicleDetails.setVehicleModel(vehicleDetailsVo.getVehicleModel());
		vehicleDetails.setVehicleNum(vehicleDetailsVo.getVehicleNum());
		vehicleDetails.setVehicleType(vehicleDetailsVo.getVehicleType());
		vehicleDetailsDao.save(vehicleDetails);
		return null;
	}
	
}
