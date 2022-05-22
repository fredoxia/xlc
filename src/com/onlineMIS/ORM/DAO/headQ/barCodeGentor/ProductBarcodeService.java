package com.onlineMIS.ORM.DAO.headQ.barCodeGentor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.chainMgmt.ChainStoreGroupDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.headQ.inventory.HeadQInventoryStockDAOImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.HeadQInventoryStoreDAOImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.HeadQSalesHisDAOImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.HeadqInventoryService;
import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryOrderDAOImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryOrderProductDAOImpl;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Area;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.BarcodeImportTemplate;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.BarcodeTemplate;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.BarcodeUpdateTemplate;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Category;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.HeadQInputHelp;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.NumPerHand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcodeVO;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductUnit;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Size;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

import com.onlineMIS.ORM.entity.headQ.inventory.HeadQInventoryStore;
import com.onlineMIS.ORM.entity.headQ.inventory.HeadQSalesHistory;
import com.onlineMIS.ORM.entity.headQ.inventory.HeadQSalesHistoryId;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.ORM.entity.headQ.inventory.JinSuanOrderTemplate;
import com.onlineMIS.action.headQ.barCodeGentor.BarcodeGenBasicData;
import com.onlineMIS.action.headQ.barCodeGentor.ProductActionFormBean;
import com.onlineMIS.action.headQ.barCodeGentor.ProductActionUIBean;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.onlineMIS.sorter.SortYear;

@Service
public class ProductBarcodeService {
	@Autowired
	private ProductBarcodeDaoImpl productBarcodeDaoImpl;
	@Autowired
	private ProductDaoImpl productDaoImpl;

	@Autowired
    private InventoryOrderDAOImpl inventoryOrderDAOImpl;
	@Autowired
	private InventoryOrderProductDAOImpl inventoryOrderProductDAOImpl;

	@Autowired
	private HeadQSalesHisDAOImpl headQSalesHisDAOImpl;
	@Autowired
	private BrandDaoImpl brandDaoImpl;
	@Autowired
	private AreaDaoImpl areaDaoImpl;
	@Autowired
	private YearDaoImpl yearDaoImpl;
	@Autowired
	private QuarterDaoImpl quarterDaoImpl;
	@Autowired
	private CategoryDaoImpl categoryDaoImpl;
	@Autowired
	private ColorDaoImpl colorDaoImpl;
	@Autowired
	private SizeDaoImpl sizeDaoImpl;
	@Autowired
	private ProductUnitDaoImpl productUnitDaoImpl;
	@Autowired
	private NumPerHandDaoImpl numPerHandDaoImpl;
	@Autowired
	private HeadQInputHelpDaoImpl headQInputHelpDaoImpl;
	@Autowired
	private ChainStoreGroupDaoImpl chainStoreGroupDaoImpl;
	@Autowired
	private HeadQInventoryStockDAOImpl headQInventoryStockDAOImpl;
	@Autowired
	private HeadQInventoryStoreDAOImpl headQInventoryStoreDAOImpl;

	/**
	 * 只取出总部需要的数据
	 * @return
	 */
	public BarcodeGenBasicData prepareBarcodeSourceDataForHead(){

		List<Area> areaList =  areaDaoImpl.getAll(true);    

		List<Year> yearList =  yearDaoImpl.getAll(true); 
		Collections.sort(yearList, new SortYear());

//		List<Brand> brandList =  brandDaoImpl.getAll(true);    
//		Collections.sort(brandList, new SortBrand());

		List<Category> categoryList =  categoryDaoImpl.getHeadCategry(true); 
		
		List<Quarter> quarterList =  quarterDaoImpl.getAll(true);  

//		List<Color> colorList = colorDaoImpl.getAll(false);
		
//		List<Size> sizeList = sizeDaoImpl.getAll(true);

		List<ProductUnit> unitList = productUnitDaoImpl.getAll(true);

		List<NumPerHand> numPerHandList = numPerHandDaoImpl.getAll(true);
		
		BarcodeGenBasicData basicData = new BarcodeGenBasicData();
		basicData.setAreaList(areaList);
		basicData.setYearList(yearList);
//		basicData.setBrandList(brandList);
		basicData.setCategoryList(categoryList);
		basicData.setQuarterList(quarterList);
		basicData.setUnitList(unitList);
		basicData.setNumPerHandList(numPerHandList);
//		basicData.setColorList(colorList);
//		basicData.setSizeList(sizeList);

		return basicData;
	}

	/**
	 * 取出所有表数据包括总部和连锁店
	 * @return
	 */
	public BarcodeGenBasicData prepareBarcodeSourceData(){

		List<Area> areaList =  areaDaoImpl.getAll(true);    

		List<Year> yearList =  yearDaoImpl.getAll(true); 
		Collections.sort(yearList, new SortYear());

//		List<Brand> brandList =  brandDaoImpl.getAll(true);    
//		Collections.sort(brandList, new SortBrand());

		List<Category> categoryList =  categoryDaoImpl.getAll(true); 
		
		List<Quarter> quarterList =  quarterDaoImpl.getAll(true);  

		List<Color> colorList = colorDaoImpl.getAll(false);
		
		List<Size> sizeList = sizeDaoImpl.getAll(true);

		List<ProductUnit> unitList = productUnitDaoImpl.getAll(true);

		List<NumPerHand> numPerHandList = numPerHandDaoImpl.getAll(true);
		
		BarcodeGenBasicData basicData = new BarcodeGenBasicData();
		basicData.setAreaList(areaList);
		basicData.setYearList(yearList);
//		basicData.setBrandList(brandList);
		basicData.setCategoryList(categoryList);
		basicData.setQuarterList(quarterList);
		basicData.setUnitList(unitList);
		basicData.setNumPerHandList(numPerHandList);
		basicData.setColorList(colorList);
		basicData.setSizeList(sizeList);

		return basicData;
	}

