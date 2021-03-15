package com.transporter.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.transporter.dao.CustomerDetailsDao;
import com.transporter.model.CustomerDetails;
import com.transporter.vo.CustomerDetailsVo;

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
	}*/

	@Override
	public CustomerDetails findCustomerByUserId(int id) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "From CustomerDetails cm where cm.user.id= :id";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("id", id);
		return (CustomerDetails) query.uniqueResult();
	}

	
	
	@Override
	public int updateCustomer(CustomerDetailsVo customerDetailsVo) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "UPDATE CustomerModel cm SET cm.addressStreet = :addressStreet, cm.addressCity = :addresCity"
				+ " cm.addressState = :addressState WHERE cm.id= :id";
		Query query = session.createQuery(sqlQuery);
		query.setParameter("id", customerDetailsVo.getId());
		query.setParameter("addressStreet", customerDetailsVo.getAddressStreet());
		query.setParameter("addressCity", customerDetailsVo.getAddressCity());
		query.setParameter("addressState", customerDetailsVo.getAddressState());
		return query.executeUpdate();
	}

}
