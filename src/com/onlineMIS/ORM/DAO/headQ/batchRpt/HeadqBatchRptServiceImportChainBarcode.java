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
import java.util.Random;
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
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.AreaDaoImpl;
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
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Area;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Category;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.NumPerHand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductUnit;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Brand2;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Category2;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Color2;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.NumPerHand2;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Product2;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.ProductBarcode2;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.ProductUnit2;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Year2;
import com.onlineMIS.ORM.entity.headQ.supplier.finance.FinanceCategorySupplier;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.filter.SystemParm;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

@Service
public class HeadqBatchRptServiceImportChainBarcode {
	@Autowired
	private AreaDaoImpl areaDaoImpl;
	@Autowired
	private ChainStoreService chainStoreService;
	
	@Autowired
	private ChainStoreDaoImpl chainStoreDaoImpl;

	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.barCodeGentor.BrandDaoImpl brandDaoImpl;
	
	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.barCodeGentor.YearDaoImpl yearDaoImpl;
	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.qxbabydb.YearDaoImpl2 yearDaoImpl2;	
	
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
	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductUnitDaoImpl productUnitDaoImpl;
	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.qxbabydb.ProductUnitDaoImpl2 productUnitDaoImpl2;	
	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.barCodeGentor.NumPerHandDaoImpl numPerHandDaoImpl;
	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.qxbabydb.NumPerHandDaoImpl2 numPerHandDaoImpl2;	
	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductDaoImpl productDaoImpl;
	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.qxbabydb.ProductDaoImpl2 productDaoImpl2;	
	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeDaoImpl productBarcodeDaoImpl;
	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.qxbabydb.ProductBarcodeDaoImpl2 productBarcodeDaoImpl2;	
	private Calendar today = Calendar.getInstance();
	
