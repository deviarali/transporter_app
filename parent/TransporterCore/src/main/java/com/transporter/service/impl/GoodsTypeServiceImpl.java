package com.transporter.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transporter.dao.GoodsTypeDao;
import com.transporter.model.GoodsType;
import com.transporter.service.GoodsTypeService;
import com.transporter.vo.GoodsTypeVo;

@Service
@Transactional
public class GoodsTypeServiceImpl implements GoodsTypeService {

	@Autowired
	GoodsTypeDao goodsTypeDao;
	
	@Override
	@Transactional
	public List<GoodsTypeVo> getAllGoodsType() {
		List<GoodsTypeVo> goodsTypesVoList = new ArrayList<GoodsTypeVo>();
		List<GoodsType> goodsTypesList = goodsTypeDao.loadAll(GoodsType.class);
		if(null != goodsTypesList && !goodsTypesList.isEmpty()) {
			for(GoodsType goodsType : goodsTypesList) {
				goodsTypesVoList.add(GoodsType.convertModelToVo(goodsType));
			}
		}
		return goodsTypesVoList;
	}

	
	
	@Override
	public int updateGoodsStatus(int id, String status) {
		int updated = goodsTypeDao.updateGoodsStatus(id, status);
		return updated;
	}
}
