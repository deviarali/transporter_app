package com.transporter.dao.impl;

import org.springframework.stereotype.Repository;

import com.transporter.dao.CustomerDetailsDao;

@Repository
public class CustomerDetailsDaoImpl extends GenericDaoImpl implements CustomerDetailsDao {

	/*@Override
	public int updateCustomer(CustomerDetails customerModel) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "UPDATE CustomerModel cm SET cm.firstName= :firstName, cm.lastName= :lastName WHERE cm.id= :id";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("firstName", customerModel.getFirstName());
		query.setParameter("lastName", customerModel.getLastName());
		query.setParameter("id", customerModel.getId());
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public CustomerDetails findCustomerByUserId(Long id) {
		String sqlQuery = "From CustomerModel cm where cm.user.id= :id";
		Query query = getSession().createQuery(sqlQuery);
		query.setParameter("id", id);
		return (CustomerModel) query.uniqueResult();
	}*/

}