	/**
	 * 1. 每个小时运行系统导入千禧基础信息
	 */
	public Response runHourlyBasicImport(){

		Response response = new Response();
		
		Random random=new Random();
		int randomNum = random.nextInt(10);
		loggerLocal.infoB("\n\n\n----------- " + randomNum+ " "+new Date() + " 开始 每小时的基本信息导入 :  HeadqBatchRptService.runHourlyBasicImport()");
		
	 	DetachedCriteria criteria = DetachedCriteria.forClass(com.onlineMIS.ORM.entity.headQ.qxbabydb.Brand2.class);
    	
	 	
    	//刘菊连锁店条码问题
    	int liujuQX = Integer.parseInt(SystemParm.getParm("liujuChainStoreId"));
    	int liujuXL = Integer.parseInt(SystemParm.getParm("liujuXiLeStoreId"));
    	criteria.add(Restrictions.eq("chain_id", liujuQX));
    	List<com.onlineMIS.ORM.entity.headQ.qxbabydb.Brand2> brands =  brandDaoImpl2.getByCritera(criteria, false);
    	if (brands != null && brands.size() > 0){
    		for (Brand2 brand2 : brands){
    			Brand brand = new Brand();
    			BeanUtils.copyProperties(brand2,brand);
    			
    			if (brand2.getChain_id() != null && brand2.getChain_id() == liujuQX) {
    				ChainStore store = new ChainStore();
    				store.setChain_id(liujuXL);
    				brand.setChainStore(store);
    			}
    			
    			try {
    				loggerLocal.infoB(brand.toString());
    				brandDaoImpl.save(brand, true);
    			} catch (Exception e){
    				loggerLocal.errorB("导入错误 : " + randomNum+ " " + brand);
    				e.printStackTrace();
    			}
    		}
    	}
    	
     	//2. 更新product

	    	DetachedCriteria criteria8 = DetachedCriteria.forClass(Product2.class);

	    	criteria8.add(Restrictions.eq("chainId", liujuQX));
	    	criteria8.addOrder(Order.asc("createDate"));
	    	List<Product2> products =  productDaoImpl2.getByCritera(criteria8, false);
		    
	    	
	    	if (products != null && products.size() > 0){
	    		for (Product2 product2 : products){
	    			
	    			//是2019年以前的条码略过
	    			if (product2.getYearId() < 9){
	    				loggerLocal.warnB(" 跳过2019年以前条码 : " + randomNum+ " " + product2.toString());
	    				continue;
	    			} else {		    			
		    			int areaId = product2.getAreaId();
		    			int yearId = product2.getYearId();
		    			int quarterId = product2.getQuarterId();
		    			int brandId = product2.getBrandId();
		    			int categoryId = product2.getCategoryId();
		    			
		    			Area area = areaDaoImpl.get(areaId, true);
		    			Year year = yearDaoImpl.get(yearId, true);
		    			Quarter quarter = quarterDaoImpl.get(quarterId, true);
		    			Brand brand = brandDaoImpl.get(brandId, true);
		    			Category category = categoryDaoImpl.get(categoryId, true);
		    			
	        			
		    			if (area == null || year == null || quarter == null || brand == null || category ==null){
		    				loggerLocal.errorB(" 无法导入 ,出现基础资料null ： " + areaId + "," + yearId + "," +quarterId + "," +brandId + "," +categoryId);
		    				continue;
		    			} else {
		    				Product product = new Product();
		    				BeanUtils.copyProperties(product2,product);
		    			    product.setArea(area);
		    			    product.setYear(year);
		    			    product.setQuarter(quarter);
		    			    product.setBrand(brand);
		    			    product.setCategory(category);
		    			    
		        			if (product2.getChainId() != null && product2.getChainId() == liujuQX) {
		        				ChainStore store = new ChainStore();
		        				store.setChain_id(liujuXL);
		        				product.setChainStore(store);
		        			}
		    				
							String serialNum = product2.getSerialNum();
							Product productOriginal = productDaoImpl.getBySerialNum(serialNum, null);
							
							if (productOriginal != null){

								try {
									loggerLocal.infoB("更新Product : " + randomNum+ " " + product.toString());
									int productId = productOriginal.getProductId();
									double recost = productOriginal.getRecCost();
									BeanUtils.copyProperties(product,productOriginal);
									productOriginal.setProductId(productId);
									productOriginal.setRecCost(recost);
									
					    			if (productOriginal.getChainStore() != null && productOriginal.getChainStore().getChain_id() == liujuQX)
					    				productOriginal.getChainStore().setChain_id(liujuXL);
									
								    productDaoImpl.update(productOriginal, true);
								} catch (Exception e){
									System.out.println(productOriginal);
									e.printStackTrace();
								}
							} else {
								try {
									loggerLocal.infoB("新建Product : " + randomNum+ " " + product.toString());
					    			
									product.setRecCost(0);
									productDaoImpl.save(product, true);
								} catch (Exception e){
									System.out.println(productOriginal);
									e.printStackTrace();
								}
							}

		    			}
	    			}
	    		}
	    	}	

	    
	  	//3. 更新productBarcode

	    	DetachedCriteria criteria9 = DetachedCriteria.forClass(ProductBarcode2.class);

	    	criteria9.add(Restrictions.eq("chainId", liujuQX));
	    	criteria9.addOrder(Order.asc("createDate"));
	    	List<ProductBarcode2> pbs =  productBarcodeDaoImpl2.getByCritera(criteria9, false);
		    
	    	
	    	if (pbs != null && pbs.size() > 0){
	    		for (ProductBarcode2 product2 : pbs){
	    			
	    			//product 不存在就掠过
	    			String serialNum = String.valueOf(product2.getProductId());
	    			Product product = productDaoImpl.getBySerialNum(serialNum, null);
	    			
	    			if (product == null){
	    				loggerLocal.warnB("skip , 主产品信息没有找到  : " + randomNum+ " " + product2.toString());
	    				continue;
	    			} else {	
	        			if (product2.getChainId() != null && product2.getChainId() == liujuQX) {
	        				System.out.println("-------------------");
	        			}
	    				String barcode2 = product2.getBarcode();
	    				ProductBarcode pb = productBarcodeDaoImpl.getByBarcode(barcode2);
	    				
	    				if (pb == null){
	                        Integer chainId = product2.getChainId();
	                        Integer colorId = product2.getColorId();
	                        //Integer sizeId = product2.getSizeId();
	                        
	                        Color color = null;
//	                        if (chainId != null){
//			    				loggerLocal.errorB(" 无法导入 ,不能导入连锁店条码 ： " + randomNum+ " " + product2);
//			    				continue;
//	                        }
	   
	                        if (colorId != null){
	                        	color = colorDaoImpl.get(colorId, true);
	                        	if (color == null){
	    		    				loggerLocal.errorB(" 无法导入 ,颜色导入出现问题 ： " + randomNum+ " " + product2);
	    		    				continue;
	                        	}
	                        }
	                        
	                        //查询这个条码是否已经输入
			    			
			    			pb = new ProductBarcode();
			    			BeanUtils.copyProperties(product2,pb);
			    			pb.setChainStore(null);
			    			pb.setColor(color);
			    			pb.setProduct(product);
			    			
		        			if (product2.getChainId() != null && product2.getChainId() == liujuQX) {
		        				ChainStore store = new ChainStore();
		        				store.setChain_id(liujuXL);
		        				pb.setChainStore(store);
		        			}
			    			
							try {
							  loggerLocal.infoB(" 新插入条码 ： " + randomNum+ " " + pb);
							  productBarcodeDaoImpl.save(pb, true);
							} catch (Exception e){
								loggerLocal.errorB(" 新插入条码失败 ： " + randomNum+ " " + pb);
								e.printStackTrace();
							}
	    				} else {
	    					Integer colorId = product2.getColorId();
	    					Color color = null;
	                        if (colorId != null){
	                        	color = colorDaoImpl.get(colorId, true);
	                        	if (color == null){
	    		    				loggerLocal.errorB(" 无法导入 ,颜色导入出现问题 ： " + randomNum+ " " + product2);
	    		    				continue;
	                        	}
	                        }
	                        
	                        pb.setColor(color);
	                        pb.setCreateDate(product2.getCreateDate());
	                        pb.setStatus(product2.getStatus());
	                        
			    			if (pb.getChainStore() != null && pb.getChainStore().getChain_id() == liujuQX)
			    				pb.getChainStore().setChain_id(liujuXL);
			    			
							try {
		                        loggerLocal.infoB(" 更新条码 ： " + randomNum+ " " + pb);
		                        productBarcodeDaoImpl.update(pb, true);
							} catch (Exception e){
								loggerLocal.errorB(" 更新条码失败 ： "+ randomNum+ " " + pb);
								e.printStackTrace();
							}
	    				}

					 }
	    		}
	    	}	

		loggerLocal.infoB("----------- " + randomNum+ " "+new Date() + " 完成 每小时的基本信息导入 ");
		
		return response;
	}
	
}




