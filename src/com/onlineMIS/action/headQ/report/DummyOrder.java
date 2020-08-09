package com.onlineMIS.action.headQ.report;

import com.onlineMIS.ORM.entity.headQ.custMgmt.HeadQCust;
import com.onlineMIS.ORM.entity.headQ.supplier.supplierMgmt.HeadQSupplier;

public class DummyOrder {
	private HeadQSupplier supplier;
	private HeadQCust cust;
	private String comment;
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public HeadQSupplier getSupplier() {
		return supplier;
	}
	public void setSupplier(HeadQSupplier supplier) {
		this.supplier = supplier;
	}
	public HeadQCust getCust() {
		return cust;
	}
	public void setCust(HeadQCust cust) {
		this.cust = cust;
	}
	
	
}
