package com.transporter.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.transporter.dao.ExceptionMasterDao;
import com.transporter.model.ExceptionMaster;

/**
 * @author Devappa.Arali
 *
 */

@Repository
public class ExceptionMasterDaoImpl extends GenericDaoImpl
        implements ExceptionMasterDao {

	@Override
	public ExceptionMaster getExceptionMasterByType(String exceptionType) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ExceptionMaster.class);
		criteria.add(Restrictions.eq("exceptionType", exceptionType));
		return (ExceptionMaster) criteria.uniqueResult();
	}

}
