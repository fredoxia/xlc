package com.onlineMIS.action.headQ.finance;

import java.util.ArrayList;
import java.util.List;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceBill;

public class FinanceActionUIBean {
	private List<FinanceBill> financeBills = new ArrayList<FinanceBill>();
	private FinanceBill order = new FinanceBill();
	private List<ChainStore> chainStores = new ArrayList<ChainStore>();
	
	

	public List<ChainStore> getChainStores() {
		return chainStores;
	}

	public void setChainStores(List<ChainStore> chainStores) {
		this.chainStores = chainStores;
	}

	public List<FinanceBill> getFinanceBills() {
		return financeBills;
	}

	public void setFinanceBills(List<FinanceBill> financeBills) {
		this.financeBills = financeBills;
	}

	public FinanceBill getOrder() {
		return order;
	}

	public void setOrder(FinanceBill financeBill) {
		this.order = financeBill;
	}
	

}
