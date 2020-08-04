package com.onlineMIS.ORM.DAO.headQ.supplier.purchase;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Category;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Brand2;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Category2;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Color2;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.HeadQInventoryStockDAOImpl;
import com.onlineMIS.ORM.DAO.headQ.supplier.finance.SupplierAcctFlowDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.supplier.supplierMgmt.HeadQSupplierDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.user.UserInforDaoImpl;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Category;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.inventory.HeadQInventoryStock;
import com.onlineMIS.ORM.entity.headQ.inventory.HeadQInventoryStore;
import com.onlineMIS.ORM.entity.headQ.inventory.HeadQSalesHistory;
import com.onlineMIS.ORM.entity.headQ.inventory.HeadQSalesHistoryId;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderVO;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Brand2;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Category2;
import com.onlineMIS.ORM.entity.headQ.qxbabydb.Color2;
import com.onlineMIS.ORM.entity.headQ.supplier.finance.SupplierAcctFlow;
import com.onlineMIS.ORM.entity.headQ.supplier.purchase.HeadqPurchaseHistory;
import com.onlineMIS.ORM.entity.headQ.supplier.purchase.PurchaseOrder;
import com.onlineMIS.ORM.entity.headQ.supplier.purchase.PurchaseOrderPrintVO;
import com.onlineMIS.ORM.entity.headQ.supplier.purchase.PurchaseOrderProduct;
import com.onlineMIS.ORM.entity.headQ.supplier.purchase.PurchaseOrderVO;
import com.onlineMIS.ORM.entity.headQ.supplier.supplierMgmt.HeadQSupplier;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.action.headQ.supplier.purchase.SupplierPurchaseActionFormBean;
import com.onlineMIS.action.headQ.supplier.purchase.SupplierPurchaseActionUIBean;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.filter.SystemParm;


@Service
public class SupplierPurchaseService {
	@Autowired
	private com.onlineMIS.ORM.DAO.headQ.barCodeGentor.BrandDaoImpl brandDaoImpl;
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
	private PurchaseOrderDaoImpl purchaseOrderDaoImpl;
	@Autowired
	private PurchaseOrderProductDaoImpl PurchaseOrderProductDaoImpl;
	@Autowired
	private ProductBarcodeDaoImpl ProductBarcodeDaoImpl;
	@Autowired
	private ProductDaoImpl ProductDaoImpl;
	@Autowired
	private HeadQSupplierDaoImpl headQSupplierDaoImpl;
	@Autowired
	private SupplierAcctFlowDaoImpl supplierAcctFlowDaoImpl;
	@Autowired
	private HeadQInventoryStockDAOImpl headQInventoryStockDAOImpl;
	
	@Autowired
	private UserInforDaoImpl userInforDaoImpl;
	
	@Autowired
	private HeadqPurchaseHistoryDaoImpl headqPurchaseHistoryDaoImpl;

	/**
	 * 获取单据信息
	 * Action 1: edit; 2 : view
	 * @param id
	 * @param loginUser
	 * @return
	 */
	@Transactional
	public Response loadPurchaseOrder(int id, UserInfor loginUser){
		Response response = new Response();
		
		PurchaseOrder order = purchaseOrderDaoImpl.get(id, true);
		if (order == null){
			response.setFail("无法找到 采购单据 : " + id);
		} else {
			UserInfor orderOwner = order.getOrderAuditor();
			int status = order.getStatus();
			
			switch (status) {
				case PurchaseOrder.STATUS_DRAFT:
					if (orderOwner.getUser_id() != loginUser.getUser_id()){
						response.setFail("你没有权限修改 采购单据 .");
					} else {
						purchaseOrderDaoImpl.initialize(order);
						order.putSetToList();
						
						response.setReturnValue(order);
						response.setAction(1);
					}
					break;
				case PurchaseOrder.STATUS_CANCEL:
				case PurchaseOrder.STATUS_COMPLETE:
					purchaseOrderDaoImpl.initialize(order);
					order.putSetToList();
					
					response.setReturnValue(order);
					response.setAction(2);
				    break;	
				case PurchaseOrder.STATUS_DELETED:
					response.setFail("采购单据 : " + id + " 已经被删除.");
					break;
			    default:
			    	response.setFail("采购单据状态错误 : " + id);
				    break;
			}

		}
		
		return response;
	}
	
