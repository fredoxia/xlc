package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ExcelTemplate;
import com.onlineMIS.common.loggerLocal;

public class ChainInventoryReportTemplate  extends ExcelTemplate{
	private List<ChainInventoryRptItem> items = new ArrayList<ChainInventoryRptItem>();
	private ChainStore chainStore;
	private boolean showCost;
	private int year_column = 0;
	private int quarter_column = 1;
	private int brand_column = 2;
	private int productCode_column = 3;
	private int colour_column = 4;
	private int unit_column = 5;
	private int barcode_column =6;
	private int category_column = 7;
	private final int GENDER_COLUMN =8;
	private final int SIZE_COLUMN =9;
	private final int SIZE_MIN_COLUMN =10;
	private final int SIZE_MAX_COLUMN =11;	
	private int quantity_column = 12;
	private int totalCost_column = 13;
	private int totalSales_column = 14;
	private int data_row = 3;


    public ChainInventoryReportTemplate(File file) throws IOException{
    	super(file);
    }
	
	public ChainInventoryReportTemplate(List<ChainInventoryRptItem> items, ChainStore chainStore, String templateWorkbookPath, boolean showCost) throws IOException{
		super(templateWorkbookPath);	
		this.items = items;
		this.chainStore = chainStore;
		this.showCost = showCost;
	}
	
	/**
	 *  this is the function to inject the inventory order to Jinsuan order template
	 * @return
	 */
	public HSSFWorkbook process(){
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);
		//write header
		Row header1 = sheet.getRow(0);
		header1.createCell(1).setCellValue(chainStore.getChain_name());
		
		Row header2 = sheet.getRow(1);
		header2.createCell(1).setCellValue(Common_util.dateFormat_f.format(new Date()));
		
		//write product infmration
		int totalDataRow = items.size();
		int totalQuantity = 0;
		double totalCost = 0;
		double totalSale = 0;
		for (int i = 0; i < totalDataRow; i++){

			ChainInventoryRptItem levelFourItem = items.get(i);
			Row row = sheet.createRow(data_row + i);

			Product product = levelFourItem.getProductBarcode().getProduct();
			
			Year year = product.getYear();
			row.createCell(year_column).setCellValue(year.getYear());
			
			Quarter quarter =  product.getQuarter();
			row.createCell(quarter_column).setCellValue(quarter.getQuarter_Name());
			
			Brand brand = product.getBrand();
			row.createCell(brand_column).setCellValue(brand.getBrand_Name());
			
			row.createCell(productCode_column).setCellValue(product.getProductCode());

			row.createCell(barcode_column).setCellValue(levelFourItem.getProductBarcode().getBarcode());
			
			Color color = levelFourItem.getProductBarcode().getColor();
			if (color == null)
				row.createCell(colour_column).setCellValue("");
			else 
				row.createCell(colour_column).setCellValue(color.getName());
			
			row.createCell(unit_column).setCellValue(product.getUnit());
			
			row.createCell(category_column).setCellValue(product.getCategory().getCategory_Name());
			
			row.createCell(GENDER_COLUMN).setCellValue(product.getGenderS());
			row.createCell(SIZE_COLUMN).setCellValue(product.getSizeRangeS());
			
			Integer sizeMin = product.getSizeMin();
			if (sizeMin != null && sizeMin != 0)
			    row.createCell(SIZE_MIN_COLUMN).setCellValue(sizeMin);
			
			Integer sizeMax = product.getSizeMin();
			if (sizeMax != null && sizeMax != 0)
			    row.createCell(SIZE_MAX_COLUMN).setCellValue(sizeMax);

			row.createCell(quantity_column).setCellValue(levelFourItem.getTotalQuantity());
			
			if (showCost)
				row.createCell(totalCost_column).setCellValue(levelFourItem.getTotalWholeSalesAmt());
			row.createCell(totalSales_column).setCellValue(levelFourItem.getTotalRetailSalesAmt());
			
			totalQuantity += levelFourItem.getTotalQuantity();
			totalCost += levelFourItem.getTotalWholeSalesAmt();
			totalSale += levelFourItem.getTotalRetailSalesAmt();
		}
		
		Row row = sheet.createRow(totalDataRow + 3);
		row.createCell(0).setCellValue("合计");
		row.createCell(quantity_column).setCellValue(totalQuantity);
		if (showCost)
		   row.createCell(totalCost_column).setCellValue(totalCost);
		row.createCell(totalSales_column).setCellValue(totalSale);

		
		return templateWorkbook;
	}
	

}
