package com.onlineMIS.ORM.DAO.headQ.qxbabydb;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.DAO.BaseDAO2;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Year2;

@Repository
public class YearDaoImpl2 extends BaseDAO2<Year2>{

	
	public List<Year2> getYear(List<String> years){
		DetachedCriteria criteria = DetachedCriteria.forClass(Year2.class);
		criteria.add(Restrictions.in("year", years));
		
		return this.getByCritera(criteria, true);
	}
	
	public Map<String, Year2> getYearNameMap(){
		List<Year2> years = getAll(true);
		Map<String, Year2> yearMap = new HashMap<String, Year2>();
		for (Year2 year: years){
			yearMap.put(year.getYear().trim(), year);
		}
		
		return yearMap;
	}

	public List<Year2> getLatestYears() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Year2.class);
		criteria.addOrder(Order.desc("year"));

		
		return this.getByCritera(criteria, 0, 3, true);
	}

	public Year2 getYearByName(String yearS) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Year2.class);
		criteria.add(Restrictions.eq("year", yearS));
		
		List<Year2> years2 =  this.getByCritera(criteria, true);
		
		if (years2 != null && years2.size() > 0)
			return years2.get(0);
		else 
			return null;
	}

}