	/**
	 * 删除单据
	 * @param id
	 * @param loginUser
	 * @return
	 */
	@Transactional
	public Response deletePurchaseOrderById(int id, UserInfor loginUser){
		Response response = new Response();
		
		if (id == 0){
			response.setFail("采购单据尚未保存，无法删除 .");
		} else {
			PurchaseOrder order = purchaseOrderDaoImpl.get(id, true);
			try {
				UserInfor orderOwner = order.getOrderAuditor();
				if (orderOwner.getUser_id() != loginUser.getUser_id()){
					response.setFail("你没有权限删除 采购单据 .");
				} else {
					order.setLastUpdateTime(Common_util.getToday());
					order.setStatus(PurchaseOrder.STATUS_DELETED);
					purchaseOrderDaoImpl.update(order, true);
				}
			} catch (Exception e){
				response.setFail(e.getMessage());
			}
		}
		
		return response;
	}
	
	/**
	 * @tobecheck
	 * 保存purchase order
	 * @param order
	 * @param loginUser
	 * @return
	 */
	@Transactional
	public Response savePurchaseOrderToDraft(PurchaseOrder order, UserInfor loginUser){
		Response response = new Response();

		try {
	
			if (!validatePurchaseOrder(order).isSuccess()){
				return response;
			}
			
			recalculatePurchaseOrder(order);
	
			if (order.getId() == 0){
				order.setCreationTime(Common_util.getToday());
				order.setLastUpdateTime(Common_util.getToday());
				order.setOrderAuditor(loginUser);
				order.setStatus(PurchaseOrder.STATUS_DRAFT);
				order.putListToSet();
				
				purchaseOrderDaoImpl.save(order, true);
			} else {
				PurchaseOrder orderOriginal = purchaseOrderDaoImpl.get(order.getId(), true);

				PurchaseOrderProductDaoImpl.removeItems(order.getId());
				
				orderOriginal.copyFrom(order);
				
				orderOriginal.setLastUpdateTime(Common_util.getToday());
				orderOriginal.setOrderAuditor(loginUser);
				orderOriginal.setStatus(PurchaseOrder.STATUS_DRAFT);
	
				order.putListToSet();	
				orderOriginal.setProductSet(order.getProductSet());
				
				purchaseOrderDaoImpl.update(orderOriginal, true);
			}
		} catch (Exception exception){
			exception.printStackTrace();
			response.setFail(exception.getMessage());
		}
		
		return response;
	}
	

	/**
	 * @tobecheck
	 * 保存purchase order过账
	 * @param order
	 * @param loginUser
	 * @return
	 */
	@Transactional
	public Response savePurchaseOrderToComplete(PurchaseOrder order, UserInfor loginUser){
		Response response = new Response();
		
		if (!validatePurchaseOrder(order).isSuccess()){
			return response;
		}
		
		try {
		     recalculatePurchaseOrder(order);
		} catch (Exception e){
			response.setFail(e.getMessage());
			return response;
		}
				
		//1. 保存单据
		
		if (order.getId() == 0){
			order.setLastUpdateTime(Common_util.getToday());
			order.setOrderAuditor(loginUser);
			order.setStatus(PurchaseOrder.STATUS_COMPLETE);
			order.putListToSet();
			
			purchaseOrderDaoImpl.save(order, true);
			
			
			//2. 过账账目
			updateSupplierAcctFlow(order, false);
					
			//3. 计算库存
			updateHeadqInventory(order, false);
			
			//4.更新最新成本
			updateRecCost(order);
		} else {
			PurchaseOrderProductDaoImpl.removeItems(order.getId());
			PurchaseOrder orderOriginal = purchaseOrderDaoImpl.get(order.getId(), true);
			
			orderOriginal.copyFrom(order);
			
			orderOriginal.setLastUpdateTime(Common_util.getToday());
			orderOriginal.setOrderAuditor(loginUser);
			orderOriginal.setStatus(PurchaseOrder.STATUS_COMPLETE);

			order.putListToSet();	
			orderOriginal.setProductSet(order.getProductSet());
			
			purchaseOrderDaoImpl.update(orderOriginal, true);
			
			
			//2. 过账账目
			updateSupplierAcctFlow(orderOriginal, false);
					
			//3. 计算库存
			updateHeadqInventory(orderOriginal, false);
			
			//4.更新最新成本
			updateRecCost(orderOriginal);
		}

		
		return response;
	}
	
