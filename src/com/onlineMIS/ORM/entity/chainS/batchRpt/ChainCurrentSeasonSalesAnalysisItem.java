package com.onlineMIS.ORM.entity.chainS.batchRpt;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.common.Common_util;


public class ChainCurrentSeasonSalesAnalysisItem {
	private int rank;
	private ChainStore chainStore;
	private double lastYearPurchase = 0;
	private int purchaseQ = 0;
	private double purchaseAmt = 0;
	private int returnQ = 0;
	private double returnAmt = 0;
	private int netPurchaseQ = 0;
	private double netPurchaseAmt = 0;
	private double inDeliveryAmt = 0;
	private int inDeliveryQ = 0;
	private double returnRatio;
	private int inventoryQ = 0;
	private double inventoryAmt = 0;
	private double inventoryRatio;
	private double salesQ = 0;
	private double salesCost = 0;
	private double salesAmt = 0;
	private double salesRatio;
	private double inDeliveryRatio;
	
	
	public ChainCurrentSeasonSalesAnalysisItem(){
		
	}
	
	public double getSalesCost() {
		return salesCost;
	}

	public void setSalesCost(double salesCost) {
		this.salesCost = salesCost;
	}

	public int getNetPurchaseQ() {
		return netPurchaseQ;
	}

	public void setNetPurchaseQ(int netPurchaseQ) {
		this.netPurchaseQ = netPurchaseQ;
	}

	public int getReturnQ() {
		return returnQ;
	}

	public void setReturnQ(int returnQ) {
		this.returnQ = returnQ;
	}

	public int getInDeliveryQ() {
		return inDeliveryQ;
	}

	public void setInDeliveryQ(int inDeliveryQ) {
		this.inDeliveryQ = inDeliveryQ;
	}

	public int getPurchaseQ() {
		return purchaseQ;
	}

	public void setPurchaseQ(int purchaseQ) {
		this.purchaseQ = purchaseQ;
	}

	public int getInventoryQ() {
		return inventoryQ;
	}

	public void setInventoryQ(int inventoryQ) {
		this.inventoryQ = inventoryQ;
	}

	public double getSalesQ() {
		return salesQ;
	}

	public void setSalesQ(double salesQ) {
		this.salesQ = salesQ;
	}

	public double getInDeliveryRatio() {
		return inDeliveryRatio;
	}

	public void setInDeliveryRatio(double inDeliveryRatio) {
		this.inDeliveryRatio = inDeliveryRatio;
	}

	public double getNetPurchaseAmt() {
		return netPurchaseAmt;
	}

	public void setNetPurchaseAmt(double netPurchaseAmt) {
		this.netPurchaseAmt = netPurchaseAmt;
	}

	public double getReturnAmt() {
		return returnAmt;
	}

	public void setReturnAmt(double returnAmt) {
		this.returnAmt = returnAmt;
	}

	public double getInDeliveryAmt() {
		return inDeliveryAmt;
	}

	public void setInDeliveryAmt(double inDeliveryAmt) {
		this.inDeliveryAmt = inDeliveryAmt;
	}

	public ChainCurrentSeasonSalesAnalysisItem(ChainStore store){
		this.chainStore = store;
	}
	
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public double getLastYearPurchase() {
		return lastYearPurchase;
	}
	public void setLastYearPurchase(double lastYearPurchase) {
		this.lastYearPurchase = lastYearPurchase;
	}
	public double getPurchaseAmt() {
		return purchaseAmt;
	}
	public void setPurchaseAmt(double purchaseAmt) {
		this.purchaseAmt = purchaseAmt;
	}
	public double getReturnRatio() {
		return returnRatio;
	}
	public void setReturnRatio(double returnRatio) {
		this.returnRatio = returnRatio;
	}
	public double getInventoryAmt() {
		return inventoryAmt;
	}
	public void setInventoryAmt(double inventoryAmt) {
		this.inventoryAmt = inventoryAmt;
	}
	public double getInventoryRatio() {
		return inventoryRatio;
	}
	public void setInventoryRatio(double inventoryRatio) {
		this.inventoryRatio = inventoryRatio;
	}
	public double getSalesAmt() {
		return salesAmt;
	}
	public void setSalesAmt(double salesAmt) {
		this.salesAmt = salesAmt;
	}
	public double getSalesRatio() {
		return salesRatio;
	}
	public void setSalesRatio(double salesRatio) {
		this.salesRatio = salesRatio;
	}

	public void calculateRatio() {
		netPurchaseAmt = purchaseAmt - returnAmt;
		netPurchaseQ = purchaseQ - returnQ;
		
		if (purchaseAmt == 0) {
			returnRatio = Common_util.ALL_RECORD;
		} else {
			returnRatio = returnAmt / purchaseAmt;
		}
		
		if (netPurchaseAmt == 0) {
			salesRatio = Common_util.ALL_RECORD;
			inventoryRatio = Common_util.ALL_RECORD;
			inDeliveryRatio = Common_util.ALL_RECORD;
		} else {
			salesRatio = salesAmt/ netPurchaseAmt;
			inventoryRatio = inventoryAmt / netPurchaseAmt;
			inDeliveryRatio = inDeliveryAmt / netPurchaseAmt;
		}	
	}

	
}
