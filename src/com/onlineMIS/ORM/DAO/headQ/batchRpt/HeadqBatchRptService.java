package com.onlineMIS.ORM.DAO.headQ.batchRpt;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javassist.expr.NewArray;

import org.apache.naming.java.javaURLContextFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hp.hpl.sparta.xpath.ThisNodeTest;
import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.chainMgmt.QxbabyConfDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainInOutStockDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainTransferFlowAcctDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainTransferOrderDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainBatchRptRepositotyDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainMonthlyActiveNumDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainMonthlyHotBrandDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainMonthlyHotProductDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainWeeklyHotBrandDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainWeeklyHotProductDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.report.ChainWeeklySalesDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.sales.ChainDailySalesDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.sales.ChainDailySalesImpactDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.sales.ChainStoreSalesOrderDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.sales.PurchaseService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.BrandDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.QuarterDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.YearDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryOrderDAOImpl;
import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonProductAnalysisItem;
import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonProductAnalysisRpt;
import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonSalesAnalysisItem;
import com.onlineMIS.ORM.entity.chainS.batchRpt.ChainCurrentSeasonSalesAnalysisRpt;
import com.onlineMIS.ORM.entity.chainS.batchRpt.batchRptTemplate.ChainCurrentSeasonProductAnalysisTemplate;
import com.onlineMIS.ORM.entity.chainS.batchRpt.batchRptTemplate.ChainCurrentSeasonSalesAnalysisTemplate;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.QxbabyConf;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainTransferOrderFlowAcct;
import com.onlineMIS.ORM.entity.chainS.report.ChainBatchRptRepositoty;


import com.onlineMIS.ORM.entity.chainS.report.ChainTransferAcctFlowItem;
import com.onlineMIS.ORM.entity.chainS.report.rptTemplate.ChainSalesReportVIPPercentageTemplate;
import com.onlineMIS.ORM.entity.chainS.report.rptTemplate.ChainTransferAcctFlowTemplate;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPType;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Category;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Brand2;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Category2;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Color2;
import com.onlineMIS.ORM.entity.headQ.supplier.finance.FinanceCategorySupplier;
import com.onlineMIS.common.loggerLocal;

@Service
public class HeadqBatchRptService {
	@Autowired
	private ChainStoreService chainStoreService;
	
	@Autowired
	private ChainStoreDaoImpl chainStoreDaoImpl;

	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.barCodeGentor.BrandDaoImpl brandDaoImpl;
	
	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.barCodeGentor.YearDaoImpl yearDaoImpl;
	
	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.barCodeGentor.QuarterDaoImpl quarterDaoImpl;
	
	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.qxbabydb.BrandDaoImpl2 brandDaoImpl2;
	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.barCodeGentor.CategoryDaoImpl categoryDaoImpl;
	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.qxbabydb.CategoryDaoImpl2 categoryDaoImpl2;	
	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ColorDaoImpl colorDaoImpl;
	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.qxbabydb.ColorDaoImpl2 colorDaoImpl2;	

	private Calendar today = Calendar.getInstance();
	
	/**
	 * 运行每周的批量报表程序
	 * 1. 每周运行当季货品分析报表
	 */
	public Response runHourlyBasicImport(){

		Response response = new Response();
		
		loggerLocal.infoB(new Date() + " 开始 每小时的基本信息导入 :  HeadqBatchRptService.runHourlyBasicImport()");
		
		//1. 更新brand
	    String BRAND_MAX_NOW = "SELECT MAX(brand_ID) FROM Brand";
	    int brandMax = brandDaoImpl.executeHQLCount(BRAND_MAX_NOW, null, false);
	    
	    //获取千禧比这个大的
    	DetachedCriteria criteria = DetachedCriteria.forClass(com.onlineMIS.ORM.entity.headQ.qxbabydb.Brand2.class);
    	criteria.add(Restrictions.gt("brand_ID", brandMax));
    	criteria.add(Restrictions.isNull("chain_id"));
    	List<com.onlineMIS.ORM.entity.headQ.qxbabydb.Brand2> brands =  brandDaoImpl2.getByCritera(criteria, false);
	  
    	if (brands != null && brands.size() > 0){
    		for (Brand2 brand2 : brands){
    			Brand brand = new Brand();
    			BeanUtils.copyProperties(brand2,brand);
    			loggerLocal.infoB(brand.toString());
    			brandDaoImpl.save(brand, true);
    		}
    	}
    	
    	//2. 更新category
	    String CATEGORY_MAX_NOW = "SELECT MAX(category_ID) FROM Category";
	    int categoryMax = categoryDaoImpl.executeHQLCount(CATEGORY_MAX_NOW, null, false);
	    
	    //获取千禧比这个大的
    	DetachedCriteria criteria2 = DetachedCriteria.forClass(Category2.class);
    	criteria2.add(Restrictions.gt("category_ID", categoryMax));
    	criteria2.add(Restrictions.eq("chainId",-1));
    	List<Category2> categories =  categoryDaoImpl2.getByCritera(criteria2, false);
	  
    	if (categories != null && categories.size() > 0){
    		for (Category2 category2 : categories){
    			Category category = new Category();
    			BeanUtils.copyProperties(category2,category);
    			loggerLocal.infoB(category.toString());
    			categoryDaoImpl.save(category, true);
    		}
    	}		

		//3.更新color
	    String COLOR_MAX_NOW = "SELECT MAX(colorId) FROM Color";
	    int colorMax = colorDaoImpl.executeHQLCount(COLOR_MAX_NOW, null, false);
	    
	    //获取千禧比这个大的
    	DetachedCriteria criteria4 = DetachedCriteria.forClass(Color2.class);
    	criteria4.add(Restrictions.gt("colorId", colorMax));

    	List<Color2> color2s =  colorDaoImpl2.getByCritera(criteria4, false);
	  
    	if (color2s != null && color2s.size() > 0){
    		for (Color2 color2 : color2s){
    			Color color = new Color();
    			BeanUtils.copyProperties(color2,color);
    			loggerLocal.infoB(color.toString());
    			colorDaoImpl.save(color, true);
    		}
    	}	
		return response;
	}
	
}