	/**
	 * 更新最新的成本到基础资料
	 * @param order
	 */
	private void updateRecCost(PurchaseOrder order) {
		//如果是测试连锁店不需要
		int TEST_SUPPLIER_ID = SystemParm.getTestSupplierId();
		int supplierId = order.getSupplier().getId();
		if (supplierId == TEST_SUPPLIER_ID)
			return;
		
		Iterator<PurchaseOrderProduct> orderProducts = order.getProductSet().iterator();
		 while (orderProducts.hasNext()){
			 PurchaseOrderProduct orderProduct = orderProducts.next();
			 int pbId = orderProduct.getPb().getId();
			 double cost = orderProduct.getRecCost();

			 ProductBarcode pb = ProductBarcodeDaoImpl.get(pbId, true);
			 
			 Product product = pb.getProduct();
			 product.setRecCost(cost);
			 product.setRecCost2(cost);
			 
			 ProductDaoImpl.save(product, true);
		 }
	}

	/**
	 * @tobecheck
	 * 红虫purchase order过账
	 * @param order
	 * @param loginUser
	 * @return
	 */
	@Transactional
	public Response cancelPurchaseOrder(int orderId, UserInfor loginUser){
		Response response = new Response();
				
		//1. 保存单据
		PurchaseOrder orderOriginal = purchaseOrderDaoImpl.get(orderId, true);
		if (orderOriginal == null){
			response.setFail("单据还未过账无法红冲");
		} else {
			if (orderOriginal.getStatus() != PurchaseOrder.STATUS_COMPLETE){
				response.setFail("单据还未过账无法红冲");
			} else {
				//orderOriginal.setLastUpdateTime(Common_util.getToday());
				orderOriginal.setOrderAuditor(loginUser);
				orderOriginal.setStatus(PurchaseOrder.STATUS_CANCEL);
				orderOriginal.putListToSet();
				
				purchaseOrderDaoImpl.save(orderOriginal, true);
			}
		}
		
		//2. 过账账目
		updateSupplierAcctFlow(orderOriginal, true);
				
		//3. 计算库存
		updateHeadqInventory(orderOriginal, true);
		
		return response;
	}
	
