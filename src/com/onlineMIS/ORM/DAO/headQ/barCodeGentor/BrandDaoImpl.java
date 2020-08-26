package com.onlineMIS.ORM.DAO.headQ.barCodeGentor;

import org.springframework.stereotype.Repository;
import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;

@Repository
public class BrandDaoImpl extends BaseDAO<Brand> {
	@Override
	public void saveOrUpdate(Brand brand, boolean cached) {
		if (cached)
			getHibernateTemplate().setCacheQueries(true);

		if (brand.getChainStore() != null  && brand.getChainStore().getChain_id() == 0 )
			brand.setChainStore(null);
		
		//保存新的brand的时候需要赋予id
		if (brand.getBrand_ID() == 0){
			synchronized (this){
			    String BRAND_MAX_NOW = "SELECT MAX(brand_ID) FROM Brand";
			    int brandMax = this.executeHQLCount(BRAND_MAX_NOW, null, false);
			    
			    int brandId = Brand.LOCAL_START_ID;
			    if (brandMax >= Brand.LOCAL_START_ID)
			    	brandId = brandMax + 1;
			    
			    brand.setBrand_ID(brandId);
			    getHibernateTemplate().saveOrUpdate(brand);
			}
		} else {
		     getHibernateTemplate().saveOrUpdate(brand);
		}

	}


}
