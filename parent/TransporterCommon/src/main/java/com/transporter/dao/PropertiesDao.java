package com.transporter.dao;

import java.util.List;

import com.transporter.model.Properties;

public interface PropertiesDao {

	List<Properties> getAllProperties();

	Properties getPropertiesByName(String propertyName);

}