	/**
	 * 更新库存信息, 以及历史购买记录
	 * @param order
	 * @param b
	 */
	private void updateHeadqInventory(PurchaseOrder order, boolean isCancel) {
		//如果是测试连锁店不需要
		int TEST_SUPPLIER_ID = SystemParm.getTestSupplierId();
		int supplierId = order.getSupplier().getId();
		if (supplierId == TEST_SUPPLIER_ID)
			return;
		
		int orderId = order.getId();
		String inventoryStockId = "";
		
		//更新库存数据
		HeadQInventoryStore store = order.getStore();
		int storeId = store.getId();
		
		int offset = 1;
		if (isCancel){
			offset = -1;
			inventoryStockId = "C";
		} 

		if (order.getType() == PurchaseOrder.TYPE_RETURN){
			offset *= -1;	
			inventoryStockId += HeadQInventoryStock.SUPPLIER_RETURN + orderId;
		} else 
			inventoryStockId += HeadQInventoryStock.SUPPLIER_PURCHASE + orderId;
		
		Iterator<PurchaseOrderProduct> orderProducts = order.getProductSet().iterator();
		 while (orderProducts.hasNext()){
			 PurchaseOrderProduct orderProduct = orderProducts.next();
			 int pbId = orderProduct.getPb().getId();
			 double cost = orderProduct.getRecCost();
			 double wholeSalePrice = orderProduct.getWholeSalePrice();
			 int quantity = orderProduct.getQuantity() * offset;
			 double costTotal = cost * quantity;
			 double wholeSalesTotal = wholeSalePrice * quantity;

			 ProductBarcode pb = ProductBarcodeDaoImpl.get(pbId, true);
			 
			 HeadQInventoryStock stock = new HeadQInventoryStock(storeId, inventoryStockId, cost, costTotal, wholeSalePrice, wholeSalesTotal, quantity, pb);
			 headQInventoryStockDAOImpl.save(stock, true);
			 
			 HeadqPurchaseHistory purchaseHistory = new HeadqPurchaseHistory(pbId, cost, wholeSalePrice, quantity);
			 headqPurchaseHistoryDaoImpl.saveOrUpdate(purchaseHistory, true);
		 }

		
	}

	/**
	 * to update the chain's acct flow
	 * @param order
	 * @param b
	 */
    private void updateSupplierAcctFlow(PurchaseOrder order,  boolean isCancel) {
    	int supplierId = order.getSupplier().getId();
		
    	HeadQSupplier supplier = headQSupplierDaoImpl.get(supplierId, true);
    	
		int orderId = order.getId();
    	
    	int orderType = order.getType();
    	double totalAmt = order.getTotalRecCost();
    	double totalDis = order.getTotalDiscount();
    	double netAmt = totalAmt - totalDis;
    	
    	//1. update the offset
    	int offset = 1;
		if (orderType == PurchaseOrder.TYPE_RETURN)
			offset *= -1;
		else if (orderType == PurchaseOrder.TYPE_FREE)
		    offset = 0;
			
		if (isCancel)
			offset *= -1;
    	
		netAmt *= offset;
		
		//2.update the order's preAcctAmt and postAcctAmt
		double initialAcctAmt = supplier.getInitialAcctBalance();  		
		double acctAmtFlow = supplierAcctFlowDaoImpl.getAccumulateAcctFlow(supplierId);
		double preAcctAmt = Common_util.getDecimalDouble(initialAcctAmt + acctAmtFlow);
		double postAcctAmt = Common_util.getDecimalDouble(preAcctAmt + netAmt);
		if (!isCancel){  
			String hql = "update PurchaseOrder set preAcctAmt =?, postAcctAmt=? where id=?";
			Object[] values = {preAcctAmt, postAcctAmt, orderId};
			
			purchaseOrderDaoImpl.executeHQLUpdateDelete(hql, values, false);
		}
		
		supplier.setCurrentAcctBalance(postAcctAmt);
		headQSupplierDaoImpl.update(supplier, true);
		
		SupplierAcctFlow supplierAcctFlow = new SupplierAcctFlow(supplierId, netAmt, "S," + orderId + "," + isCancel, order.getLastUpdateTime());
		supplierAcctFlowDaoImpl.save(supplierAcctFlow, true);
	
	}
    