  	class SortBrand implements java.util.Comparator<Brand>{
		@Override
		public int compare(Brand arg0, Brand arg1) {
		       int brand1 = 0;
		       int brand2 = 0;
		       
		       brand1 = arg0.getBrand_ID();
		       brand2 = arg1.getBrand_ID();

               return brand1 - brand2;
		 }
	}
	/**
	 * get the products by the similiar product code
	 * scope : -1 所有条码 （总部和连锁店做的条码）
	 *         >0 总部条码 + 当前连锁店和关联连锁店条码
	 *         0  只有总部条码
	 * @param productCode
	 * @return
	 */
	@Transactional
	public List<ProductBarcode> getProductsForSimiliarProductCode(String similiarProductCode, int scope, Pager pager) {
		boolean cache = false;

		//1. check the pager
		if (pager.getTotalResult() == 0){
			DetachedCriteria criteria = buildSearchProductCodeCriteria(similiarProductCode, scope);
			criteria.setProjection(Projections.rowCount());
			int totalRecord = Common_util.getProjectionSingleValue(productBarcodeDaoImpl.getByCriteriaProjection(criteria, false));
			pager.initialize(totalRecord);
		} else {
			pager.calFirstResult();
			cache = true;
		}
		
		DetachedCriteria criteria2 = buildSearchProductCodeCriteria(similiarProductCode, scope);

		return productBarcodeDaoImpl.getByCritera(criteria2, pager.getFirstResult(), pager.getRecordPerPage(),cache);
	}
	
	/**
	 * getProductsForSimiliarProductCode 功能的扩展
	 * @param productCode
	 * @param i
	 * @param pager
	 * @return
	 */
	public List<ProductBarcodeVO> getProductsForSimiliarProductCodeHeadq(String productCode, int scope, Pager pager) {
		List<ProductBarcode> products = getProductsForSimiliarProductCode(productCode, scope, pager);
		
		List<ProductBarcodeVO> productBarcodeVOs = new ArrayList<ProductBarcodeVO>();
		HeadQInventoryStore store = headQInventoryStoreDAOImpl.getDefaultStore();
		
		for (ProductBarcode pBarcode : products){
			int inventoryLevel = headQInventoryStockDAOImpl.getProductStock(pBarcode.getId(), store.getId(), true);
			ProductBarcodeVO vo = new ProductBarcodeVO(pBarcode);
			vo.setInventoryLevel(inventoryLevel);
			
			productBarcodeVOs.add(vo);
		}
		
		return productBarcodeVOs;
	}
	

	private DetachedCriteria buildSearchProductCodeCriteria(String productCode, int scope){
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductBarcode.class);
		DetachedCriteria productCriteria = criteria.createCriteria("product");
		
		if (scope == 0)
			criteria.add(Restrictions.isNull("chainStore"));
		else if (scope > 0) {
			List<ChainStore> chainStores = chainStoreGroupDaoImpl.getChainGroupStoreList(scope, null, Common_util.CHAIN_ACCESS_LEVEL_3);
			Set<Integer> chainIds = new HashSet<Integer>();
			for (ChainStore chain: chainStores){
				chainIds.add(chain.getChain_id());
			}
			
			criteria.add(Restrictions.or(Restrictions.isNull("chainStore"), Restrictions.in("chainStore.chain_id",chainIds)));
		}
			
		
		productCriteria.add(Restrictions.like("productCode", productCode, MatchMode.ANYWHERE));
		criteria.add(Restrictions.eq("status", ProductBarcode.STATUS_OK));
		
		productCriteria.addOrder(Order.desc("year.year_ID"));
		productCriteria.addOrder(Order.desc("quarter.quarter_ID"));
		productCriteria.addOrder(Order.desc("productCode"));
		

