package com.onlineMIS.ORM.DAO.headQ.qxbabydb;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.DAO.BaseDAO2;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Category2;
import com.onlineMIS.common.loggerLocal;

@Repository
public class CategoryDaoImpl2 extends BaseDAO2<Category2> {
	
	public List<Category2> getHeadCategry(boolean cached){
		DetachedCriteria criteria = DetachedCriteria.forClass(Category2.class);
		criteria.add(Restrictions.eq("chainId", Category2.TYPE_HEAD));
		return this.getByCritera(criteria, cached);
	}
	
	public List<Category2> getChainCategry(boolean cached){
		DetachedCriteria criteria = DetachedCriteria.forClass(Category2.class);
		criteria.add(Restrictions.eq("chainId", Category2.TYPE_CHAIN));
		return this.getByCritera(criteria, cached);
	}	
	
	public boolean checkCategoryExist(String category){
		DetachedCriteria criteria = DetachedCriteria.forClass(Category2.class);
		criteria.add(Restrictions.eq("category_Name", category));
		criteria.add(Restrictions.eq("chainId", Category2.TYPE_HEAD));
		List<Category2> categories = this.getByCritera(criteria, true);
		
		if (categories == null || categories.size() != 1)
			return false;
		else 
			return true;
	}

	public Category2 getCategoryByName(String categoryString) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Category2.class);
		criteria.add(Restrictions.eq("category_Name", categoryString));
		criteria.add(Restrictions.eq("chainId", Category2.TYPE_HEAD));
		List<Category2> categories = this.getByCritera(criteria, true);
		
		return categories.get(0);
	}
}
