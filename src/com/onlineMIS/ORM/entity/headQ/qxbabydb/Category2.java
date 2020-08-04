package com.onlineMIS.ORM.entity.headQ.qxbabydb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Category2  implements Serializable{

	public static final int TYPE_HEAD = -1;
	public static final int TYPE_CHAIN = 1;
    private int category_ID;
    private String category_Name;
    private String category_Code = ""; 
    private int chainId = -1;
    private int hide;


	public int getHide() {
		return hide;
	}

	public void setHide(int hide) {
		this.hide = hide;
	}

	public int getChainId() {
		return chainId;
	}

	public void setChainId(int chainId) {
		this.chainId = chainId;
	}

	public int getCategory_ID() {
		return category_ID;
	}
	public void setCategory_ID(int category_ID) {
		this.category_ID = category_ID;
	}
	public String getCategory_Name() {
		return category_Name;
	}
	public void setCategory_Name(String category_Name) {
		this.category_Name = category_Name;
	}
	public String getCategory_Code() {
		return category_Code;
	}
	public void setCategory_Code(String category_Code) {
		this.category_Code = category_Code;
	}
     
     
}
