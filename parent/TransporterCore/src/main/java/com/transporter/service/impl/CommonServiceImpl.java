package com.transporter.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transporter.dao.CommonDao;
import com.transporter.model.InternalUserRoleMaster;
import com.transporter.service.CommonService;
import com.transporter.vo.InternalUserRoleMasterVo;

/**
 * @author Devappa.Arali
 *
 */

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	private CommonDao commonDao;
	
	@Override
	public List<InternalUserRoleMasterVo> getInternalUserRoles() {
		List<InternalUserRoleMaster> internalUserroleMastersList = commonDao.loadAll(InternalUserRoleMaster.class);
		List<InternalUserRoleMasterVo> internalUserroleMasterVoList = new ArrayList<>();
		if(null != internalUserroleMastersList && !internalUserroleMastersList.isEmpty()) {
			for(InternalUserRoleMaster internalUserRoleMaster : internalUserroleMastersList) {
				internalUserroleMasterVoList.add(InternalUserRoleMaster.convertModelToVo(internalUserRoleMaster));
			}
		}
		return internalUserroleMasterVoList;
	}

}
