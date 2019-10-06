package com.transporter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transporter.constants.WebConstants;
import com.transporter.dao.VehicleTypeDao;
import com.transporter.exceptions.BusinessException;
import com.transporter.exceptions.ErrorCodes;
import com.transporter.model.VehicleType;
import com.transporter.repo.VehicleTypeRepo;
import com.transporter.service.VehicleTypeService;
import com.transporter.utility.TransporterUtility;
import com.transporter.utils.Utils;
import com.transporter.vo.VehicleTypeVo;
import com.transporter.vo.VehiclesByOrderRequest;
import com.transporter.vo.VehiclesByOrderResponse;

@Service
public class VehicleTypeServiceImpl implements VehicleTypeService {

	@Autowired
	private VehicleTypeDao vehicleTypeDao;
	
	@Autowired
	TransporterUtility transporterUtility;
	
	@Autowired
	VehicleTypeRepo vehicleTypeRepo;

	@Override
	public List<VehicleTypeVo> getAllVehicleTypes() {
		List<VehicleTypeVo> displayVehicleList = vehicleTypeDao.getAllVehicleTypes();
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
			vehicleTypeDao.saveOrUpdate(displayVehicle);
		}

		if (displayVehicle != null) {
			return displayVehicle;
		} else
			return null;
	}

	@Override
	@Transactional
	public String addVehicleType(VehicleTypeVo vehicleTypeVo) {
		String response = null;
		VehicleType vType = vehicleTypeRepo.findByVehicleName(vehicleTypeVo.getVehicleName());
		if(null != vType) {
			throw new BusinessException(ErrorCodes.VEHICLEEXISTS.name(), ErrorCodes.VEHICLEEXISTS.value());
		}
		if (vehicleTypeVo != null) {
			VehicleType vehicleType = new VehicleType();
			vehicleType.setCapacity(vehicleTypeVo.getCapacity());
			vehicleType.setVehicleName(vehicleTypeVo.getVehicleName());
			vehicleType.setPrice(vehicleTypeVo.getPrice());
			vehicleType.setCreatedBy(vehicleTypeVo.getCreatedBy());
			vehicleType.setHeight(vehicleTypeVo.getHeight());
			vehicleType.setLength(vehicleTypeVo.getLength());
			vehicleType.setSize(vehicleTypeVo.getSize());
			vehicleType.setWidth(vehicleTypeVo.getWidth());
			vehicleType.setSelectedVehicleUrl(transporterUtility.generateFilePathAndStore(vehicleTypeVo.getSelectedVehicle(),"vehicle"));
			vehicleType.setUnselectedVehicleUrl(transporterUtility.generateFilePathAndStore(vehicleTypeVo.getUnSelectedVehicle(), "vehicle"));
			try {
				vehicleTypeDao.save(vehicleType);
				response = WebConstants.SUCCESS;
			} catch (Exception e) {
				throw new BusinessException(ErrorCodes.NOTSAVED.name(), ErrorCodes.NOTSAVED.value());
			}
		}
		return response;
	}

	@Override
	public VehicleType deleteDisplayVehicle(VehicleType displayVehicle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public List<VehiclesByOrderResponse> fetchVehiclesByOrder(VehiclesByOrderRequest vehiclesByOrderRequest) {
		List<VehiclesByOrderResponse> orderResponse = new ArrayList<VehiclesByOrderResponse>();
		List<VehicleType> vehicleTypeList = vehicleTypeDao.fetchVehiclesByOrder(vehiclesByOrderRequest);
		if(!Utils.isNullOrEmpty(vehicleTypeList)) {
			for(VehicleType vehicleType : vehicleTypeList) {
				VehiclesByOrderResponse response = new VehiclesByOrderResponse();
				response.setVehicleType(vehicleType.getId());
				response.setVehicleName(vehicleType.getVehicleName());
				response.setVehicleSelectedUrl(vehicleType.getSelectedVehicleUrl());
				response.setVehicleUnSelecetedUrl(vehicleType.getUnselectedVehicleUrl());
				//response.setPrice(vehicleType.getPrice()*vehiclesByOrderRequest.getDistance());
				if(vehiclesByOrderRequest.getDistance() > vehicleType.getMinKm()) {
					double perKmPrice = (vehiclesByOrderRequest.getDistance() - vehicleType.getMinKm()) * vehicleType.getPerKm();
					response.setPrice(vehicleType.getPrice() + perKmPrice);
				} else {
					response.setPrice(vehicleType.getPrice());
				}
				orderResponse.add(response);
			}
		}
		return orderResponse;
	}

}
