package com.transporter.dao.impl;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.transporter.dao.GoodsTypeDao;


@Repository
@Transactional
public class GoodsTypeDaoImpl extends GenericDaoImpl implements GoodsTypeDao {

}
