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
public class HeadqBatchRptService {
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
		
		//1. 更新brand, 获取从千禧系统拿到的最大的id
	    String BRAND_MAX_NOW = "SELECT MAX(brand_ID) FROM Brand WHERE brand_ID < " + Brand.LOCAL_START_ID;
	    int brandMax = brandDaoImpl.executeHQLCount(BRAND_MAX_NOW, null, false);
	    
	    //获取千禧比这个大的
    	DetachedCriteria criteria = DetachedCriteria.forClass(com.onlineMIS.ORM.entity.headQ.qxbabydb.Brand2.class);
    	criteria.add(Restrictions.gt("brand_ID", brandMax));
    	
    	//刘菊连锁店条码问题
    	int liujuQX = Integer.parseInt(SystemParm.getParm("liujuChainStoreId"));
    	int liujuXL = Integer.parseInt(SystemParm.getParm("liujuXiLeStoreId"));
    	criteria.add(Restrictions.or(Restrictions.eq("chain_id", liujuQX), Restrictions.isNull("chain_id")));
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
    	
    	//2. 更新category
	    String CATEGORY_MAX_NOW = "SELECT MAX(category_ID) FROM Category";
	    int categoryMax = categoryDaoImpl.executeHQLCount(CATEGORY_MAX_NOW, null, false);
	    
	    //获取千禧比这个大的
    	DetachedCriteria criteria2 = DetachedCriteria.forClass(Category2.class);
    	criteria2.add(Restrictions.gt("category_ID", categoryMax));
    	List<Category2> categories =  categoryDaoImpl2.getByCritera(criteria2, false);
	  
    	if (categories != null && categories.size() > 0){
    		for (Category2 category2 : categories){
    			Category category = new Category();
    			BeanUtils.copyProperties(category2,category);
    			
    			try {
	    			loggerLocal.infoB(category.toString());
	    			categoryDaoImpl.save(category, true);
    			} catch (Exception e){
    				loggerLocal.errorB("导入错误 : " + randomNum+ " " + category);
    				e.printStackTrace();
    			}
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
    			try {
    				loggerLocal.infoB(color.toString());
    				colorDaoImpl.save(color, true);
    			} catch (Exception e){
    				loggerLocal.errorB("导入错误 : " + randomNum+ " " + color);
    				e.printStackTrace();
    			}
    		}
    	}	
    	

		//4.更新product unit
	    String PROD_UNIT_NOW = "SELECT MAX(id) FROM ProductUnit";
	    int productUnitMax = productUnitDaoImpl.executeHQLCount(PROD_UNIT_NOW, null, false);
	    
	    //获取千禧比这个大的
    	DetachedCriteria criteria5 = DetachedCriteria.forClass(ProductUnit2.class);
    	criteria5.add(Restrictions.gt("id", productUnitMax));

    	List<ProductUnit2> productUnits2 =  productUnitDaoImpl2.getByCritera(criteria5, false);
    	if (productUnits2 != null && productUnits2.size() > 0){
    		for (ProductUnit2 pu2 : productUnits2){
    			ProductUnit productUnit = new ProductUnit();
    			BeanUtils.copyProperties(pu2,productUnit);
    			try {
    				loggerLocal.infoB(productUnit.toString());
    				productUnitDaoImpl.save(productUnit, true);
    			} catch (Exception e){
    				loggerLocal.errorB("导入错误 : " + randomNum+ " " + productUnit);
    				e.printStackTrace();
    			}
    		}
    	}	 
    	
    	//5.更新year
	    String year_NOW = "SELECT MAX(year_ID) FROM Year";
	    int yearIdMax = yearDaoImpl.executeHQLCount(year_NOW, null, false);
	    
	    //获取千禧比这个大的
    	DetachedCriteria criteria6 = DetachedCriteria.forClass(Year2.class);
    	criteria6.add(Restrictions.gt("id", yearIdMax));

    	List<Year2> year2s =  yearDaoImpl2.getByCritera(criteria6, false);
    	if (year2s != null && year2s.size() > 0){
    		for (Year2 year2 : year2s){
    			Year year = new Year();
    			BeanUtils.copyProperties(year2,year);
    			try {
    				loggerLocal.infoB(year.toString());
    				yearDaoImpl.save(year, true);
    			} catch (Exception e){
    				loggerLocal.errorB("导入错误 : " + randomNum+ " " + year);
    				e.printStackTrace();
    			}
    		}
    	}	  
    	
    	//6.更新numPerHand
	    String numPerHand_NOW = "SELECT MAX(id) FROM NumPerHand";
	    int numPerHandIdMax = numPerHandDaoImpl.executeHQLCount(numPerHand_NOW, null, false);
	    
	    //获取千禧比这个大的
    	DetachedCriteria criteria7 = DetachedCriteria.forClass(NumPerHand2.class);
    	criteria7.add(Restrictions.gt("id", numPerHandIdMax));

