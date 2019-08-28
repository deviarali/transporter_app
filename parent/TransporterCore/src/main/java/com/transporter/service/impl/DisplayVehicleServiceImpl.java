package com.transporter.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transporter.dao.CancelReasonDao;
import com.transporter.dao.DisplayVehcileDao;
import com.transporter.model.DisplayVehicle;
import com.transporter.service.DisplayVehicleService;
import com.transporter.vo.DisplayVehicleVo;

@Service
public class DisplayVehicleServiceImpl implements DisplayVehicleService {

	@Autowired
	private DisplayVehcileDao displayVehicleDao;

	@Override
	public List<DisplayVehicle> getAllDisplayVehicle() {
		List<DisplayVehicle> displayVehicleList = displayVehicleDao.getAllDisplayVehicle();
		return displayVehicleList;
	}

	@Override
	@Transactional
	public DisplayVehicle updateDisplayVehicle(DisplayVehicleVo displayVehicleVo) {
		DisplayVehicle displayVehicle = null;
		if (displayVehicleVo != null) {
			displayVehicle = new DisplayVehicle();
			displayVehicle.setId(displayVehicleVo.getId());
			displayVehicle.setCapacity(displayVehicleVo.getCapacity());
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
	public DisplayVehicle addDisplayVehicle(DisplayVehicleVo displayVehicleVo) {
		DisplayVehicle displayVehicle = null;
		if (displayVehicleVo != null) {
			displayVehicle = new DisplayVehicle();
			displayVehicle.setCapacity(displayVehicleVo.getCapacity());
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
	public DisplayVehicle deleteDisplayVehicle(DisplayVehicle displayVehicle) {
		// TODO Auto-generated method stub
		return null;
	}

}
