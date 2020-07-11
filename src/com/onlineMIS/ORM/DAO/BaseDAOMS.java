package com.onlineMIS.ORM.DAO;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.onlineMIS.common.loggerLocal;
/**
 * the base DAO for Microsoft SQL Server
 * @author fredo
 * @param <T>
 *
 */
public class BaseDAOMS<T> extends DAOAbstract implements DAOInterface<T>{

	@Autowired
	private HibernateTemplate hibernateTemplateMS;

	public HibernateTemplate getHibernateTemplateMS() {
		return hibernateTemplateMS;
	}

	public void setHibernateTemplateMS(HibernateTemplate hibernateTemplateMS) {
		this.hibernateTemplateMS = hibernateTemplateMS;
	}


	@Override
	public List<T> getByCritera(DetachedCriteria criteria, boolean cached){
		if (cached)
			getHibernateTemplateMS().setCacheQueries(true);
		
		return getHibernateTemplateMS().findByCriteria(criteria);
	}
	
	@Override
	public void saveOrUpdate(T entity, boolean cached) {
		if (cached)
			getHibernateTemplateMS().setCacheQueries(true);
		getHibernateTemplateMS().saveOrUpdate(entity);
	}
	
	@Override
	public T get(Serializable id, boolean cached){
		if (cached)
			getHibernateTemplateMS().setCacheQueries(true);
		return (T)getHibernateTemplateMS().get(entityClass, id);
	}
	@Override
	public void delete(T entity, boolean cached){
		if (cached)
			getHibernateTemplateMS().setCacheQueries(true);
		getHibernateTemplateMS().delete(entity);
	}
	
	/**
	 * to initialized object
	 * @param object
	 */
	@Override
	public void initialize(Object object){
		getHibernateTemplateMS().initialize(object);
	}


	@Override
	public void save(T entity, boolean cached) {
		if (cached)
			getHibernateTemplateMS().setCacheQueries(true);
		getHibernateTemplateMS().save(entity);
	}

	@Override
	public void update(T entity, boolean cached) {
		if (cached)
			getHibernateTemplateMS().setCacheQueries(true);
		getHibernateTemplateMS().update(entity);
	}

	@Override
	public List<T> getByCritera(DetachedCriteria criteria, int start, int end, boolean cached) {
		if (cached)
			getHibernateTemplateMS().setCacheQueries(true);
		return getHibernateTemplateMS().findByCriteria(criteria, start, end);
	}

	@Override
	public List<T> getAll(boolean cached) {
		if (cached)
			getHibernateTemplateMS().setCacheQueries(true);
		
		return getHibernateTemplateMS().loadAll(entityClass);
	}
	
	@Override
	public List<T> getByHQL(String queryString, Object[] values, boolean cached) {
		getHibernateTemplateMS().setCacheQueries(cached);
		return getHibernateTemplateMS().find(queryString, values);
	}

	@Override
	public int executeHQLUpdateDelete(String queryString, Object[] values,
			boolean cached) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeHQLCount(String queryString, Object[] values,
			boolean cached) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void evict(T entity) {
		getHibernateTemplateMS().evict(entity);
		
	}

	@Override
	public List<Object> getByCriteriaProjection(DetachedCriteria criteria,
			boolean cached) {
		if (cached)
			getHibernateTemplateMS().setCacheQueries(true);

		
		List<Object> resultList = null;
		try{
		     resultList = getHibernateTemplateMS().findByCriteria(criteria);
		} catch (Exception e) {
			loggerLocal.error(e);
		}
		return resultList;
	}
	
	@Override
	public List<Object> executeProcedure(final String procedure, final Object[] values) {
		return getHibernateTemplateMS().execute(new HibernateCallback<List<Object>>() {
			@Override
			public List<Object> doInHibernate(Session session) throws HibernateException,
					SQLException {
				SQLQuery  q= session.createSQLQuery(procedure);
				q.setCacheable(false);
            	if (values != null && values.length > 0)
            	  for (int i =0; i < values.length; i++)
    				  q.setParameter(i, values[i]);
            	
    			List<Object> result = q.list();
    			
    			return result;
			}
		});	
	}

	@Override
	public List<Object> executeHQLSelect(String queryString, Object[] values,Integer[] pager,
			boolean cached) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> getByCriteriaProjection(DetachedCriteria criteria,
			Integer start, Integer end, boolean cached) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearSession() {
		// TODO Auto-generated method stub
		
	}


	
}
