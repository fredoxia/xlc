package com.onlineMIS.ORM.DAO.headQ.qxbabydb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.DAO.BaseDAO2;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.ProductBarcode2;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;

@Repository
public class ProductBarcodeDaoImpl2  extends BaseDAO2<ProductBarcode2> {
	@Autowired
	private ProductDaoImpl2 productDaoImpl;
	@Autowired
	private ColorDaoImpl2 colorDaoImpl;

	


}
