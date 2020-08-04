package com.onlineMIS.ORM.entity.headQ.qxbabydb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;

import javassist.expr.NewArray;


public class Brand2  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2808801063978393561L;

	public static final int TYPE_ALL = -1;
	public static final int TYPE_CHAIN = 1;

    private int brand_ID;
    private String brand_Name;
    private String brand_Code;
    private String supplier;
    private String pinyin;
    private Integer chain_id ;

	
 	
 	public Integer getChain_id() {
		return chain_id;
	}

	public void setChain_id(Integer chain_id) {
		this.chain_id = chain_id;
	}

	public Brand2(){
 		
 	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public Brand2(int brandId, String brandName){
 		this.brand_ID = brandId;
 		this.brand_Name = brandName;
 	}
	public int getBrand_ID() {
		return brand_ID;
	}
	public void setBrand_ID(int brand_ID) {
		this.brand_ID = brand_ID;
	}
	public String getBrand_Name() {
		return brand_Name;
	}
	public void setBrand_Name(String brand_Name) {
		this.brand_Name = brand_Name;
	}
	public String getBrand_Code() {
		return brand_Code;
	}
	public void setBrand_Code(String brand_Code) {
		this.brand_Code = brand_Code;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Override
	public String toString() {
		return "Brand [brand_ID=" + brand_ID + ", brand_Name=" + brand_Name
				+ "]";
	}
    
	
}
