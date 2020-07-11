package com.onlineMIS.ORM.entity.headQ.barcodeGentor;

import java.io.Serializable;
import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;

public class ProductBarcode implements Serializable {
	public static final int BARCODE_LENGTH = 12;
	public static final String BARCODE_PREFIX ="1";
	public static final String SERIAL_PREFIX = "2";
    public static final int STATUS_DELETE = 2;
    public static final int STATUS_OK = 1;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7555848597032873392L;
	private int id;
	private Product product = new Product();
	private Color color;
	private Size size;
	private String barcode;
	private Date createDate;
    private int boughtBefore = 0 ;
    private int inventoryLevel = 0;
    private int status = 1;
    private ChainStore chainStore;

	public ChainStore getChainStore() {
		return chainStore;
	}

	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public ProductBarcode(){
		
	}
	
	public ProductBarcode(int id){
		this.setId(id);
	}
	
	public ProductBarcode(Product product , Color color, Size size){
		this.setColor(color);
		this.setProduct(product);
		this.setSize(size);
		this.setCreateDate(new Date());
		
	}
	
	public int getInventoryLevel() {
		return inventoryLevel;
	}

	public void setInventoryLevel(int inventoryLevel) {
		this.inventoryLevel = inventoryLevel;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBoughtBefore() {
		return boughtBefore;
	}

	public void setBoughtBefore(int boughtBefore) {
		this.boughtBefore = boughtBefore;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Size getSize() {
		return size;
	}
	public void setSize(Size size) {
		this.size = size;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
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
		ProductBarcode other = (ProductBarcode) obj;
		if (id != 0 && id == other.getId())
			return true;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (other.color == null)
		    return false;
		  else if (color.getColorId() != other.color.getColorId())
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (other.product == null) 
			return false;
		  else if (!product.equals(other.product))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (other.size == null)
		    return false;
		  else if (size.getSizeId() != other.size.getSizeId())
			return false;
		return true;
	}
	
	public String toString(){
		Product product = this.getProduct();
		return this.getBarcode() + " " + this.product.getProductCode();
	}
	
}