	/**
	 * 再次汇总
	 * 合并重复的产品
	 * @param order
	 * @throws Exception 
	 */
	private void recalculatePurchaseOrder(PurchaseOrder order) throws Exception {
		
		/**
		 * this is the barcode list with a sequence
		 */
		Map<Integer, PurchaseOrderProduct> orderProductMap = new HashMap<Integer, PurchaseOrderProduct>();
		
		List<PurchaseOrderProduct> orderProducts = order.getProductList();
		Set<Integer> pbIds = new LinkedHashSet<Integer>();

		
		/**
		 * 1. to group the products' quantity, recost, whole sales and build the index
		 */
        double totalRecCost = 0;
        double totalWholePrice = 0;
        int totalQuantity = 0;
		for (PurchaseOrderProduct orderProduct: orderProducts){
			if (orderProduct!= null && orderProduct.getPb() != null && orderProduct.getPb().getId() != 0){
				int pbId = orderProduct.getPb().getId();
				
				ProductBarcode pBarcode = ProductBarcodeDaoImpl.get(pbId, true);
				double wholePrice = ProductBarcodeDaoImpl.getWholeSalePrice(pBarcode);
				
				orderProduct.setWholeSalePrice(wholePrice);
//				orderProduct.setPb(pBarcode);
				
				if (orderProductMap.containsKey(pbId)){
					PurchaseOrderProduct original_orderProduct = orderProductMap.get(pbId);
					int addQuantity = orderProduct.getQuantity();
					int orignalQuantity = original_orderProduct.getQuantity();
					original_orderProduct.setQuantity(addQuantity + orignalQuantity);
					
				} else {
					orderProductMap.put(pbId, orderProduct);
				}
				
				pbIds.add(pbId);
				
				totalQuantity += orderProduct.getQuantity();
				totalRecCost += orderProduct.getRecCost() * orderProduct.getQuantity();
				totalWholePrice += orderProduct.getWholeSalePrice() * orderProduct.getQuantity();
			}
		}
		
		order.setTotalQuantity(totalQuantity);
		order.setTotalRecCost(totalRecCost);
		order.setTotalWholePrice(totalWholePrice);
		
		List<PurchaseOrderProduct> orderProducts2 = new ArrayList<PurchaseOrderProduct>();
		order.getProductList().clear();
		
		int i = 0;
		for (Integer pbId2 : pbIds){
			PurchaseOrderProduct orderProduct = orderProductMap.get(pbId2);
			if (orderProduct == null){
				throw new Exception("合并货品出现错误");
			} else {
				orderProduct.setIndex(i++);
				orderProducts2.add(orderProduct);
			}
		}
		
		order.setProductList(orderProducts2);

	}

	/**
	 * 验证单据的信息是否都正确
	 * @param order
	 * @return
	 */
	private Response validatePurchaseOrder(PurchaseOrder order){
		Response response = new Response();
		boolean success = true;
		Set<PurchaseOrderProduct> productSet = order.getProductSet();
		String errorMsg = "";
		
		for (PurchaseOrderProduct orderProduct : productSet){
			int quantity = orderProduct.getQuantity();
			if (quantity <= 0){
				errorMsg += "条码 " + orderProduct.getPb().getBarcode() + " 的数量必须是>0\n";
				success = false;
			}
		}
		
		if (!success){
			response.setFail(errorMsg);
		}
		
		return response;
	}

	/**
	 * 准备搜索的页面
	 * @param uiBean
	 */
	public void prepareSearchPurchasePage(SupplierPurchaseActionFormBean formBean) {
		formBean.getOrder().setType(Common_util.ALL_RECORD);

	}

	public void prepareEditPurchasePage(SupplierPurchaseActionUIBean uiBean) {
        List<UserInfor> users  = userInforDaoImpl.getAllNormalUsers();	
        List<UserInfor> newUsers = new ArrayList<UserInfor>();
        for (UserInfor user: users){
        	user.setName(user.getPinyin().substring(0, 1) + " " + user.getName());
        	newUsers.add(user);
        }
		
		uiBean.setUsers(newUsers);
		
	}

