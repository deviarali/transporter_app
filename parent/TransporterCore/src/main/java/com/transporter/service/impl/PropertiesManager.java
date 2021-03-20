package com.transporter.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transporter.dao.PropertiesDao;
import com.transporter.model.Properties;
import com.transporter.vo.PropertiesVo;

@Service
public class PropertiesManager {
	
	@Autowired
	private PropertiesDao propertiesDao;
	
	public List<PropertiesVo> getAllProperties() {
		List<Properties> propertiesList = propertiesDao.getAllProperties();
		List<PropertiesVo> propertiesVoList = new ArrayList<PropertiesVo>();
		for(Properties properties : propertiesList) {
			propertiesVoList.add(Properties.convertModelToVo(properties));
		}
		return propertiesVoList;
	}
	
	public PropertiesVo getPropertiesByName(String propertyName) {
		Properties properties = propertiesDao.getPropertiesByName(propertyName);
		return Properties.convertModelToVo(properties);
	}
}
