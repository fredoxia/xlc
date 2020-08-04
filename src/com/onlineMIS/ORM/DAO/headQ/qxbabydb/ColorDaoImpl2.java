package com.onlineMIS.ORM.DAO.headQ.qxbabydb;


import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.DAO.BaseDAO2;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Category2;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Color2;
import com.onlineMIS.common.loggerLocal;

@Repository
public class ColorDaoImpl2 extends BaseDAO2<Color2> {
	
	@Override
	public List<Color2> getAll(boolean cached){
		DetachedCriteria criteria = DetachedCriteria.forClass(Color2.class);
		criteria.addOrder(Order.asc("code"));
		
		return getByCritera(criteria, cached);
	}

	public int deleteAll() {
		String hql = "delete from Color";
		int count = executeHQLUpdateDelete(hql, new Object[]{}, true);
		loggerLocal.info("deleted " + count +" rows");
		
		return count;
	}

	public List<Color2> getColors(List<String> colors) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Color2.class);
		Disjunction dis=Restrictions.disjunction();  
		
		for (String color: colors){
            if (!color.trim().equals("")){
				dis.add(Restrictions.like("name", color,MatchMode.ANYWHERE));
			}
		}
		
		criteria.add(dis);
		
		return getByCritera(criteria, true);
	}

	public boolean checkColorExist(String color){
		DetachedCriteria criteria = DetachedCriteria.forClass(Color2.class);
		criteria.add(Restrictions.eq("name", color));
		
		List<Color2> colors = this.getByCritera(criteria, true);
		
		if (colors == null || colors.size() != 1)
			return false;
		else 
			return true;
	}

	public Color2 getColorByName(String colorString) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Color2.class);
		criteria.add(Restrictions.eq("name", colorString));
		
		List<Color2> colors = this.getByCritera(criteria, true);
		
		return colors.get(0);
	}

}
