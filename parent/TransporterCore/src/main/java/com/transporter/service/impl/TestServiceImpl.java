package com.transporter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transporter.dao.TestDao;
import com.transporter.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao testDao;
	
	@Override
	public String getMessage() {
		return testDao.getMessage();
	}

}
