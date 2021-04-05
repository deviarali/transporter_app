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
	@Transactional
	public List<VehicleTypeVo> getAllVehicleTypes() {
		List<VehicleType> displayVehicleList = vehicleTypeDao.getAllVehicleTypes();
		List<VehicleTypeVo> vehicleTypeVoList = new ArrayList<>();
		for(VehicleType vehicleType : displayVehicleList) {
			vehicleTypeVoList.add(VehicleType.convertModelToVo(vehicleType));
		}
		return vehicleTypeVoList;
	}

	@Override
	@Transactional
	public VehicleTypeVo updateDisplayVehicle(VehicleTypeVo vehicleTypeVo) {
		VehicleType vehicleType = vehicleTypeRepo.findOne(vehicleTypeVo.getId());
		if (vehicleType != null) {
			vehicleType.setId(vehicleTypeVo.getId());
			vehicleType.setCapacity(vehicleTypeVo.getCapacity());
			vehicleType.setVehicleName(vehicleTypeVo.getVehicleName());
			vehicleType.setPrice(vehicleTypeVo.getPrice());
			vehicleType.setHeight(vehicleTypeVo.getHeight());
			vehicleType.setLength(vehicleTypeVo.getLength());
			vehicleType.setSize(vehicleTypeVo.getSize());
			vehicleType.setWidth(vehicleTypeVo.getWidth());
			vehicleType.setPerKm(vehicleTypeVo.getPerKm());
			vehicleType.setMinKm(vehicleTypeVo.getMinKm());
			vehicleTypeDao.saveOrUpdate(vehicleType);
		} else {
			throw new BusinessException(ErrorCodes.VEHICLEIDNOTFOUND.name(), ErrorCodes.VEHICLEIDNOTFOUND.value());
		}

		return vehicleTypeVo;
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
			//vehicleType.setCreatedBy(vehicleTypeVo.getCreatedBy());
			vehicleType.setHeight(vehicleTypeVo.getHeight());
			vehicleType.setLength(vehicleTypeVo.getLength());
			vehicleType.setSize(vehicleTypeVo.getSize());
			vehicleType.setWidth(vehicleTypeVo.getWidth());
			vehicleType.setMinKm(vehicleTypeVo.getMinKm());
			vehicleType.setPerKm(vehicleTypeVo.getPerKm());
			//vehicleType.setSelectedVehicleUrl(transporterUtility.generateFilePathAndStore(vehicleTypeVo.getSelectedVehicle(),"vehicle"));
			//vehicleType.setUnselectedVehicleUrl(transporterUtility.generateFilePathAndStore(vehicleTypeVo.getUnSelectedVehicle(), "vehicle"));
			vehicleType.setSelectedVehicleUrl(transporterUtility.generateFilePathAndStore(null, vehicleTypeVo.getSelectedVehicle(),"vehicle", "vehicle"));
			vehicleType.setUnselectedVehicleUrl(transporterUtility.generateFilePathAndStore(null, vehicleTypeVo.getUnSelectedVehicle(), "vehicle", "vehicle"));
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
					response.setPrice(Math.ceil(vehicleType.getPrice() + perKmPrice));
				} else {
					response.setPrice(Math.ceil(vehicleType.getPrice()));
				}
				orderResponse.add(response);
			}
		}
		return orderResponse;
	}

}
