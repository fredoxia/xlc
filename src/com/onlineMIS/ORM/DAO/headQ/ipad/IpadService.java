package com.onlineMIS.ORM.DAO.headQ.ipad;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeService;
import com.onlineMIS.ORM.DAO.headQ.custMgmt.HeadQCustDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.HeadQInventoryStockDAOImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.HeadQInventoryStoreDAOImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryOrderDAOImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryOrderProductDAOImpl;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.custMgmt.HeadQCust;
import com.onlineMIS.ORM.entity.headQ.inventory.HeadQInventoryStock;
import com.onlineMIS.ORM.entity.headQ.inventory.HeadQInventoryStore;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.action.headQ.ipad.ProductBarcodeVO;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;

@Service
public class IpadService {

	
	@Autowired
	private ProductBarcodeDaoImpl productBarcodeDaoImpl;
	
	@Autowired
	private InventoryOrderDAOImpl inventoryOrderDAOImpl;
	
	@Autowired
	private InventoryOrderProductDAOImpl inventoryOrderProductDAOImpl;

	
	@Autowired
	private HeadQCustDaoImpl headQCustDaoImpl;
	
	@Autowired
	private HeadQInventoryStockDAOImpl headQInventoryStockDAOImpl;

	@Autowired
	private HeadQInventoryStoreDAOImpl headQInventoryStoreDAOImpl;
	/**
	 * when people type the pinyin, this service will search the client infroamtion from the jinsuan database
	 * @param pinyin
	 * @return
	 */
	public List<HeadQCust> getHeadqCust(String pinyin) {
		List<HeadQCust> custs = headQCustDaoImpl.getCustByPinyin(pinyin, HeadQCust.CustStatusEnum.GOOD.getKey());

		return custs;
	}
	
	/**
	 * in the ipad, people choose the cust
	 * @param clientId
	 * @return
	 */
	public HeadQCust getCustById(int clientId) {
		HeadQCust cust = null;
		if (clientId > 0)
		    cust = headQCustDaoImpl.get(clientId, true);
		return cust;
	}

	public Response searchByProductCode(String productCode, Integer clientId, Integer orderId) {
		productCode = productCode.replaceAll("\\.", "_");
		//System.out.println("-----------" + productCode);
		Response response = new Response();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ProductBarcode.class);
		DetachedCriteria productCriteria = criteria.createCriteria("product");
		
		productCriteria.add(Restrictions.like("productCode", productCode, MatchMode.ANYWHERE));
		productCriteria.add(Restrictions.isNull("chainStore"));

		productCriteria.addOrder(Order.desc("year.year_ID"));
		productCriteria.addOrder(Order.desc("quarter.quarter_ID"));
		productCriteria.addOrder(Order.desc("brand.brand_ID"));
		productCriteria.addOrder(Order.asc("productCode"));
		
		//1. 生成CurrentOrderProduct map
		List<ProductBarcode> productBarcodes = productBarcodeDaoImpl.getByCritera(criteria, 0, 15, true);
		if (productBarcodes == null || productBarcodes.size() == 0){
			response.setReturnValue(null);
		} else {
			List<ProductBarcodeVO> productBarcodeVOs = new ArrayList<ProductBarcodeVO>();
			for (ProductBarcode pb: productBarcodes){
				String barcode = pb.getBarcode();

                int inventory = headQInventoryStockDAOImpl.getProductStock(pb.getId(), HeadQInventoryStore.INVENTORY_STORE_DEFAULT_ID, true);
				
				int orderHis = inventoryOrderDAOImpl.getQuantityBefore(barcode, clientId);

				int orderCurrent = 0 ;
				if (orderId != null){
					InventoryOrderProduct orderProduct = inventoryOrderProductDAOImpl.getByOrderIdProductId(orderId.intValue(), pb.getId());
				    if(orderProduct != null)
					  orderCurrent = orderProduct.getQuantity();
				}
				
				
				ProductBarcodeVO productBarcodeVO = new ProductBarcodeVO(pb, inventory, orderHis, orderCurrent);
				productBarcodeVOs.add(productBarcodeVO);
			}
			response.setReturnValue(productBarcodeVOs);
		}
		
		return response;		
	}
