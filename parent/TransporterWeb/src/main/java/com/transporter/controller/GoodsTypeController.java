package com.transporter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.transporter.response.CommonResponse;
import com.transporter.service.GoodsTypeService;
import com.transporter.utils.RestUtils;

@RestController
public class GoodsTypeController {

	@Autowired
	GoodsTypeService goodsTypeService;
	
	@RequestMapping(value = "customer/goodsypes", method = RequestMethod.GET)
	public CommonResponse getGoodsType() {
		CommonResponse response = null;
		response = RestUtils.wrapObjectForSuccess(goodsTypeService.getAllGoodsType());		
		return response;
	}
	
}
