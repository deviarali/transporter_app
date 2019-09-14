package com.transporter.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.constants.WebConstants;
import com.transporter.dao.VehicleDetailsDao;
import com.transporter.exceptions.BusinessException;
import com.transporter.exceptions.ErrorCodes;
import com.transporter.model.DriverDetails;
import com.transporter.model.VehicleDetails;
import com.transporter.model.VehicleType;
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
		String response = null;
		VehicleDetails details = vehicleDetailsDao.isVehicleExists(vehicleDetailsVo.getVehicleNum());
		if (null != details) {
			throw new BusinessException(ErrorCodes.VEHICLEEXISTS.name(), ErrorCodes.VEHICLEEXISTS.value());
		}
		VehicleDetails vehicleDetails = new VehicleDetails();
		vehicleDetails.setCreatedBy(Integer.parseInt(vehicleDetailsVo.getCreatedBy()));
		vehicleDetails.setCreatedOn(new Date());
		DriverDetailsVo driverDetailsVo = vehicleDetailsVo.getDriverDetails();
		DriverDetails driverDetails = new DriverDetails();
		driverDetails.setId(driverDetailsVo.getId());
		vehicleDetails.setDriverDetails(driverDetails);
		vehicleDetails.setVehicleColor(vehicleDetailsVo.getVehicleColor());
		vehicleDetails.setVehicleModel(vehicleDetailsVo.getVehicleModel());
		vehicleDetails.setVehicleNum(vehicleDetailsVo.getVehicleNum());
		VehicleType vehicleType = new VehicleType();
		vehicleType.setId(vehicleDetailsVo.getVehicleTypeVo().getId());
		vehicleDetails.setVehicleType(vehicleType);
		 vehicleDetailsDao.save(vehicleDetails);
		if(vehicleDetails.getId()>0)
		{
			return response = WebConstants.SUCCESS;
		}
		return response;
	}

	@Override
	@Transactional
	public VehicleDetailsVo updateVehicleDetails(VehicleDetailsVo vehicleDetailsVo) {
		System.out.println(vehicleDetailsVo.getId());
		VehicleDetails vehicleDetailsExist = isVehilceExistById(vehicleDetailsVo.getId());
		if (vehicleDetailsExist == null) {
			throw new BusinessException(ErrorCodes.VEHICLENOTFOUND.name(), ErrorCodes.VEHICLENOTFOUND.value());
		} else {
			vehicleDetailsExist.setId(vehicleDetailsVo.getId());
			vehicleDetailsExist.setVehicleColor(vehicleDetailsVo.getVehicleColor());
			vehicleDetailsExist.setVehicleModel(vehicleDetailsVo.getVehicleModel());
			VehicleType vehicleType = new VehicleType();
			vehicleType.setId(vehicleDetailsVo.getVehicleTypeVo().getId());
			vehicleDetailsExist.setVehicleType(vehicleType);
			vehicleDetailsExist.setVehicleNum(vehicleDetailsExist.getVehicleNum());
			try {
				vehicleDetailsDao.saveOrUpdate(vehicleDetailsExist);
			} catch (Exception e) {
				throw new BusinessException(ErrorCodes.VSAVE.name(), ErrorCodes.VSAVE.value());
			}

		}
		return vehicleDetailsVo;

	}

	@Override
	public VehicleDetails isVehilceExistById(int vehicleId) {
		return vehicleDetailsDao.isVehicleExistById(vehicleId);
	}

	@Override
	public VehicleDetails getVehicleByDriverId(int driverId) {
		VehicleDetails vehicleDetails = vehicleDetailsDao.getVehicleByDriverId(driverId);
		return vehicleDetails;
	}

}
