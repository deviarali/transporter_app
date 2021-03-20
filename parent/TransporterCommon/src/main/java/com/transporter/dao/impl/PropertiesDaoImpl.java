package com.transporter.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.transporter.dao.PropertiesDao;
import com.transporter.model.Properties;

@Repository
public class PropertiesDaoImpl extends GenericDaoImpl implements PropertiesDao {

	@Override
	@Cacheable
	public List<Properties> getAllProperties() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT p FROM Properties p";
		Query query = session.createQuery(sqlQuery);
		return (List<Properties>) query.list();
	}

	@Override
	@Cacheable("properties")
	public Properties getPropertiesByName(String propertyName) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT p FROM Properties p WHERE p.propertyName = :propertyName";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("propertyName", propertyName);
		return (Properties) query.uniqueResult();
	}
	
}