//
	@Transactional
	public Response orderProduct(Object clientIdObj, Object orderIdObj, int pbId, int quantity, UserInfor orderScanner) {
		Response response = new Response();
		Map<String, Integer> result = new HashMap<String, Integer>();
	
		
		if (clientIdObj == null){
		   response.setFail("输入客户信息后再选择货品");
		   return response;
		}
		
		int client_id = Integer.valueOf(String.valueOf(clientIdObj));
		
		if (quantity > 0){
			if (orderIdObj == null){
				HeadQCust clientsMS = headQCustDaoImpl.get(client_id, true);
				
				InventoryOrder order = new InventoryOrder();
				order.setCust(clientsMS);
				
				order.setOrder_StartTime(Common_util.getToday());
				order.setOrder_Status(InventoryOrder.STATUS_PDA_DRAFT);
				order.setOrder_type(InventoryOrder.TYPE_SALES_ORDER_W);
				order.setPdaScanner(orderScanner);
				
				ProductBarcode pBarcode = productBarcodeDaoImpl.get(pbId, true);
				Product p = pBarcode.getProduct();
				
				InventoryOrderProduct inventoryOrderProduct= new InventoryOrderProduct();
				inventoryOrderProduct.setIndex(0);
				inventoryOrderProduct.setProductBarcode(pBarcode);
				inventoryOrderProduct.setQuantity(quantity * p.getNumPerHand());
				
				double wholePrice = ProductBarcodeDaoImpl.getWholeSalePrice(pBarcode);
				inventoryOrderProduct.setWholeSalePrice(wholePrice);
				
				double totalWholeSalePrice = wholePrice * p.getNumPerHand() * quantity;
				order.setTotalWholePrice((int)totalWholeSalePrice);
				order.setTotalQuantity(quantity * p.getNumPerHand());
				
				List<InventoryOrderProduct> orderProducts = new ArrayList<InventoryOrderProduct>();
				orderProducts.add(inventoryOrderProduct);
				
				order.setProduct_List(orderProducts);
				order.putListToSet();
				
				inventoryOrderDAOImpl.save(order, true);
				result.put("orderId", order.getOrder_ID());
				result.put("pbId", pBarcode.getId());
				result.put("cq", p.getNumPerHand());
				
				response.setReturnValue(result);
			} else {
				int orderId = Integer.valueOf(String.valueOf(orderIdObj));
				InventoryOrderProduct orderProduct = inventoryOrderProductDAOImpl.getByOrderIdProductId(orderId, pbId);
				if (orderProduct != null){
					ProductBarcode pBarcode = orderProduct.getProductBarcode();
					Product p = pBarcode.getProduct();
					
					int moreQ = p.getNumPerHand() * quantity;
					int newQ = moreQ + orderProduct.getQuantity();
					
					if (newQ ==0)
						inventoryOrderProductDAOImpl.delete(orderProduct, true);
					else {
						orderProduct.setQuantity(newQ);
						inventoryOrderProductDAOImpl.update(orderProduct, true);
					}
					
					InventoryOrder order = inventoryOrderDAOImpl.get(orderId, true);
					double wholePrice = ProductBarcodeDaoImpl.getWholeSalePrice(pBarcode);
					
					double totalWholeSalePrice = wholePrice * p.getNumPerHand() * quantity;
					order.setTotalWholePrice((int)(totalWholeSalePrice + order.getTotalWholePrice()));
					order.setTotalQuantity(moreQ + order.getTotalQuantity());
					inventoryOrderDAOImpl.save(order, true);
					
					result.put("orderId", orderId);
					result.put("pbId", pBarcode.getId());
					result.put("cq", newQ);
					
					response.setReturnValue(result);
					
				} else {
					InventoryOrder order = inventoryOrderDAOImpl.get(orderId, true);
					
					ProductBarcode pBarcode = productBarcodeDaoImpl.get(pbId, true);
					Product p = pBarcode.getProduct();
					
					InventoryOrderProduct inventoryOrderProduct= new InventoryOrderProduct();
					inventoryOrderProduct.setIndex(0);
					inventoryOrderProduct.setProductBarcode(pBarcode);
					inventoryOrderProduct.setQuantity(quantity * p.getNumPerHand());
					inventoryOrderProduct.setOrder(order);
					
					double wholePrice = ProductBarcodeDaoImpl.getWholeSalePrice(pBarcode);
					inventoryOrderProduct.setWholeSalePrice(wholePrice);
									
					inventoryOrderProductDAOImpl.saveOrUpdate(inventoryOrderProduct, true);
					
					
					double totalWholeSalePrice = wholePrice * p.getNumPerHand() * quantity;
					
					order.setTotalWholePrice((int)(totalWholeSalePrice + order.getTotalWholePrice()));
					order.setTotalQuantity(p.getNumPerHand() * quantity + order.getTotalQuantity());
					inventoryOrderDAOImpl.save(order, true);
					
					result.put("orderId", orderId);
					result.put("pbId", pBarcode.getId());
					result.put("cq", p.getNumPerHand());
					
					response.setReturnValue(result);
				}
			}
		} else {
			if (orderIdObj == null){
				response.setFail("当前货品尚未有数量");
			} else {
				int orderId = Integer.valueOf(String.valueOf(orderIdObj));
				InventoryOrderProduct orderProduct = inventoryOrderProductDAOImpl.getByOrderIdProductId(orderId, pbId);
				if (orderProduct != null){
					ProductBarcode pBarcode = orderProduct.getProductBarcode();
					Product p = pBarcode.getProduct();
					
					int moreQ = p.getNumPerHand() * quantity;
					int newQ = moreQ + orderProduct.getQuantity();
					
					if (newQ ==0)
						inventoryOrderProductDAOImpl.delete(orderProduct, true);
					else {
						orderProduct.setQuantity(newQ);
						inventoryOrderProductDAOImpl.update(orderProduct, true);
					}
					
					InventoryOrder order = inventoryOrderDAOImpl.get(orderId, true);
					double wholePrice = ProductBarcodeDaoImpl.getWholeSalePrice(pBarcode);
					
					double totalWholeSalePrice = wholePrice * p.getNumPerHand() * quantity;
					order.setTotalWholePrice((int)(totalWholeSalePrice + order.getTotalWholePrice()));
					order.setTotalQuantity(moreQ + order.getTotalQuantity());
					inventoryOrderDAOImpl.save(order, true);
					
					result.put("orderId", orderId);
					result.put("pbId", pBarcode.getId());
					result.put("cq", newQ);
					
					response.setReturnValue(result);
					
				} else {
					response.setFail("当前货品尚未有数量");
				}
			}
		}
		
		return response;
	}