		return criteria;
	}
	
	/**
	 * 1. 总部不能使用连锁店品牌
	 * 2. 总部不能使用连锁店种类
	 * 2.检查serial number如果填写
	 */
	@Transactional
	public Response checkErrorBeforeGenerateBarcode(Product product) {
		Response response = new Response();
		
		String serialNum = product.getSerialNum();
		String productCode = product.getProductCode();

//@todo: 以后扩展用
//		ProductsMS productsMS = productsMSDAOImpl.getBySerialNum(serialNum);
//		if (productsMS == null){
//			response.setReturnCode(Response.FAIL);
//			response.setMessage("无法在精算中找到对应编码的商品资料");
//			return response;
//		} else {
//			Product product2 = productDaoImpl.getBySerialNum(serialNum);
//			if (product2 != null){
//				response.setReturnCode(Response.FAIL);
//				response.setMessage("此编码已经存在于,条码系统中");
//				return response;
//			} else {
		//1. check the serial number valid in the product table
		
		Brand brand = brandDaoImpl.get(product.getBrand().getBrand_ID(), true);
		if (brand.getChainStore() != null){
			response.setFail("总部条码不能使用连锁店品牌 : " + brand.getBrand_Name());
			return response;
		}
		
		Category category = categoryDaoImpl.get(product.getCategory().getCategory_ID(),true);	
		if (category.getChainId() == Category.TYPE_CHAIN){
			response.setFail("总部条码不能使用连锁店种类 : " + category.getCategory_Name());
			return response;
		}
		
		if (serialNum != null && !serialNum.trim().equals("")){
			Product product2 = productDaoImpl.getBySerialNum(serialNum, null);
			if (product2 == null){
				response.setReturnCode(Response.FAIL);
				response.setMessage("此商品编码不存在于条码系统中");
				return response;
			} else if (!product.equals(product2)) {
				response.setReturnCode(Response.FAIL);
				response.setMessage("此商品编码的基础资料和以前所建基础资料不同");
				return response;
			} 
		} else {
		
			List<Product> products = productDaoImpl.getProductsByProductCode(productCode,null);
	
			if (products != null && products.size() >0){
				for (Product product2 : products)
					productDaoImpl.initialize(product2);
				
				response.setReturnCode(Response.WARNING);
				response.setMessage("相同货号已经存在");
				response.setReturnValue(products);
				return response;
			}
		}
//			}
//		}
		
		response.setReturnCode(Response.SUCCESS);
		return response;
	}


	
	/**
	 * this function is the headq scan the product information from sales order
	 * @param barcode
	 * @param client_id
	 * @param skipJinsuan 0 : not skip, 1: skip
	 * @return
	 */
	@Transactional
	public ProductBarcode scanProductsByBarcodeHeadq(String barcode, int client_id) {
		ProductBarcode productBarcode = productBarcodeDaoImpl.getByBarcode(barcode);
		if (productBarcode!= null && productBarcode.getStatus() == ProductBarcode.STATUS_OK){

				Product product = productBarcode.getProduct();
				
				
				//1. get the qbefore from the SQLServer
				final String barcode_final = barcode;
				final int client_id_final = client_id;
				int qBefore = inventoryOrderDAOImpl.getQuantityBefore(barcode_final,client_id_final);
				productBarcode.setBoughtBefore(qBefore);
				
				int inventoryLevel = headQInventoryStockDAOImpl.getProductStock(productBarcode.getId(), headQInventoryStoreDAOImpl.getDefaultStore().getId(), true);
				productBarcode.setInventoryLevel(inventoryLevel);
			    
			    //2. get the sales history information
			    int productId = productBarcode.getId();
				DetachedCriteria criteria_history = DetachedCriteria.forClass(HeadQSalesHistory.class,"salesHistory");
				criteria_history.add(Restrictions.eq("salesHistory.id.productId", productId));
				criteria_history.add(Restrictions.eq("salesHistory.id.clientId", client_id));
			    List<HeadQSalesHistory> salesHistories = headQSalesHisDAOImpl.getByCritera(criteria_history, true);
			    if (salesHistories != null && salesHistories.size()!=0){
			    	HeadQSalesHistory headQSalesHistory = salesHistories.get(0);
			    	double salesPriceSelected = headQSalesHistory.getSalePriceSelected();
			    	double discountHistory = headQSalesHistory.getDiscount();
			    	double wholePrice = headQSalesHistory.getWholePrice();
			    	
			    	if (salesPriceSelected>0 && discountHistory>0 && wholePrice>0){
			    		product.setLastChoosePrice(salesPriceSelected);
			    		product.setLastInputPrice(wholePrice);
			    		product.setDiscount(discountHistory);
			    		productDaoImpl.evict(product);
			    	}
			    }
	    }

		return productBarcode;
	}
	
	public ProductBarcode getProductsByBarcode(String barcode) {

		ProductBarcode productBarcode =  productBarcodeDaoImpl.getByBarcode(barcode);
		
		return productBarcode;
	}
	
	
	/**
	 * PDA获取产品信息
	 * @param barcode
	 * @param clientId
	 * @return
	 */
	@Transactional
	public ProductBarcode getPDAProductsByBarcodeCalWholePrice(String barcode, int clientId) {
		//1. get the product barcode
		ProductBarcode productBarcode = getProductsByBarcode(barcode);
		if (productBarcode != null){
			productBarcode.getProduct().setLastChoosePrice(productBarcodeDaoImpl.getWholeSalePrice(productBarcode));

			//2. get the qbefore from the SQLServer
		    final String barcode_final = barcode;
		    final int client_id_final = clientId;
		    int qBefore = inventoryOrderDAOImpl.getQuantityBefore(barcode_final,client_id_final);
		    productBarcode.setBoughtBefore(qBefore);
		}
	    
		return productBarcode;
	}

	public List<ProductBarcode> getBarcodesFromCriteria(ProductBarcode productBarcode, List<Integer> brandIds, Date startDate, Date endDate, String createDate  ) {
		List<ProductBarcode> barcodes = new ArrayList<ProductBarcode>();

			DetachedCriteria productBarcodeCriteria = DetachedCriteria.forClass(ProductBarcode.class);
			DetachedCriteria productCriteria = productBarcodeCriteria.createCriteria("product");
			
			if (productBarcode.getChainStore() != null && productBarcode.getChainStore().getChain_id() == 99)
				productBarcodeCriteria.add(Restrictions.isNull("chainStore"));
			else if (productBarcode.getChainStore() != null && productBarcode.getChainStore().getChain_id() == 0)
				productBarcodeCriteria.add(Restrictions.isNotNull("chainStore"));
            
			if (!productBarcode.getProduct().getProductCode().trim().equals(""))
			    productCriteria.add(Restrictions.like("productCode", productBarcode.getProduct().getProductCode(),MatchMode.ANYWHERE));
	        if (createDate != null && !createDate.equals("")){
	        	java.util.Date endDate2 = new java.util.Date(endDate.getTime());
	        	endDate2.setHours(23);
	        	endDate2.setMinutes(59);
	        	endDate2.setSeconds(59);
				productBarcodeCriteria.add(Restrictions.between("createDate",startDate,endDate2));
	        }if (!productBarcode.getBarcode().trim().equals(""))
	        	productBarcodeCriteria.add(Restrictions.eq("barcode", productBarcode.getBarcode()));
	        
	//        DetachedCriteria sequenceCriteria = productCriteria.createCriteria("productSequence");
			if (productBarcode.getProduct().getArea().getArea_ID() != 0 )
				productCriteria.add(Restrictions.eq("area.area_ID", productBarcode.getProduct().getArea().getArea_ID()));
			if (productBarcode.getProduct().getYear().getYear_ID() != 0)
				productCriteria.add(Restrictions.eq("year.year_ID", productBarcode.getProduct().getYear().getYear_ID()));
			if (productBarcode.getProduct().getQuarter().getQuarter_ID() != 0)
				productCriteria.add(Restrictions.eq("quarter.quarter_ID", productBarcode.getProduct().getQuarter().getQuarter_ID()));
			if (brandIds != null && brandIds.size() != 0){
				if (brandIds.size() == 1)
				   productCriteria.add(Restrictions.eq("brand.brand_ID", brandIds.get(0)));
				else 
					productCriteria.add(Restrictions.in("brand.brand_ID", brandIds));
			}
			if (productBarcode.getProduct().getCategory().getCategory_ID() != 0)
				productCriteria.add(Restrictions.eq("category.category_ID", productBarcode.getProduct().getCategory().getCategory_ID()));
	
			productBarcodeCriteria.add(Restrictions.eq("status", ProductBarcode.STATUS_OK));
			barcodes =  productBarcodeDaoImpl.getByCritera(productCriteria,true);

		return barcodes;
	}