	/**
	 * purchase order上面扫描条码
	 * @param barcode
	 * @param id
	 * @param type
	 * @return
	 */
	@Transactional
	public Response scanByBarcodePurchaseOrder(String barcode, int supplierId, int orderType, int indexPage, int fromSrc) {
		Response response = new Response();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		ProductBarcode productBarcode = ProductBarcodeDaoImpl.getByBarcode(barcode);
		if (productBarcode!= null && productBarcode.getStatus() == ProductBarcode.STATUS_OK){
                if (orderType == PurchaseOrder.TYPE_PURCHASE || orderType == PurchaseOrder.TYPE_FREE){
					
					dataMap.put("barcode", productBarcode);
					dataMap.put("orderType", orderType);
					dataMap.put("index", indexPage);
					dataMap.put("where", fromSrc);
					
					response.setReturnValue(dataMap);
                } else if (orderType == PurchaseOrder.TYPE_RETURN){
        			Product product = productBarcode.getProduct();
        			int productId = productBarcode.getId();
        			
        			HeadqPurchaseHistory salesHistory = headqPurchaseHistoryDaoImpl.get(productId, true);
        			
        			if (salesHistory != null){
        				product.setRecCost(salesHistory.getRecCost());
        				product.setWholeSalePrice(salesHistory.getWholePrice());

        			} else {
        				product.setRecCost(0);
        				product.setWholeSalePrice(0);
        			}
        			ProductDaoImpl.evict(product);
        			
					dataMap.put("barcode", productBarcode);
					dataMap.put("orderType", orderType);
					dataMap.put("index", indexPage);
					dataMap.put("where", fromSrc);
					
					
                } else 
                	response.setFail("无法找到条码数据");
	    } else {
	    	response.setFail("无法找到条码数据");
	    }

		dataMap.put("returnCode", response.getReturnCode());
		dataMap.put("msg",response.getMessage());
		response.setReturnValue(dataMap);
		
		return response;
	}

