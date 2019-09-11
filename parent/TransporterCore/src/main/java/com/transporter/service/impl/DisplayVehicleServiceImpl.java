package com.transporter.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transporter.dao.CancelReasonDao;
import com.transporter.dao.DisplayVehcileDao;
import com.transporter.model.VehicleType;
import com.transporter.service.DisplayVehicleService;
import com.transporter.vo.VehicleTypeVo;

@Service
public class DisplayVehicleServiceImpl implements DisplayVehicleService {

	@Autowired
	private DisplayVehcileDao displayVehicleDao;

	@Override
	public List<VehicleTypeVo> getAllDisplayVehicle() {
		List<VehicleTypeVo> displayVehicleList = displayVehicleDao.getAllDisplayVehicle();
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
			displayVehicleDao.saveOrUpdate(displayVehicle);
		}

		if (displayVehicle != null) {
			return displayVehicle;
		} else
			return null;
	}

	@Override
	@Transactional
	public VehicleTypeVo addDisplayVehicle(VehicleTypeVo displayVehicleVo) {
		VehicleTypeVo displayVehicle = null;
		if (displayVehicleVo != null) {
			displayVehicle = new VehicleTypeVo();
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
			displayVehicleDao.save(displayVehicle);
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
