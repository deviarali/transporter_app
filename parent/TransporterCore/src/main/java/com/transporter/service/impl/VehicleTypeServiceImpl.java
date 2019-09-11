package com.transporter.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.transporter.dao.CancelReasonDao;
import com.transporter.dao.VehcileTypeDao;
import com.transporter.model.VehicleType;
import com.transporter.service.VehicleTypeService;
import com.transporter.utility.TransporterUtility;
import com.transporter.vo.VehicleTypeVo;

@Service
public class VehicleTypeServiceImpl implements VehicleTypeService {

	@Autowired
	private VehcileTypeDao vehcileTypeDao;
	
	@Autowired
	TransporterUtility transporterUtility;

	@Override
	public List<VehicleTypeVo> getAllDisplayVehicle() {
		List<VehicleTypeVo> displayVehicleList = vehcileTypeDao.getAllDisplayVehicle();
		return displayVehicleList;
	}

	@Override
	@Transactional
	public VehicleTypeVo updateDisplayVehicle(VehicleTypeVo displayVehicleVo) {
		VehicleTypeVo displayVehicle = null;
		if (displayVehicleVo != null) {
			displayVehicle = new VehicleTypeVo();
			displayVehicle.setId(displayVehicleVo.getId());
			displayVehicle.setCapacity(displayVehicleVo.getCapacity());
			displayVehicle.setVehicleName(displayVehicleVo.getVehicleName());
			displayVehicle.setPrice(displayVehicleVo.getPrice());
			displayVehicle.setCreatedBy(displayVehicleVo.getCreatedBy());
			displayVehicle.setHeight(displayVehicleVo.getHeight());
			displayVehicle.setLength(displayVehicleVo.getLength());
			displayVehicle.setSelectedVehicleUrl(displayVehicleVo.getSelectedVehicleUrl());
			displayVehicle.setSize(displayVehicleVo.getSize());
			displayVehicle.setUnselectedVehicleUrl(displayVehicleVo.getUnselectedVehicleUrl());
			displayVehicle.setWidth(displayVehicleVo.getWidth());
			vehcileTypeDao.saveOrUpdate(displayVehicle);
		}

		if (displayVehicle != null) {
			return displayVehicle;
		} else
			return null;
	}

	@Override
	@Transactional
	public VehicleTypeVo addDisplayVehicle(VehicleTypeVo displayVehicleVo,MultipartFile multipartFile1,MultipartFile multipartFile2) {
		VehicleTypeVo displayVehicle = null;
		if (displayVehicleVo != null) {
			displayVehicle = new VehicleTypeVo();
			displayVehicle.setCapacity(displayVehicleVo.getCapacity());
			displayVehicle.setVehicleName(displayVehicleVo.getVehicleName());
			displayVehicle.setPrice(displayVehicleVo.getPrice());
			displayVehicle.setCreatedBy(displayVehicleVo.getCreatedBy());
			displayVehicle.setHeight(displayVehicleVo.getHeight());
			displayVehicle.setLength(displayVehicleVo.getLength());
		
			displayVehicle.setSelectedVehicleUrl(transporterUtility.generateFilePathAndStore(displayVehicle.getSelectedVehicle(),"vehicle"));
			displayVehicle.setSize(displayVehicleVo.getSize());
			displayVehicle.setUnselectedVehicleUrl(transporterUtility.generateFilePathAndStore(displayVehicle.getUnSelectedVehicle(), "vehicle"));
			displayVehicle.setWidth(displayVehicleVo.getWidth());
			vehcileTypeDao.save(displayVehicle);
		}
		if (displayVehicle != null) {
			return displayVehicle;
		} else
			return null;
	}

	@Override
	public VehicleType deleteDisplayVehicle(VehicleType displayVehicle) {
		// TODO Auto-generated method stub
		return null;
	}

}
