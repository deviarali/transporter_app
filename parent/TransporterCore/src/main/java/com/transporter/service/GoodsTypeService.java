package com.transporter.service;

import java.util.List;

import com.transporter.vo.GoodsTypeVo;

public interface GoodsTypeService {

	List<GoodsTypeVo> getAllGoodsType();

	int updateGoodsStatus(int id, String status);

}
