package com.transporter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.transporter.constants.WebConstants;
import com.transporter.dao.CustomerDetailsDao;
import com.transporter.dao.UserDao;
import com.transporter.enums.UserRoleEnum;
import com.transporter.exceptions.BusinessException;
import com.transporter.exceptions.ErrorCodes;
import com.transporter.model.CustomerDetails;
import com.transporter.model.TripDetails;
import com.transporter.model.User;
import com.transporter.repo.CustomerDetailsRepo;
import com.transporter.service.CustomerDetailsService;
import com.transporter.service.TripDetailsService;
import com.transporter.service.UserService;
import com.transporter.vo.CustomerDetailsVo;
import com.transporter.vo.TripDetailsHistoryVo;
import com.transporter.vo.TripDetailsVo;
import com.transporter.vo.UserRoleVo;
import com.transporter.vo.UserVo;

@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	private CustomerDetailsDao customerDetailsDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private CustomerDetailsRepo customerDetailsRepo;

	@Autowired
	TripDetailsService tripDetailService;

	@Override
	@Transactional
	public String registerCustomer(CustomerDetailsVo customerDetailsVo) {
		String response = null;
		UserVo userExists = userService.isUserExists(customerDetailsVo.getUser().getMobileNumber());
		if (null != userExists) {
			throw new BusinessException(ErrorCodes.MOEXISTS.name(), ErrorCodes.MOEXISTS.value());
		}
		UserVo userVo = customerDetailsVo.getUser();
		UserRoleVo userRoleVo = new UserRoleVo();
		userRoleVo.setId(UserRoleEnum.CUSTOMER.getId());
		userVo.setUserRole(userRoleVo);
		User user = userService.registerUser(userVo);

		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setUser(user);
		customerDetailsDao.save(customerDetails);
		if (customerDetails.getId() > 0) {
			int generate = generateOtp(customerDetailsVo.getUser().getMobileNumber());
			if (generate == 1) {
				response = WebConstants.SUCCESS;
			}
		}
		return response;
	}

	@Override
	public UserVo isUserExists(CustomerDetailsVo customerDetailsVo) {
		return userService.isUserExists(customerDetailsVo.getUser().getMobileNumber());
	}

	@Override
	public int generateOtp(String mobileNumber) {
		UserVo userExists = userService.isUserExists(mobileNumber);
		if (null == userExists) {
			throw new BusinessException(ErrorCodes.CNFOUND.name(), ErrorCodes.CNFOUND.value());
		}
		return userService.generateOtp(mobileNumber);
	}

	@Override
	@Transactional
	public CustomerDetailsVo validateOtp(String mobile, String otp) {
		CustomerDetailsVo customerDetailsVo = null;
		UserVo userVo = userService.validateOtp(mobile, otp);
		if (userVo == null) {
			throw new BusinessException(ErrorCodes.INVALIDOTP.name(), ErrorCodes.INVALIDOTP.value());
		}
		customerDetailsVo = findCustomerByUserId(userVo.getId());
		return customerDetailsVo;
	}

	@Override
	@Transactional
	public CustomerDetailsVo updateCustomer(CustomerDetailsVo customerDetailsVo) {
		User user = userService.updateUser(customerDetailsVo.getUser());

		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setUser(user);
		customerDetailsDao.saveOrUpdate(customerDetails);
		return CustomerDetails.convertModelToVO(customerDetails);
	}

	@Transactional
	@Override
	public CustomerDetailsVo findCustomerByUserId(int id) {
		CustomerDetails customerDetails = customerDetailsDao.findCustomerByUserId(id);
		CustomerDetailsVo customerDetailsVo = CustomerDetails.convertModelToVO(customerDetails);
		return customerDetailsVo;
	}

	@Override
	@Transactional
	public CustomerDetailsVo updateUserProfile(UserVo userVo) {
		User user = userService.findById(userVo.getId());
		if (user == null) {
			return null;
		}
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setId(userVo.getCustomerDetails().getId());
		customerDetails.setUser(user);
		customerDetails.setAddressCity(userVo.getCustomerDetails().getAddressCity());
		customerDetails.setAddressState(userVo.getCustomerDetails().getAddressState());
		customerDetails.setAddressStreet(userVo.getCustomerDetails().getAddressStreet());
		customerDetails.setAddressZipcode(userVo.getCustomerDetails().getAddressZipcode());
		customerDetails.setDateofbirth(userVo.getCustomerDetails().getDateofbirth());
		customerDetailsDao.saveOrUpdate(customerDetails);
		return CustomerDetails.convertModelToVO(customerDetails);
	}

	@Override
	@Transactional
	public CustomerDetails findCustomerById(int customerId) {
		return customerDetailsRepo.findOne(customerId);
	}

	@Override
	@Transactional
	public CustomerDetailsVo getUserById(int id) {
		CustomerDetails customerDetails = customerDetailsDao.findCustomerByUserId(id);
		CustomerDetailsVo customerDetailsVo = null;
		if (customerDetails != null) {
			customerDetailsVo = CustomerDetails.convertModelToVO(customerDetails);
			List<TripDetails> tripDetails = tripDetailService.getTripHistoryByUserId(id);
			if (tripDetails != null && tripDetails.size() > 0) {
				Gson gson = new Gson();
				List<TripDetailsVo> tripDetailsVoList = new ArrayList<>();
				tripDetails.forEach(data -> {
					TripDetailsVo tripDetailsVo = TripDetails.convertEntityTOVo(data);
					TripDetailsHistoryVo tripDetailsHistoryVo = gson.fromJson(tripDetailsVo.getTripHistoryJson(),
							TripDetailsHistoryVo.class);
					if (null != tripDetailsHistoryVo) {
						if (null != tripDetailsVo.getTripStarttime() && null != tripDetailsVo.getTripEndtime()) {
							Period p = new Period(tripDetailsVo.getTripStarttime().getTime(),
									tripDetailsVo.getTripEndtime().getTime());
							if (p.getMinutes() < 10) {
								tripDetailsHistoryVo.setTripHours(
										String.valueOf(p.getHours()) + ":0" + String.valueOf(p.getMinutes()));
							} else {
								tripDetailsHistoryVo.setTripHours(
										String.valueOf(p.getHours()) + ":" + String.valueOf(p.getMinutes()));
							}
						}
						if (null != tripDetailsHistoryVo.getCgstPercentage()
								&& null != tripDetailsHistoryVo.getSgstPercentage()) {
							Double cgstAmount = Double.valueOf(tripDetailsVo.getAmount())
									* tripDetailsHistoryVo.getCgstPercentage() / 100;
							Double sgstAmount = Double.valueOf(tripDetailsVo.getAmount())
									* tripDetailsHistoryVo.getSgstPercentage() / 100;
							Double rideFare = Double.valueOf(tripDetailsVo.getAmount()) - (cgstAmount + sgstAmount);
							tripDetailsVo.setCgst(cgstAmount);
							tripDetailsVo.setSgst(sgstAmount);
							tripDetailsVo.setRideFare(rideFare);
							tripDetailsHistoryVo.setCgst(10.00);
							tripDetailsHistoryVo.setSgst(10.00);
						}
					}
					tripDetailsVo.setTripDetailsHistory(tripDetailsHistoryVo);
					tripDetailsVoList.add(tripDetailsVo);
				});

				customerDetailsVo.setTripDetailsVoList(tripDetailsVoList);
			}
		} else {
			throw new BusinessException(ErrorCodes.CNFOUND.name(), ErrorCodes.CNFOUND.value());
		}
		return customerDetailsVo;
	}

	@Override
	@Transactional
	public List<CustomerDetailsVo> getTopCustomerForWeek(Integer count) {
		List<CustomerDetailsVo> customerDetails = new ArrayList<>();
		Map<Integer, Long> topCustomersForWeek = tripDetailService.getTopCustomerForWeek(count);

		topCustomersForWeek.entrySet().forEach(key -> {
			Integer driverId = key.getKey();
			try {
				CustomerDetailsVo customerVo = findCustomerByUserId(driverId);
				if (customerVo != null) {
					customerDetails.add(customerVo);
				}
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		});
		return customerDetails;
	}

}