//	private void initializeProductBarcode(List<ProductBarcode> barcodes) {
//		for (ProductBarcode productBarcode: barcodes)
//			productBarcodeDaoImpl.initialize(productBarcode);
//	}

	/**
	 * to delte the product barcode by id
	 * @param productBarcodeId
	 * @return
	 */
	public void deleteProductBarcode(int productBarcodeId) {
		ProductBarcode pb = productBarcodeDaoImpl.get(productBarcodeId, true);
		pb.setCreateDate(Common_util.getToday());
		pb.setStatus(ProductBarcode.STATUS_DELETE);
        productBarcodeDaoImpl.update(pb, true);
	}

	@Transactional
	public Response updateProduct(ProductBarcode productBarcode) {
		Response response = new Response();
		
        Product product = productDaoImpl.get(productBarcode.getProduct().getProductId(), true);
        if (product == null){
        	loggerLocal.info("Fail to get the product of productId : " + productBarcode.getProduct().getProductId());
            response.setFail("无法找到更新的产品");
        } else {
        	ChainStore chainStore = product.getChainStore();
        	if (chainStore != null){
        		response.setFail("总部不能修改连锁店条码");
        		return response;
        	}
        	
    		Brand brand = brandDaoImpl.get(product.getBrand().getBrand_ID(), true);
    		if (brand.getChainStore() != null){
    			response.setFail("总部条码不能使用连锁店品牌 : " + brand.getBrand_Name());
    			return response;
    		} 
    		
        	Product newProduct = productBarcode.getProduct();
        	product.setNumPerHand(newProduct.getNumPerHand());
        	product.setProductCode(newProduct.getProductCode());
        	product.setSalesPrice(newProduct.getSalesPrice());
        	product.setRecCost(newProduct.getRecCost());
        	//product.setRecCost2(newProduct.getRecCost2());
        	product.setWholeSalePrice(newProduct.getWholeSalePrice());
        	product.setWholeSalePrice2(newProduct.getWholeSalePrice2());
        	product.setWholeSalePrice3(newProduct.getWholeSalePrice3());
        	product.setUnit(newProduct.getUnit());
        	product.setSalesPriceFactory(newProduct.getSalesPriceFactory());
        	product.setCategory(newProduct.getCategory());
        	product.setYear(newProduct.getYear());
        	product.setQuarter(newProduct.getQuarter());
        	product.setBrand(newProduct.getBrand());
        	double discount = newProduct.getDiscount();
        	if (discount <= 0 || discount >1)
        		product.setDiscount(1);
        	else
        	    product.setDiscount(newProduct.getDiscount());
        	product.setCreateDate(Common_util.getLastUpdateTimeInterval());
        	
//        	String gender = newProduct.getGender();
//        	if (gender.equals(""))
//        		product.setGender(null);
//        	else 
//        		product.setGender(newProduct.getGender());
//        	
//        	Integer sizeMin = newProduct.getSizeMin();
//        	if (sizeMin == null ||sizeMin == 0)
//        		product.setSizeMin(null);
//        	else 
//        		product.setSizeMin(sizeMin);
//        	
//        	Integer sizeMax = newProduct.getSizeMax();
//        	if (sizeMax == null ||sizeMax == 0)
//        		product.setSizeMax(null);
//        	else 
//        		product.setSizeMax(sizeMax);
//        	
//        	String sizeRange = newProduct.getSizeRange();
//        	if (sizeRange.equals(""))
//        		product.setSizeRange(null);
//        	else 
//        		product.setSizeRange(sizeRange);

        	productDaoImpl.saveOrUpdate(product,true);
        	
        	List<ProductBarcode> pbs = productBarcodeDaoImpl.getBarcodeFromProduct(product.getProductId());
        	for (ProductBarcode pb: pbs){
        		pb.setCreateDate(Common_util.getLastUpdateTimeInterval());
        		productBarcodeDaoImpl.update(pb, true);
        	}
        }
 
		return response;
	}

	/**
	 * to check whether the barcode is used or not
	 * @param barcode
	 * @return
	 */
	public boolean checkBarcodeInOrder(int productBarcodeId) {
		
		int count = inventoryOrderProductDAOImpl.checkBarcodeInOrder(productBarcodeId);
		
		if (count > 0)
		   return true;
		else
			return false;
	}

	/**
	 * to generate the barcode excel report which is to send to 
	 * @param selectedBarcodes
	 * @param templatePath
	 * @return
	 */
	@Transactional
	public Map<String, Object> generateExcelReport(
			List<String> selectedBarcodes, String templatePath) {
		Map<String,Object> returnMap=new HashMap<String, Object>();   

        
		ByteArrayInputStream byteArrayInputStream;   
		try {     
			HSSFWorkbook wb = null;   
			
			//to get the order information from database
			List<ProductBarcode> products = new ArrayList<ProductBarcode>();
			
			if (selectedBarcodes != null && selectedBarcodes.size()>0){
				DetachedCriteria productCriteria = DetachedCriteria.forClass(ProductBarcode.class,"p");
				productCriteria.add(Restrictions.in("p.barcode", selectedBarcodes));
		        
				products = productBarcodeDaoImpl.getByCritera(productCriteria,true);
		    }
			
//			initializeProductBarcode(products);
			
			BarcodeTemplate barcodeTemplate = new BarcodeTemplate(products, templatePath);
			
			wb = barcodeTemplate.process();

			ByteArrayOutputStream os = new ByteArrayOutputStream();   
			try {   
			    wb.write(os);   
			} catch (IOException e) {   
		        e.printStackTrace();   
		    }   
		    byte[] content = os.toByteArray();   
		    byteArrayInputStream = new ByteArrayInputStream(content);   
		    returnMap.put("stream", byteArrayInputStream);   
         
		    return returnMap;   
		 } catch (Exception ex) {   
			 loggerLocal.error(ex);    
		 }   
		return null;   
	}

	

	/**
	 * to generate the product and the barcodes
	 * @param barCode
	 * @throws Exception 
	 */
	@Transactional
	public Response saveProduct(Product product, List<Integer> colors, List<Integer> sizes) {
		Response response = new Response();
		
		String serialNum = product.getSerialNum();

		/**
		 * 3.0 if it is existing product, no need to save information again, save the product information
		 */
		if (serialNum == null || serialNum.trim().equals("")){
	    	double discount = product.getDiscount();
	    	if (discount <= 0 || discount >1)
	    		product.setDiscount(1);
			productDaoImpl.save(product, true);
			product.setSerialNum(String.valueOf(product.getProductId()+50000000));
			productDaoImpl.update(product, true);
		} else {
			product = productDaoImpl.getBySerialNum(serialNum,null);
		}
				
		/**
		 * 4.0 generate the barcode
		 */
		generateProductBarcodes(product, colors, sizes);
		
		/**
		 * 5.0 保存year 和quarter以备以后使用
		 */
		int yearId = product.getYear().getYear_ID();
		int quarterId = product.getQuarter().getQuarter_ID();
		HeadQInputHelp headQInputHelp = headQInputHelpDaoImpl.getCreateProductHelp();
		if (headQInputHelp != null){
			headQInputHelp.setYear(yearId);
			headQInputHelp.setQuarter(quarterId);
			headQInputHelpDaoImpl.saveOrUpdate(headQInputHelp, true);
		} else {
			headQInputHelp = new HeadQInputHelp();
			headQInputHelp.setYear(yearId);
			headQInputHelp.setQuarter(quarterId);
			headQInputHelpDaoImpl.save(headQInputHelp, true);
		}

		response.setReturnCode(Response.SUCCESS);
		response.setMessage("成功生成条形码");
		
		return response;
	}
	
	/**
      * 5.0 get the same group product infromation
    */
	@Transactional
	public List<ProductBarcode> getSameGroupProductBarcodes(Product product){

		List<ProductBarcode> barcodes = productBarcodeDaoImpl.getProductBracodeFromSameGroup(product,null);
		for (ProductBarcode barcode: barcodes)
		      productBarcodeDaoImpl.initialize(barcode);

		return barcodes;
	}

	/**
	 * 
	 * @param product
	 * @param colors
	 * @param sizes
	 */
	private void generateProductBarcodes(Product product, List<Integer> colorIds,
			List<Integer> sizeIds) {
		List<Color> colors = new ArrayList<Color>();
		List<Size> sizes = new ArrayList<Size>();
		
		if (colorIds == null || colorIds.size()==0)
			colors.add(null);
		else{
			for (Integer colorId: colorIds){
				if (colorId != 0)
					colors.add(new Color(colorId));
			}
		}
		
		if (sizeIds == null || sizeIds.size() == 0)
			sizes.add(null);
		else {
			for (Integer sizeId: sizeIds){
				if (sizeId != 0)
					sizes.add(new Size(sizeId));
			}
		}
			
		List<ProductBarcode> productBarcodes = productBarcodeDaoImpl.getBarcodeFromProduct(product.getProductId());
		List<ProductBarcode> deletedProductBarcodes = new ArrayList<ProductBarcode>();
		for (ProductBarcode barcode : productBarcodes){
			if (barcode.getStatus() == ProductBarcode.STATUS_DELETE)
				deletedProductBarcodes.add(barcode);
		}
		
		for (Color color: colors){
			for (Size size: sizes){	
				//save the productBarcode information
				ProductBarcode productBarcode = new ProductBarcode(product, color, size);
				if (productBarcodes.contains(productBarcode)){
					if (deletedProductBarcodes.contains(productBarcode)){
						for (ProductBarcode pBarcode : deletedProductBarcodes){
							if (pBarcode.equals(productBarcode)){
								pBarcode.setStatus(ProductBarcode.STATUS_OK);
								productBarcodeDaoImpl.update(pBarcode, true);
							}
						}
					} 
				} else {
						productBarcodeDaoImpl.save(productBarcode, true);
						
						//generate the barcode
						int id = productBarcode.getId();
						String barcode = generateBarcode(id, null);
						productBarcode.setBarcode(barcode);
						productBarcodeDaoImpl.update(productBarcode, true);
				}
			}
		}	
		
	}

	
	
	/**
	 * logic of barcode
	 * odd total = odd number  * 3
	 * even total = even number
	 * total = odd total + even total
	 * check number = 10 - total % 10
	 * barcode = barcode11 + check number
	 * 千禧 1开头
	 * 千禧客户9开头
	 * 喜乐3开头
	 * 喜乐客户7开头
	 * @param barCode
	 * @return
	 */
	public final static String generateBarcode(int sequenceId, ChainStore chainStore){
		StringBuffer prefix = null;
		
		//自己做的条码从3开始
		if (chainStore == null)
			prefix = new StringBuffer("3");
		else 
		//连锁店条码
			prefix = new StringBuffer("7");

		String productIdS = String.valueOf(sequenceId);
		int prefixLength = 11 - productIdS.length();

		
		prefixLength = prefixLength - prefix.length();
		//for the others add 0 or random number to the barcode
		Random random = new Random(new Date().getTime());
		for (int i = 0;  i < prefixLength; i++){
			//for the first two digits, we need the random number
			if (i < 2)
				prefix.append(random.nextInt(10));
			else 
			    prefix.append("0");
		}
		
		productIdS = prefix.toString() + productIdS;

        char[] codeArray  =productIdS.toCharArray();
        
        List<Integer> intArray = new ArrayList<Integer>();
        for (char code: codeArray){
      	  intArray.add(Integer.parseInt(String.valueOf(code)));
        }
        int totalOfOdd = 0;
        for (int i =0; i <intArray.size(); i=i+2){
      	  totalOfOdd+=intArray.get(i);
        }
        totalOfOdd *= 3;
        
        int totalOfEven = 0;
        for (int i =1; i <intArray.size(); i=i+2){
      	  totalOfEven+=intArray.get(i);
        }
        
        int total = totalOfEven + totalOfOdd;
        
        int remain = total % 10;
        
        if (remain != 0)
      	  remain = 10 - remain;
		
		
		return productIdS + remain;
	}

	/**
	 * to get the product sales information from sales history
	 * @param barcode
	 * @param client_id
	 * @return
	 */
	public ProductBarcode getProductFromSalesHistory(String barcode,
			int client_id) {
		ProductBarcode productBarcode =  productBarcodeDaoImpl.getByBarcode(barcode);
		
		if (productBarcode != null){
			Product product = productBarcode.getProduct();
			int productId = productBarcode.getId();
			
			int inventoryLevel = headQInventoryStockDAOImpl.getProductStock(productBarcode.getId(), headQInventoryStoreDAOImpl.getDefaultStore().getId(), true);
			productBarcode.setInventoryLevel(inventoryLevel);
		    
			
			HeadQSalesHistoryId headQSalesHistoryId = new HeadQSalesHistoryId(productId, client_id);
			HeadQSalesHistory salesHistory = headQSalesHisDAOImpl.get(headQSalesHistoryId, true);
			
			if (salesHistory != null){
				product.setRecCost(salesHistory.getRecCost());
				product.setSalesPrice(salesHistory.getSalesPrice());
				product.setDiscount(salesHistory.getDiscount());
				product.setLastChoosePrice(salesHistory.getSalePriceSelected());
				product.setLastInputPrice(salesHistory.getWholePrice());
			} else {
				product.setRecCost(0);
				product.setSalesPrice(0);
				product.setLastInputPrice(0);
				product.setDiscount(0);
			}
		    
			return productBarcode;
		} else 
		   return productBarcode;
	}

	/**
	 * this function is to generate the Jinsuan excel report
	 * @param selectedBarcodes
	 * @param string
	 * @return
	 */
	@Transactional
	public Map<String, Object> generateJinsuanExcelReport(
			Set<String> selectedBarcodes, String templatePosition) {
		List<ProductBarcode> products = productBarcodeDaoImpl.getProductBarcodes(selectedBarcodes);
		
//		initializeProductBarcode(products);
		
		InventoryOrder order = new InventoryOrder();
		Set<InventoryOrderProduct> inventoryOrderProducts = order.getProduct_Set();
		
		for (ProductBarcode product: products){
			InventoryOrderProduct orderProduct = new InventoryOrderProduct();
			orderProduct.setProductBarcode(product);
			
			inventoryOrderProducts.add(orderProduct);
		}
		
		Map<String,Object> returnMap=new HashMap<String, Object>();   

        
		ByteArrayInputStream byteArrayInputStream;   
		try {     
			HSSFWorkbook wb = null;   
			
			//to get the order information from database
			JinSuanOrderTemplate orderTemplate = new JinSuanOrderTemplate(order, templatePosition);
			
			wb = orderTemplate.process();

			ByteArrayOutputStream os = new ByteArrayOutputStream();   
			try {   
			    wb.write(os);   
			} catch (IOException e) {   
				loggerLocal.error(e);
		    }   
		    byte[] content = os.toByteArray();   
		    byteArrayInputStream = new ByteArrayInputStream(content);   
		    returnMap.put("stream", byteArrayInputStream);   
         
		    return returnMap;   
		 } catch (Exception ex) {   
			 loggerLocal.error(ex);
		 }   
		return null;
	}

	/**
	 * search the brands by the brandName
	 * @param brand_Name
	 */
	public List<Brand> searchBrands(String brand_Name) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Brand.class);
		criteria.add(Restrictions.like("brand_Name", "%" + brand_Name + "%"));
		criteria.addOrder(Order.asc("brand_Name"));

		
		return brandDaoImpl.getByCritera(criteria, true);
		
	}

	
    public static JSONObject transferProductBarcode(Map<String,Object> jsonMap, JsonConfig jsonConfig){
    	if (jsonConfig == null)
		   jsonConfig = new JsonConfig();
    	
		JSONObject jsonObject = new JSONObject();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
		try{
		   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
		} catch (Exception e){
			loggerLocal.error(e);
		}
		return jsonObject;
    }
    
	/**
	 * 转化product barcode 成 vo
	 * @param barcode_org
	 * @return
	 */
    public Map formatProductBarcodeVO(List<ProductBarcode> barcode_org){
    	Map dataMap = new HashMap();
    	List<ProductBarcodeVO> barcodeVOs = new ArrayList<ProductBarcodeVO>();
    	if (barcode_org != null){
    		for (ProductBarcode barcode : barcode_org){
    			ProductBarcodeVO productBarcodeVO = new ProductBarcodeVO(barcode);
    			barcodeVOs.add(productBarcodeVO);
    		}
    	}
    	
    	dataMap.put("rows", barcodeVOs);
    	return dataMap;
    }

    /**
     * to search color
     * @param colorNames
     * @return
     */
	public List<Color> searchColors(String colorNames) {
		List<Color> colors = new ArrayList<Color>();
		
        if (colorNames != null && !colorNames.trim().equals("")){
        	String[] colorArray = colorNames.split("-");
        	List<String> colorNameList = Arrays.asList(colorArray);
        	
        	colors = colorDaoImpl.getColors(colorNameList);
        }
		return colors;
	}

	/**
	 * 通过serial number获取产品信息
	 * @param serialNum
	 * @return
	 */
	public Response getProductInforBySerialNum(String serialNum) {
		Response response = new Response();

		Product product = productDaoImpl.getBySerialNum(serialNum,null);
		
		if (product == null){
			response.setMessage("找不到产品信息");
			response.setReturnCode(Response.FAIL);
			return response;
		}
		
		int productId = product.getProductId();	
		List<ProductBarcode> productBarcodes = productBarcodeDaoImpl.getBarcodeFromProduct(productId);
		  
		String colors = "";
		if (productBarcodes != null && productBarcodes.size() > 0){
			for (ProductBarcode productBarcode: productBarcodes)
				if (productBarcode.getColor() != null)
				    colors += productBarcode.getColor().getName() + " ";
		}
		
		Map dataMap = new HashMap<String, Object>();
		dataMap.put("product", product);
		dataMap.put("color", colors);
		
		response.setReturnValue(dataMap);
		response.setReturnCode(Response.SUCCESS);
		
		return response;
	}

	public void prepareCreateProductUIForm(ProductActionFormBean formBean) {
		HeadQInputHelp headQInputHelp = headQInputHelpDaoImpl.getCreateProductHelp();
		if (headQInputHelp != null){
			formBean.getProductBarcode().getProduct().getYear().setYear_ID(headQInputHelp.getYear());
			formBean.getProductBarcode().getProduct().getQuarter().setQuarter_ID(headQInputHelp.getQuarter());
		}
		
	}

	/**
	 * 批量删除条码
	 * @param inventory
	 */
	@Transactional
	public Response batchDeleteBarcode(File inventory) {
		Response response = new Response();
		
		BarcodeTemplate barcodeTemplate = new BarcodeTemplate(inventory);
		List<String> barcodes = barcodeTemplate.proccess();
		
		if (barcodes == null){
			response.setFail("读取文件出现问题,请检查文件系统");
		} else {
			if (barcodes.size() > 0){
				String queryString = "UPDATE ProductBarcode p SET p.status = "+ProductBarcode.STATUS_DELETE+" where p.barcode in ";
				
				String variables = "(" + barcodes.get(0) ;
				
				for (int i =1; i < barcodes.size(); i++){
					variables += "," + barcodes.get(i);
				}
				
				queryString += variables + ")";
				
		        int numberOfDelete =  productBarcodeDaoImpl.executeHQLUpdateDelete(queryString, null, true);
		        
		        response.setSuccess("文件上需要删除 " + barcodes.size() + " 个条码. 实际删除了 " + numberOfDelete+ " 个条码");
		        
			} else {
				response.setFail("没有条码需要删除，请检查文件");
			}
		}
		
		return response;
		
	}

	/**
	 * 批量新增条码
	 * @param inventory
	 */
	@Transactional
	public Response batchInsertBarcode(File inventory) {
		Response response = new Response();

		
		BarcodeImportTemplate barcodeTemplate = new BarcodeImportTemplate(inventory);
		barcodeTemplate.proccess(areaDaoImpl,yearDaoImpl, quarterDaoImpl, brandDaoImpl,categoryDaoImpl, colorDaoImpl, productUnitDaoImpl, productDaoImpl);
		
		if (!barcodeTemplate.isSuccess()){
			response.setFail(barcodeTemplate.getValidateMsg());
		} else {
			List<Object> wsData = barcodeTemplate.getWsData();
			if (wsData == null|| wsData.size()==0){
				response.setFail("这个表格无数据");
			} else {
				
				for (Object rowData: wsData){
					List<Object> rowDataList = (List<Object>)rowData;
					Product product2 = (Product)rowDataList.get(0);
					Color color = (Color)rowDataList.get(1);
					String barcode = (String)rowDataList.get(2);
					
					//获取产品信息
					String serialNum = product2.getSerialNum();
					Product productOriginal = productDaoImpl.getBySerialNum(serialNum, null);
					
					if (productOriginal != null){
						productOriginal.setBrand(product2.getBrand());
						productOriginal.setYear(product2.getYear());
						productOriginal.setQuarter(product2.getQuarter());
						productOriginal.setCategory(product2.getCategory());
						productOriginal.setCreateDate(Common_util.getToday());
						productOriginal.setProductCode(product2.getProductCode());
						productOriginal.setDiscount(product2.getDiscount());
						productOriginal.setSalesPrice(product2.getSalesPrice());
						productOriginal.setWholeSalePrice(product2.getWholeSalePrice());
						productOriginal.setUnit(product2.getUnit());
						productOriginal.setNumPerHand(product2.getNumPerHand());
						productOriginal.setSalesPriceFactory(product2.getSalesPriceFactory());
						productOriginal.setRecCost(product2.getRecCost());
						product2 = productOriginal;
						productDaoImpl.evict(productOriginal);
					}  
						
					productDaoImpl.saveOrUpdate(product2, true);

					
					ProductBarcode pb = productBarcodeDaoImpl.getByBarcode(barcode);
					if (pb == null){
						pb = new ProductBarcode();
						pb.setColor(color);
						pb.setChainStore(null);
						pb.setCreateDate(Common_util.getToday());
						pb.setProduct(product2);
						pb.setBarcode(barcode);
						productBarcodeDaoImpl.save(pb, true);
					}
				}
		
				response.setSuccess("成功导入 "+ wsData.size() +"个 条码 . ");
			}
			
		}
		
		return response;
	}

	public void prepareBatchInsertBarcodeUI(ProductActionFormBean formBean,
			ProductActionUIBean uiBean) {
		List<Year> years = yearDaoImpl.getLatestYears();
		List<Quarter> quarters = quarterDaoImpl.getAll(true);
		
		uiBean.getBasicData().setQuarterList(quarters);
		uiBean.getBasicData().setYearList(years);
		
	}

	/**
	 * 进行批量条码修改
	 * @param inventory
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public Response batchUpdateBarcode(File inventory)  {
		Response response = new Response();
		
		try {
			BarcodeUpdateTemplate barcodeTemplate = new BarcodeUpdateTemplate(inventory);
			barcodeTemplate.proccess(productBarcodeDaoImpl, categoryDaoImpl, productUnitDaoImpl);
			
			if (!barcodeTemplate.isSuccess()){
				response.setFail(barcodeTemplate.getValidateMsg());
			} else {
				Set<Product> wsData = barcodeTemplate.getWsData();
				if (wsData == null|| wsData.size()==0){
					response.setFail("无法找到更新数据");
				} else {
					
					for (Product rowData: wsData){
						productDaoImpl.update(rowData, true);
					}
					response.setSuccess("成功更新 "+ wsData.size() +"个 产品信息。");
				}
			}
		} catch (Exception e){
			response.setFail("更新条码失败: " + e.getMessage());
		}
		
		return response;
	}


	
}
