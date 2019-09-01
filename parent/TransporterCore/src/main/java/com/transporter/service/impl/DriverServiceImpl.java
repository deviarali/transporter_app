package com.transporter.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;
import com.transporter.constants.WebConstants;
import com.transporter.dao.DriverDao;
import com.transporter.enums.UserRoleEnum;
import com.transporter.exceptions.BusinessException;
import com.transporter.exceptions.ErrorCodes;
import com.transporter.model.DriverDetails;
import com.transporter.model.User;
import com.transporter.service.DriverService;
import com.transporter.service.UserService;
import com.transporter.utility.TransporterUtility;
import com.transporter.vo.DriverDetailsVo;
import com.transporter.vo.UserRoleVo;
import com.transporter.vo.UserVo;

/**
 * @author Devappa.Arali
 *
 */

@Service
public class DriverServiceImpl implements DriverService{

	@Autowired
	private DriverDao driverDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TransporterUtility transporterUtility;
	
	@Override
	@Transactional
	public String registerDriver(DriverDetailsVo driverDetailsVo) {
		String response = null;
		UserVo userExists = userService.isUserExists(driverDetailsVo.getUser().getMobileNumber());
		if(null != userExists) {
			throw new BusinessException(ErrorCodes.MOEXISTS.name(), ErrorCodes.MOEXISTS.value());
		}
		UserVo userVo = driverDetailsVo.getUser();
		UserRoleVo userRoleVo = new UserRoleVo();
		userRoleVo.setId(UserRoleEnum.DRIVER.getId());
		userVo.setUserRole(userRoleVo);
		User user = userService.registerUser(userVo);
		DriverDetails driverDetails = new DriverDetails();
		driverDetails.setCreatedOn(new Date());
		driverDetails.setDrivername(userVo.getFirstName());
		driverDetails.setDriverVerificationStatus("pending");
		driverDetails.setOnRoad(0);
		User userCreatedBy = new User();
		userCreatedBy.setId(driverDetailsVo.getCreatedBy().getId());
		driverDetails.setCreatedBy(userCreatedBy);
		driverDetails.setUser(user);
		driverDao.save(driverDetails);
		if(driverDetails.getId() > 0) {
			response = WebConstants.SUCCESS;
		} else {
			throw new BusinessException(ErrorCodes.NOTSAVED.name(), ErrorCodes.NOTSAVED.value());
		}
		return response;
	}

	@Override
	@Transactional
	public String updateLattitudeAndLongitude(int id, String lattitude, String longitude) {
		String response = null;
		int updated = driverDao.updateLattitudeAndLongitude(id, lattitude, longitude);
		if(updated == 1) {
			response = WebConstants.SUCCESS;
		}
		return response;
	}

	@Override
	@Transactional
	public String updateDriverDocuments(int userId, MultipartFile adharMultiPart, MultipartFile dlMultiPart) {
		User userExists = userService.findById(userId);
		if (null == userExists) {
			throw new BusinessException(ErrorCodes.UNFOUND.name(), ErrorCodes.UNFOUND.value());
		}
		String generateFilePathAndStoreForAdhar = transporterUtility.generateFilePathAndStore(adharMultiPart, "driver");
		String generateFilePathAndStoreForDl = transporterUtility.generateFilePathAndStore(dlMultiPart, "driver");
		if (!(StringUtils.isNullOrEmpty(generateFilePathAndStoreForAdhar))
				|| !(StringUtils.isNullOrEmpty(generateFilePathAndStoreForDl))) {
			int updated = driverDao.updateDriverDocuments(userId, generateFilePathAndStoreForAdhar,
					generateFilePathAndStoreForDl);
			if (updated != 0) {
				return "Documents Updated..";
			}
		}
		return null;
	}

	@Override
	@Transactional
	public DriverDetailsVo updateDriverOnRoadAndOffRoad(int driverId, DriverDetailsVo detailsVo) {
		DriverDetails driverDetails = driverDao.findById(driverId);
		if (driverDetails == null) {
			throw new BusinessException(ErrorCodes.UNFOUND.name(), ErrorCodes.UNFOUND.value());
		}
		driverDetails.setOnRoad(detailsVo.getOnRoad());
		driverDao.saveOrUpdate(driverDetails);
		return DriverDetails.convertModelToVo(driverDetails);
	}

	@Override
	@Transactional
	public DriverDetailsVo updateDriverAddress(DriverDetailsVo driverDetailsVo) {
		DriverDetails driverDetails = driverDao.findById(driverDetailsVo.getId());
		if (driverDetails == null) {
			return null;
		}
		driverDetails.setDrivername(driverDetailsVo.getDrivername());
		driverDetails.setAddressCity(driverDetailsVo.getAddressCity());
		driverDetails.setAddressState(driverDetailsVo.getAddressState());
		driverDetails.setAddressStreet(driverDetailsVo.getAddressStreet());
		driverDetails.setAddressZipcode(driverDetailsVo.getAddressZipcode());
		driverDetails.setDateOfBirth(driverDetailsVo.getDateofbirth());
		driverDao.saveOrUpdate(driverDetails);
		return DriverDetails.convertModelToVo(driverDetails);
	}
}
