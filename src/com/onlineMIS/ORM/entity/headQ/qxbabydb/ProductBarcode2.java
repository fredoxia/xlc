package com.onlineMIS.ORM.entity.headQ.qxbabydb;

import java.io.Serializable;
import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;

public class ProductBarcode2 implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7555848597032873392L;
	private int id;
	private Integer productId = null;
	private Integer colorId = null;
	private Integer sizeId = null;
	private String barcode;
	private Date createDate;
	private int status;

    private Integer chainId = null;

	public ProductBarcode2(){
		
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getColorId() {
		return colorId;
	}

	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}

	public Integer getSizeId() {
		return sizeId;
	}

	public void setSizeId(Integer sizeId) {
		this.sizeId = sizeId;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getChainId() {
		return chainId;
	}

	public void setChainId(Integer chainId) {
		this.chainId = chainId;
	}

	@Override
	public String toString() {
		return "ProductBarcode2 [id=" + id + ", productId=" + productId + ", colorId=" + colorId + ", sizeId=" + sizeId
				+ ", barcode=" + barcode + ", createDate=" + createDate + ", status=" + status + ", chainId=" + chainId
				+ "]";
	}

	
	
}
