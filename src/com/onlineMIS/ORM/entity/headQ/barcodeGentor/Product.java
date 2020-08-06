package com.onlineMIS.ORM.entity.headQ.barcodeGentor;



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



public class Product  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4698543100350625116L;
	
	private int productId;
	/**
	 * unique
	 */
    private String serialNum;
    private Area area = new Area();
    private Year year = new Year();
    private Quarter quarter = new Quarter();
    private Brand brand = new Brand();
    private Category category = new Category();
    private String productCode;
    private int numPerHand;
    private String unit;

	private Date createDate = new Date();
    private String createDate_s;
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
     * 历史输入 折扣价
     */
    private double lastInputPrice;
    
    /**
     * 历史选择价格
     */
    private double lastChoosePrice;
    /**
     * the whole saler's cost price批发商进价
     */
    private double recCost;
    /**
     * the whole saler's cost price批发商进价2
     */
    private double recCost2 = 0;
    /**
     * the price decided by factory 厂家零售价
     */
    private double salesPriceFactory;
    /**
     * the product's discount 默认折扣
     */
    private double discount;
    private ChainStore chainStore;
    
    private String gender;
    private String sizeRange;
    private Integer sizeMin;
    private Integer sizeMax;
    
    private String genderS;
    private String sizeRangeS;
    static Map<String, String> genderMap = new HashMap<String, String>();
    static Map<String, String> sizeRangeMap = new HashMap<String, String>();
    
    static {
    	genderMap.put("M", "男");
    	genderMap.put("F", "女");
    	genderMap.put("N", "中性");
    	
    	sizeRangeMap.put("L", "大童");
    	sizeRangeMap.put("M", "中童");
    	sizeRangeMap.put("S", "小童");
    }


	public String getGenderS() {
		if (StringUtils.isEmpty(gender))
			return "";
		else 
		    return genderMap.get(gender);
	}


	public String getSizeRangeS() {
		if (StringUtils.isEmpty(sizeRange))
			return "";
		else 
		    return sizeRangeMap.get(sizeRange);
	}


	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSizeRange() {
		return sizeRange;
	}

	public void setSizeRange(String sizeRange) {
		this.sizeRange = sizeRange;
	}

	public Integer getSizeMin() {
		return sizeMin;
	}

	public void setSizeMin(Integer sizeMin) {
		this.sizeMin = sizeMin;
	}

	public Integer getSizeMax() {
		return sizeMax;
	}

	public void setSizeMax(Integer sizeMax) {
		this.sizeMax = sizeMax;
	}

	public ChainStore getChainStore() {
		return chainStore;
	}

	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}

	public double getWholeSalePrice() {
		return wholeSalePrice;
	}
	public void setWholeSalePrice(double wholeSalePrice) {
		this.wholeSalePrice = wholeSalePrice;
	}
	public double getRecCost() {
		return recCost;
	}
	public void setRecCost(double recCost) {
		this.recCost = recCost;
	}

	public double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(double salesPrice) {
		this.salesPrice = salesPrice;
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

    public String getCreateDate_s() {
		return createDate_s;
	}

	public void setCreateDate_s(String createDate_s) {
		this.createDate_s = createDate_s;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
		setCreateDate_s(Common_util.dateFormat_f.format(createDate));
	}

	public Product(){

	}
    public Product(String serialNum,String productCode, int numPerHand, String unit, Date createDate, String createDate_s,double salesPrice){
    	this.setSerialNum(serialNum);
    	this.setProductCode(productCode);
    	this.setNumPerHand(numPerHand);
    	this.setUnit(unit);
    	this.setCreateDate(createDate);
    	this.setCreateDate_s(createDate_s);
    	this.setSalesPrice(salesPrice);
  	
    }
    

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}
	public Quarter getQuarter() {
		return quarter;
	}
	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
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
	
	public double getLastInputPrice() {
		return lastInputPrice;
	}
	public void setLastInputPrice(double lastInputPrice) {
		this.lastInputPrice = lastInputPrice;
	}
	
	public double getLastChoosePrice() {
		return lastChoosePrice;
	}
	public void setLastChoosePrice(double lastChoosePrice) {
		this.lastChoosePrice = lastChoosePrice;
	}

	public double getRecCost2() {
		return recCost2;
	}


	public void setRecCost2(double recCost2) {
		this.recCost2 = recCost2;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((productCode == null) ? 0 : productCode.hashCode());
		result = prime * result + ((quarter == null) ? 0 : quarter.hashCode());
		result = prime * result
				+ ((serialNum == null) ? 0 : serialNum.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (productId != 0 && productId== other.getProductId())
			return true;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (area.getArea_ID() != other.area.getArea_ID())
			return false;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (brand.getBrand_ID() != other.brand.getBrand_ID())
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (category.getCategory_ID() != other.category.getCategory_ID())
			return false;
		if (productCode == null) {
			if (other.productCode != null)
				return false;
		} else if (!productCode.equals(other.productCode))
			return false;
		if (quarter == null) {
			if (other.quarter != null)
				return false;
		} else if (quarter.getQuarter_ID() != other.quarter.getQuarter_ID())
			return false;
		if (serialNum == null) {
			if (other.serialNum != null)
				return false;
		} else if (!serialNum.equals(other.serialNum))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (year.getYear_ID() != other.year.getYear_ID())
			return false;
		return true;
	}
	public String toString(){
    	
    	return  this.getArea().getArea_Name() + " " + 
    	        this.getYear().getYear() + " " + 
    			this.getQuarter().getQuarter_Name() + " " + 
    			this.getCategory().getCategory_Name() + " " + 
    			this.getBrand().getBrand_Name() + " " + 
    			this.getProductCode() + " " + 
    			this.getNumPerHand() + " " + 
    			this.getUnit() + " " + 
    			this.getSalesPrice() + " " + 
    			this.getSerialNum();
    }
    
}
