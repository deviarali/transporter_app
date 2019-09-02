package com.transporter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.transporter.constants.WebConstants;
import com.transporter.model.GoodsType;
import com.transporter.response.CommonResponse;
import com.transporter.service.GoodsTypeService;
import com.transporter.utils.RestUtils;

@RestController
public class GoodsTypeController {

	@Autowired
	GoodsTypeService goodsTypeService;

	@RequestMapping(value = "goods/goodsTypes", method = RequestMethod.GET)
	public CommonResponse getGoodsType() {
		CommonResponse response = null;
		List<GoodsType> goodsTypeList = goodsTypeService.getAllGoodsType();
		if (goodsTypeList != null && goodsTypeList.size() > 0) {
			response = RestUtils.wrapObjectForSuccess(goodsTypeList);
		} else {
			response = RestUtils.wrapObjectForFailure("Goods type not found", "error", WebConstants.WEB_RESPONSE_ERROR);
		}

		return response;
	}

}