    	List<NumPerHand2> numPerHand2s =  numPerHandDaoImpl2.getByCritera(criteria7, false);
    	if (numPerHand2s != null && numPerHand2s.size() > 0){
    		for (NumPerHand2 numPerHand2 : numPerHand2s){
    			NumPerHand numPerHand = new NumPerHand();
    			BeanUtils.copyProperties(numPerHand2,numPerHand);
    			try {
    			  loggerLocal.infoB(numPerHand.toString());
    			  numPerHandDaoImpl.save(numPerHand, true);
    			} catch (Exception e){
    				loggerLocal.errorB("导入错误 : " + randomNum+ " " + numPerHand);
    				e.printStackTrace();
    			}
    		}
    	}	
    	
     	//2. 更新product
	    String PRODUCT_MAX_NOW = "SELECT MAX(createDate) FROM Product WHERE LENGTH(serial_number) < 8";
	    List<Object> productMax = productDaoImpl.executeHQLSelect(PRODUCT_MAX_NOW, null,null, false);
	    Object maxObj = productMax.get(0);
	    if (maxObj != null){
	    	Timestamp maxTime = (Timestamp)maxObj;
	    	loggerLocal.infoB("Product date" + randomNum+ "  :  " + maxTime);
		    //获取千禧比这个大的
	    	DetachedCriteria criteria8 = DetachedCriteria.forClass(Product2.class);
	    	criteria8.add(Restrictions.gt("createDate", maxTime));
	    	criteria8.add(Restrictions.or(Restrictions.eq("chainId", liujuQX), Restrictions.isNull("chainId")));
	    	criteria8.addOrder(Order.asc("createDate"));
	    	List<Product2> products =  productDaoImpl2.getByCritera(criteria8, false);
		    
	    	
	    	if (products != null && products.size() > 0){
	    		loggerLocal.infoB("Product size" + randomNum+ "  :  " + products.size());
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
		    				loggerLocal.infoB("准备导入 " + randomNum+ "  :  " + product2.toString());
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
									loggerLocal.errorB(" ?????? 更新产品出现问题 :");
									loggerLocal.errorB(e);
									
									e.printStackTrace();
								}
							} else {
								try {
									loggerLocal.infoB("新建Product : " + randomNum+ " " + product.toString());
					    			
									product.setRecCost(0);
									productDaoImpl.save(product, true);
								} catch (Exception e){
									loggerLocal.errorB(" ?????? 新建产品出现问题 :");
									loggerLocal.errorB(e);
									
									e.printStackTrace();
								}
							}

		    			}
	    			}
	    		}
	    	}	
	    }
	    
	  	//3. 更新productBarcode
	    String PB_MAX_NOW = "SELECT MAX(createDate) FROM ProductBarcode WHERE barcode LIKE '1%' or barcode LIKE '9%'";
	    
	    List<Object> pbMax = productDaoImpl.executeHQLSelect(PB_MAX_NOW, null,null, false);
	    Object maxObjPB = pbMax.get(0);
	    loggerLocal.infoB("最大 createDate PB" + randomNum+ "  :  " + maxObjPB);
	    if (maxObjPB != null){
	    	Timestamp maxTime = (Timestamp)maxObjPB;
	    	
		    //获取千禧比这个大的
	    	DetachedCriteria criteria9 = DetachedCriteria.forClass(ProductBarcode2.class);
	    	criteria9.add(Restrictions.gt("createDate", maxTime));
	    	criteria9.add(Restrictions.or(Restrictions.eq("chainId", liujuQX), Restrictions.isNull("chainId")));
	    	criteria9.addOrder(Order.asc("createDate"));
	    	List<ProductBarcode2> products =  productBarcodeDaoImpl2.getByCritera(criteria9, false);
		    
	    	
	    	if (products != null && products.size() > 0){
	    		loggerLocal.infoB("PB size" + randomNum+ "  :  " + products.size());
	    		for (ProductBarcode2 product2 : products){
	    			
	    			//product 不存在就掠过
	    			String serialNum = String.valueOf(product2.getProductId());
	    			Product product = productDaoImpl.getBySerialNum(serialNum, null);
	    			
	    			if (product == null){
	    				loggerLocal.warnB("skip , 主产品信息没有找到  : " + randomNum+ " " + product2.toString());
	    				continue;
	    			} else {
	    				loggerLocal.warnB("准备导入 " + randomNum+ " " + product2.toString());
	    				String barcode2 = product2.getBarcode();
	    				ProductBarcode pb = productBarcodeDaoImpl.getByBarcode(barcode2, null);
	    				
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
	    }
		loggerLocal.infoB("----------- " + randomNum+ " "+new Date() + " 完成 每小时的基本信息导入 ");
		
		return response;

	}
	
}




