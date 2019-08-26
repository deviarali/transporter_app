package com.transporter.dao.impl;

import org.springframework.stereotype.Repository;

import com.transporter.dao.TestDao;

@Repository
public class TestDaoImpl extends GenericDaoImpl implements TestDao {

	@Override
	public String getMessage() {
		return "Welcome To Transporters";
	}

}
