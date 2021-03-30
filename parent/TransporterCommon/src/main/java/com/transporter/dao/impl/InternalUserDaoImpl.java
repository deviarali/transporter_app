package com.transporter.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.transporter.dao.InternalUserDao;
import com.transporter.model.InternalUserRoleMaster;

/**
 * @author Devappa.Arali
 *
 */

@Repository
public class InternalUserDaoImpl extends GenericDaoImpl implements InternalUserDao {

	@Override
	public List<InternalUserRoleMaster> getInternalUserRoles() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "From InternalUserRoleMaster irm";
		Query query = session.createQuery(sqlQuery);
		List<InternalUserRoleMaster> list = (List<InternalUserRoleMaster>) query.list();
		return list;
	}

}