//	
	public Response getOrderHeadInfo(int orderId) {
		Response response = new Response();
		
		InventoryOrder order = inventoryOrderDAOImpl.get(orderId, true);
	
		response.setReturnValue(order);
		
		return response;
	}
//
	@Transactional
	public Response getOrder(Object orderIdObj) {
		Response response = new Response();
		if (orderIdObj == null){
		   response.setFail("没有找到订单号,订单号为空");
		   return response;
		}
		
		int orderId = Integer.parseInt(String.valueOf(orderIdObj));
		
		InventoryOrder order = inventoryOrderDAOImpl.retrieveOrder(orderId);
		order.putSetToList();
		
		response.setReturnValue(order);
		
		return response;
	}

	@Transactional
	public Response submitOrder(Object clientIdObj, Object orderIdObj, UserInfor loginUser) {
		Response response = new Response();
		if (clientIdObj == null){
		   response.setFail("客户信息为空无法提交单据");
		   return response;
		}
		int client_id = Integer.valueOf(String.valueOf(clientIdObj));
		HeadQCust client = headQCustDaoImpl.get(client_id, true);
		
//		client.setClient_id(client_id);
//		client.setName("test12");
		
		if (orderIdObj == null){
			   response.setFail("没有订单信息无法提交单据");
			   return response;
		}
		int orderId = Integer.valueOf(String.valueOf(orderIdObj));
		
		InventoryOrder order = inventoryOrderDAOImpl.retrieveOrder(orderId);
		
	    //part 1. to set the selected price and whole price
		Set<String> barcodes = new HashSet<String>();
		
		// 1.1 to store the non-duplicated product code in a sequence list for new order
		Set<InventoryOrderProduct> orderProducts = order.getProduct_Set();
		for (InventoryOrderProduct orderProduct: orderProducts){
			if (orderProduct!= null && orderProduct.getProductBarcode() != null && !orderProduct.getProductBarcode().getBarcode().equals("")){
			   String barcode = orderProduct.getProductBarcode().getBarcode();
			   barcodes.add(barcode);
			}
		}
		
		Map<String, ProductBarcode> productMap = productBarcodeDaoImpl.getProductMapByBarcode(barcodes);
		
		//1.2 to set the re-cost, selected price, whole price, discount
		//               totalQ, totalWhole, totalRecost, totalSalePrice
		int totalQ = 0;
		int totalWholePrice = 0;
		int totalCost = 0;
		int totalSalePrice = 0;
        int index = 0;
        
        List<InventoryOrderProduct> newOrderProducts = new ArrayList<InventoryOrderProduct>();
		newOrderProducts.addAll(orderProducts);
		Collections.sort(newOrderProducts, new com.onlineMIS.sorter.SortByBrandProductCode());
        
		for (InventoryOrderProduct orderProduct: orderProducts){

			if (orderProduct == null || orderProduct.getProductBarcode()==null )
				continue;

			orderProduct.setIndex(index++);
			
			String barcode = orderProduct.getProductBarcode().getBarcode();
			ProductBarcode productBarcode = productMap.get(barcode);
			
			if (productBarcode == null){
				loggerLocal.error("Error to get the product barcode information :" + barcode);
			} else {
				Product product = productBarcode.getProduct();

				double selectedPrice = productBarcodeDaoImpl.getSelectedSalePrice(productBarcode);
				double discount = 1;
				if (selectedPrice == product.getSalesPriceFactory())
					discount = product.getDiscount();
				
				int quantity = orderProduct.getQuantity();
				double wholePrice = selectedPrice * discount;
				double cost = ProductBarcodeDaoImpl.getRecCost(productBarcode);
				double salePrice = product.getSalesPrice();
				
				orderProduct.setDiscount(discount);
				orderProduct.setSalePriceSelected(selectedPrice);
				orderProduct.setWholeSalePrice(Common_util.roundDouble(wholePrice,2));
				orderProduct.setSalesPrice(salePrice);
				orderProduct.setProductBarcode(productBarcode);
				orderProduct.setRecCost(cost);
				
				totalQ += quantity;
				totalCost += quantity * cost;
				totalWholePrice += wholePrice * quantity;
				totalSalePrice += salePrice * quantity;
			}

		}

	    order.setPdaScanner(loginUser);
		order.setOrder_scanner(loginUser);
	    order.setComment("");
	    order.setOrder_StartTime(new Date());
	    order.setOrder_type(InventoryOrder.TYPE_SALES_ORDER_W);
	    order.setTotalQuantity(totalQ);
	    order.setTotalRecCost(Common_util.roundDouble(totalCost,2));
	    order.setTotalRetailPrice(Common_util.roundDouble(totalSalePrice,2));
	    order.setTotalWholePrice(Common_util.roundDouble(totalWholePrice,2));
	    order.setOrder_Status(InventoryOrder.STATUS_PDA_COMPLETE);
		
	    order.setProduct_List(newOrderProducts);
	    order.setProduct_Set(new HashSet<InventoryOrderProduct>());
		order.buildIndex();
		order.putListToSet();
		inventoryOrderDAOImpl.saveOrUpdate(order,true);
	    
	    response.setQuickValue(Response.SUCCESS, "");
		
		
		return response;
	}
