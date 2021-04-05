package com.transporter.dao;

public interface GoodsTypeDao extends GenericDao {

	int updateGoodsStatus(int id, String status);
}
