package com.onlineMIS.ORM.DAO.headQ.qxbabydb;


import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.DAO.BaseDAO2;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Product2;
import com.onlineMIS.common.Common_util;


@Repository
public class ProductDaoImpl2 extends BaseDAO2<Product2> {

	public Product2 getBySerialNum(String serialNum) {
		DetachedCriteria productCriteria = DetachedCriteria.forClass(Product.class,"p");
		productCriteria.add(Restrictions.eq("p.serialNum", serialNum));


		List<Product2> productList =  this.getByCritera(productCriteria, true);
		if (productList != null && productList.size() ==1){
		   Product2 product = productList.get(0);
		   return product;
		}else 
		   return null;
	}

	

}