	/**
	 * 通过页面搜索采购单据
	 * @param formBean
	 * @return
	 */
	public Response searchPurchaseOrder(SupplierPurchaseActionFormBean formBean) {
		Response response = new Response();
	    DetachedCriteria criteria = DetachedCriteria.forClass(PurchaseOrder.class,"order");
		
		PurchaseOrder searchBean = formBean.getOrder();
		List<PurchaseOrderVO> orderVOs = new ArrayList<PurchaseOrderVO>();
		
		List<PurchaseOrderVO> footers = new ArrayList<PurchaseOrderVO>();
		PurchaseOrderVO footer = new PurchaseOrderVO();
		int totalQ = 0;
		double totalCost = 0;
		double totalWholeSales = 0;
		try {
			if (searchBean.getId() != 0){
				criteria.add(Restrictions.eq("order.id", searchBean.getId()));
				
				criteria.add(Restrictions.ne("order.status", InventoryOrder.STATUS_DELETED));
			} else {
				Date startTime = formBean.getSearchStartTime();
				Date endTime = formBean.getSearchEndTime();
		
				if (searchBean.getStatus() != Common_util.ALL_RECORD)
					criteria.add(Restrictions.eq("order.status", searchBean.getStatus()));
				else
					criteria.add(Restrictions.ne("order.status", PurchaseOrder.STATUS_DELETED));
				
				if (searchBean.getSupplier().getId() != 0 && !searchBean.getSupplier().getName().equals("")){
					int supplierId = searchBean.getSupplier().getId();
		
					criteria.add(Restrictions.eq("order.supplier.id", supplierId));
				}
	
				if (startTime != null && endTime != null){
					Date end_date = Common_util.formEndDate(endTime);
					criteria.add(Restrictions.between("order.creationTime",startTime,end_date));
				}
				
				if (searchBean.getType() != Common_util.ALL_RECORD)
					criteria.add(Restrictions.eq("order.type",searchBean.getType()));
				
				if (!StringUtils.isEmpty(formBean.getProductIds())){
					String[] productIds = formBean.getProductIds().split(",");
					if (productIds.length > 0){
						Set<Integer> productIdSet = new HashSet<Integer>();
						for (String id : productIds){
							productIdSet.add(Integer.parseInt(id));
						}
						DetachedCriteria inventoryProduct = criteria.createCriteria("productSet");
						inventoryProduct.add(Restrictions.in("pb.id", productIdSet));
						
					}
				}
				
				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				criteria.addOrder(Order.asc("order.creationTime"));
			}
			List<PurchaseOrder> orders = purchaseOrderDaoImpl.getByCritera(criteria, true);
				
			
			for (PurchaseOrder order : orders){
				PurchaseOrderVO purchaseOrderVO = new PurchaseOrderVO(order);
				orderVOs.add(purchaseOrderVO);
				
				if (order.getStatus() != PurchaseOrder.STATUS_CANCEL && order.getStatus() != PurchaseOrder.STATUS_DELETED ){
					int orderType = order.getType();
					switch (orderType) {
						case PurchaseOrder.TYPE_PURCHASE:
							totalQ += order.getTotalQuantity();
							totalCost += order.getTotalRecCost();
							totalWholeSales += order.getTotalWholePrice();
							break;
						case PurchaseOrder.TYPE_RETURN:
							totalQ -= order.getTotalQuantity();
							totalCost -= order.getTotalRecCost();
							totalWholeSales -= order.getTotalWholePrice();
							break;
						default:
							break;
					}
				}
			}
			
			
		} catch (Exception exception ){
			exception.printStackTrace();
			response.setFail(exception.getMessage());
			return response;
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", orderVOs);
		
		footer.setTotalQuantity(totalQ);
		footer.setTotalWholePrice(totalWholeSales);
		footer.setTotalRecCost(totalCost);
		footer.setSupplier("合计");
		footers.add(footer);
		data.put("footer", footers);
		
		response.setReturnValue(data);
		
		return response;
	}

	/**
	 * 获取单据准备打印
	 * @param orderId
	 * @return
	 */
	@Transactional
	public Response getPurchaseOrderForPrint(int orderId) {
		Response response = new Response();
		
		PurchaseOrder order = purchaseOrderDaoImpl.get(orderId, true);
		if (order == null){
			response.setFail("单据无法找到");
		} else {
			order.putSetToList();
			
			PurchaseOrderPrintVO printVO = new PurchaseOrderPrintVO(order);
			response.setReturnValue(printVO);
		}
		
		return response;
	}
	
	/**
	 * 复制purchase order
	 * @param order
	 * @return
	 */
	@Transactional
	public Response copyPurchaseOrder(PurchaseOrder order, UserInfor orderAuditor){
		Response response = new Response();
		int orderId = order.getId();
		
		if (orderId > 0){
			PurchaseOrder orderInDB = purchaseOrderDaoImpl.get(orderId, true);
			purchaseOrderDaoImpl.initialize(orderInDB);
			purchaseOrderDaoImpl.evict(orderInDB);
			
			order = orderInDB;
			order.setId(0);
			
            String comment = order.getComment();
            
            order.setComment(comment + "\n 复制于单据" + orderId);
			order.setLastUpdateTime(Common_util.getToday());
			order.setOrderAuditor(orderAuditor);
			order.setStatus(PurchaseOrder.STATUS_DRAFT);
			
			purchaseOrderDaoImpl.save(order, true);

			response.setReturnCode(Response.SUCCESS);
			response.setReturnValue(order.getId());
		} else 
			response.setReturnCode(Response.FAIL);
		
		return response;
	}
	
	/**
	 * 更新order 的 comment
	 * @param order
	 * @return
	 */
	public Response updateOrderComment(PurchaseOrder order) {
	       Response response = new Response();
			
	       if (order!= null && order.getId() != 0){
				String hql_order = "UPDATE PurchaseOrder i set i.comment = ? where id = ?";
				Object[] values = {order.getComment(),order.getId()};
				try {
					purchaseOrderDaoImpl.executeHQLUpdateDelete(hql_order, values, true);
				   response.setSuccess("成功更新备注");
				} catch (Exception e){
				   response.setFail(e.getMessage());
				}
				
	        } else {
	        	response.setQuickValue(Response.FAIL, "无法找到当前订单号");
	        }
	        
	        return response;
	}

}
