package com.transporter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.constants.WebConstants;
import com.transporter.dao.VehicleDetailsDao;
import com.transporter.exceptions.BusinessException;
import com.transporter.exceptions.ErrorCodes;
import com.transporter.model.DriverDetails;
import com.transporter.model.VehicleDetails;
import com.transporter.model.VehicleType;
import com.transporter.repo.VehicleDetailsRepo;
import com.transporter.response.LatitudeLongitudeResponse;
import com.transporter.service.VehicleService;
import com.transporter.service.VehicleTypeService;
import com.transporter.utils.Utils;
import com.transporter.vo.DriverDetailsVo;
import com.transporter.vo.FetchSelectedVehiclesRequest;
import com.transporter.vo.FetchSelectedVehiclesResponse;
import com.transporter.vo.VehicleDetailsVo;
import com.transporter.vo.VehiclesByOrderRequest;
import com.transporter.vo.VehiclesByOrderResponse;

/**
 * @author Devappa.Arali
 *
 */


@Service
public class VehicleServiceImpl implements VehicleService {

	@Autowired
	private VehicleDetailsDao vehicleDetailsDao;
	
	@Autowired
	private VehicleDetailsRepo vehicleDetailsRepo;
	
	@Autowired
	private VehicleTypeService vehicleTypeService;
	
	@Value("${surrounding.area}")
	private double surroundingDistance;

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

	@Override
	public int updateLattitudeAndLongitude(int id, String lattitude, String longitude) {
		return vehicleDetailsDao.updateLattitudeAndLongitude(id, lattitude, longitude);
	}

	@Override
	@Transactional
	public List<FetchSelectedVehiclesResponse> fetchSelectedVehicles(
			FetchSelectedVehiclesRequest fetchSelectedVehiclesRequest) {
		fetchSelectedVehiclesRequest.setSurroundingDistance(surroundingDistance);
		List<VehicleDetails> responseIdsList = vehicleDetailsDao.fetchSelectedVehicles(fetchSelectedVehiclesRequest);
		List<VehicleDetails> vehiclesList = new ArrayList<VehicleDetails>();
		List<FetchSelectedVehiclesResponse> response = new ArrayList<>();
		if(!Utils.isNullOrEmpty(responseIdsList)) {
			for(VehicleDetails vehicleDetails : responseIdsList) {
				vehiclesList.add( (VehicleDetails) vehicleDetailsDao.get(VehicleDetails.class, vehicleDetails.getId()));
			}
		}
		
		if(!Utils.isNullOrEmpty(vehiclesList)) {
			for(VehicleDetails vehicleDetails : vehiclesList) {
				FetchSelectedVehiclesResponse fetchResponse = new FetchSelectedVehiclesResponse();
				fetchResponse.setVehicleId(vehicleDetails.getId());
				fetchResponse.setVehicleType(vehicleDetails.getVehicleType().getId());
				fetchResponse.setDriverId(vehicleDetails.getDriverDetails().getId());
				fetchResponse.setLattitude(vehicleDetails.getCurrentLattitude());
				fetchResponse.setLongitude(vehicleDetails.getCurrentLongitude());
				response.add(fetchResponse);
				
			}
		}
		return response;
	}

	@Override
	public List<VehicleDetails> fetchSelectedVehiclesToConfirmOrder(
			FetchSelectedVehiclesRequest fetchSelectedVehiclesRequest) {
				return vehicleDetailsRepo.fetchSelectedVehiclesToConfirmOrder(fetchSelectedVehiclesRequest.getLattitude(), 
				fetchSelectedVehiclesRequest.getLongitude(), fetchSelectedVehiclesRequest.getSurroundingDistance(), 
				fetchSelectedVehiclesRequest.getVehicleType());
	}

	@Override
	public List<VehiclesByOrderResponse> fetchVehiclesByOrder(VehiclesByOrderRequest vehiclesByOrderRequest) {
		return vehicleTypeService.fetchVehiclesByOrder(vehiclesByOrderRequest);
	}

	@Override
	public LatitudeLongitudeResponse getDriverLocations(int driverId) {
		LatitudeLongitudeResponse res = new LatitudeLongitudeResponse();
		VehicleDetails vehicleDetails = vehicleDetailsRepo.getdriverDeatils(driverId);
		if(vehicleDetails == null)
		{
			throw new BusinessException(ErrorCodes.DRIVERNOTFOUND.name(), ErrorCodes.DRIVERNOTFOUND.value());
		}
		res.setCurrentLattitude(vehicleDetails.getCurrentLattitude());
		res.setCurrentLongitude(vehicleDetails.getCurrentLongitude());
		return  res;
	}

}
