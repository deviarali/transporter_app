package com.transporter.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.service.VehicleService;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

	/*@Autowired
	private OwnerService ownerService;

	@Override
	public Long registerVehicle(VehicleVO vehicleVO) {
		
		VehicleModel vehicleModel = ModelVoConvertUtils.convertVehicleVoToVehicleModel(vehicleVO);

		Long vehicleId = (Long) this.saveDomain(vehicleModel);

		OwnerModel ownerModel = (OwnerModel) ownerService.loadDomain(OwnerModel.class, vehicleVO.getOwnerId());
		
		ownerModel.addVehicle(vehicleModel);
		
		ownerService.updateDomain(ownerModel);
		
		return vehicleVO.getId();
	}*/

}
