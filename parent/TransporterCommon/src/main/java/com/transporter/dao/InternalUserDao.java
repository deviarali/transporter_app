package com.transporter.dao;

import java.util.List;

import com.transporter.model.InternalUserRoleMaster;

/**
 * @author Devappa.Arali
 *
 */

public interface InternalUserDao extends GenericDao {

	List<InternalUserRoleMaster> getInternalUserRoles();

}
