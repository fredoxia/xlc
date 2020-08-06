package com.onlineMIS.ORM.entity.headQ.qxbabydb;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.objectweb.asm.xwork.commons.StaticInitMerger;

import com.onlineMIS.ORM.DAO.headQ.user.NewsService;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.common.Common_util;



public class Product2  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4698543100350625116L;
	
	private int productId;
	/**
	 * unique
	 */
    private String serialNum;
    private Integer areaId = null;
    private Integer yearId = null;
    private Integer quarterId = null;
    private Integer brandId = null;
    private Integer categoryId = null;
    private String productCode;
    private int numPerHand;
    private String unit;

	private Date createDate = new Date();

    /**
     * the chain store's sale price连锁店零售价
     */
    private double salesPrice;
    /**
     * the whole saler's sale price/chain store's cost,批发商发价1/连锁店进价
     */
    private double wholeSalePrice;
    /**
     * the whole saler's sale price/chain store's cost,批发商发价2/连锁店进价
     */    
    private double wholeSalePrice2;
    /**
     * the whole saler's sale price/chain store's cost,批发商发价3/连锁店进价
     */    
    private double wholeSalePrice3;

    /**
     * the whole saler's cost price批发商进价
     */
    private double recCost;
    /**
     * the whole saler's cost price批发商进价2
     */
    private double recCost2;
    /**
     * the price decided by factory 厂家零售价
     */
    private double salesPriceFactory;
    /**
     * the product's discount 默认折扣
     */
    private double discount;
    private Integer chainId = null;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public Integer getYearId() {
		return yearId;
	}
	public void setYearId(Integer yearId) {
		this.yearId = yearId;
	}
	public Integer getQuarterId() {
		return quarterId;
	}
	public void setQuarterId(Integer quarterId) {
		this.quarterId = quarterId;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public int getNumPerHand() {
		return numPerHand;
	}
	public void setNumPerHand(int numPerHand) {
		this.numPerHand = numPerHand;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public double getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(double salesPrice) {
		this.salesPrice = salesPrice;
	}
	public double getWholeSalePrice() {
		return wholeSalePrice;
	}
	public void setWholeSalePrice(double wholeSalePrice) {
		this.wholeSalePrice = wholeSalePrice;
	}
	public double getWholeSalePrice2() {
		return wholeSalePrice2;
	}
	public void setWholeSalePrice2(double wholeSalePrice2) {
		this.wholeSalePrice2 = wholeSalePrice2;
	}
	public double getWholeSalePrice3() {
		return wholeSalePrice3;
	}
	public void setWholeSalePrice3(double wholeSalePrice3) {
		this.wholeSalePrice3 = wholeSalePrice3;
	}
	public double getRecCost() {
		return recCost;
	}
	public void setRecCost(double recCost) {
		this.recCost = recCost;
	}
	public double getRecCost2() {
		return recCost2;
	}
	public void setRecCost2(double recCost2) {
		this.recCost2 = recCost2;
	}
	public double getSalesPriceFactory() {
		return salesPriceFactory;
	}
	public void setSalesPriceFactory(double salesPriceFactory) {
		this.salesPriceFactory = salesPriceFactory;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public Integer getChainId() {
		return chainId;
	}
	public void setChainId(Integer chainId) {
		this.chainId = chainId;
	}
    
    
}