//
	public Response getDraftOrders(UserInfor loginUser) {
		Response response = new Response();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(InventoryOrder.class,"order");
		criteria.add(Restrictions.eq("order.order_Status", InventoryOrder.STATUS_PDA_DRAFT));
		criteria.add(Restrictions.eq("pdaScanner.user_id", loginUser.getUser_id()));
	
		List<InventoryOrder> orders = inventoryOrderDAOImpl.search(criteria);
		
		response.setReturnValue(orders);
		
		return response;
	}
//
	public Response deleteOrder(int orderId) {
        Response response = new Response();
		
        String hql_order = "UPDATE InventoryOrder i set i.order_Status = ? where order_ID = ?";
		Object[] values = {InventoryOrder.STATUS_DELETED,orderId};
			
		inventoryOrderDAOImpl.executeHQLUpdateDelete(hql_order, values, true);
		response.setReturnCode(Response.SUCCESS);
        
        return response;
	}
//
	@Transactional
	public Response orderProduct(Object clientIdObj, Object orderIdObj,
			String barcode, int quantity, UserInfor loginUser) {
		Response response = new Response();
		
		ProductBarcode pb = productBarcodeDaoImpl.getByBarcode(barcode);
		if (pb == null){
			response.setFail("无法找到此条码的货品");
			return response;
		} else {
			response = orderProduct(clientIdObj, orderIdObj, pb.getId(), quantity, loginUser);
			if (response.isSuccess()){

				int orderCurrent = 0 ;
				Integer orderId = (Integer) orderIdObj;
				if (orderId == null){
					Map<String, Integer> result = (Map<String, Integer>)response.getReturnValue();
					
					orderId = result.get("orderId");
				}
				
				InventoryOrderProduct orderProduct = inventoryOrderProductDAOImpl.getByOrderIdProductId(orderId.intValue(), pb.getId());
			    if(orderProduct != null)
				  orderCurrent = orderProduct.getQuantity();

				
				HeadQInventoryStore store = headQInventoryStoreDAOImpl.getDefaultStore();
				int inventoryLevel = headQInventoryStockDAOImpl.getProductStock(pb.getId(), store.getId(), true);
				
				int client_id = Integer.valueOf(String.valueOf(clientIdObj));
				int orderHis = inventoryOrderDAOImpl.getQuantityBefore(barcode,client_id);
				
				ProductBarcodeVO productBarcodeVO = new ProductBarcodeVO(pb, inventoryLevel, orderHis, orderCurrent);
				
				Map<String, Object> result = (Map)response.getReturnValue();
				result.put("product", productBarcodeVO.toString());
				
				response.setReturnValue(result);
			}
			
			return response;
		}
	}



}
