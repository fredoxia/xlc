package com.onlineMIS.ORM.entity.headQ.barcodeGentor;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.AreaDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.BrandDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.CategoryDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ColorDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductUnitDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.QuarterDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.YearDaoImpl;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ExcelTemplate;
import com.onlineMIS.common.ExcelUtil;

public class BarcodeImportTemplate extends ExcelTemplate{
	private boolean isSuccess = true;
	private String validateMsg = "";
	
	public final String SPLITTER = ",";
	protected final int data_row = 1;
	protected final int year_column= 0;
	protected final int quarter_column= 1;
	protected final int brand_column= 2;
	protected final int productCode_column= 3;
	protected final int barcode_column= 4;
	protected final int color_column= 5;
	protected final int category_column= 6;
	protected final int recCost_column= 7;
	protected final int factoryPrice_column= 8;
	protected final int discount_column= 9;
	protected final int wholePrice_column= 10;
	protected final int salePrice_column= 11;
	protected final int unit_column= 12;
	protected final int numPerHand_column= 13;
	protected final int prodNum_column= 14;
	private Area area = null;
	private List<Object> wsData = new ArrayList<Object>();
	
	/**
	 * 传入文件，生出条码
	 */
	public BarcodeImportTemplate(File file){
		super();
		try {
		  templateWorkbook = new HSSFWorkbook(new FileInputStream(file));
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 验证所有信息正确
	 * @param categoryDaoImpl
	 * @param colorDaoImpl
	 * @return
	 */
	private void validate(YearDaoImpl yearDaoImpl, QuarterDaoImpl quarterDaoImpl, BrandDaoImpl brandDaoImpl, CategoryDaoImpl categoryDaoImpl, ColorDaoImpl colorDaoImpl, ProductUnitDaoImpl productUnitDaoImpl, ProductDaoImpl productDaoImpl){
		StringBuilder errorMsg = new StringBuilder();
		boolean validate = true;
		
		if (templateWorkbook == null){
			isSuccess = false;
			validateMsg = "上传的文件是空文件";
			return ;
		}
		
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);

		int rowDisplay = 0;
		boolean rowError = true;
		for (int i = data_row; ;i++){
			rowError = true;
			rowDisplay = i +1;
			HSSFRow row = sheet.getRow(i);
			if (row == null)
				break;
			
			String yearS = ExcelUtil.getPuzzleString(row.getCell(year_column));
			Year year = yearDaoImpl.getYearByName(yearS);
			if (year == null){
				rowError = false;
				errorMsg.append("行" + rowDisplay + ": 年份不存在数据库中, " + yearS);
			}
			
			String quarterS = ExcelUtil.getPuzzleString(row.getCell(quarter_column));
			Quarter quarter = quarterDaoImpl.getByName(quarterS);
			if (quarter == null){
				rowError = false;
				errorMsg.append("行" + rowDisplay + ": 季度不存在数据库中, " + quarterS);
			}
			
			String brandS = ExcelUtil.getPuzzleString(row.getCell(brand_column));
			try {
				int brandId = Integer.parseInt(brandS.split(SPLITTER)[1]);
				Brand brand = brandDaoImpl.get(brandId, true);
				if (brand == null){
					rowError = false;
					errorMsg.append("行" + rowDisplay + ": 品牌无法获取. " + brandS);
				}
			} catch (Exception e){
				rowError = false;
				errorMsg.append("行" + rowDisplay + ": 品牌转换出问题. " + brandS);
			}

			String productCode = ExcelUtil.getPuzzleString(row.getCell(productCode_column));
			if (productCode.equals("")){
				rowError = false;
				errorMsg.append("行" + rowDisplay + ": 产品货号为空. ");
			}
			
			String barcode = ExcelUtil.getPuzzleString(row.getCell(barcode_column));
			if (barcode.equals("")){
				rowError = false;
				errorMsg.append("行" + rowDisplay + ": 条码为空. ");
			}
			
			HSSFCell colourCell = row.getCell(color_column);
			if (colourCell == null){
				continue;
			} else {
				String colorsString = colourCell.getStringCellValue().trim();
				if (colorsString.equals(""))
					continue;
				else {
					try {
						int colorId = Integer.parseInt(colorsString.split(SPLITTER)[1]);
						Color color = colorDaoImpl.get(colorId, true);
						if (color == null){
							rowError = false;
							errorMsg.append("行" + rowDisplay + ": 颜色无法获取. " + colorsString);
						}
					} catch (Exception e){
						rowError = false;
						errorMsg.append("行" + rowDisplay + ": 颜色处理出问题. " + colorsString);
					}
				}
			}
			
			
			HSSFCell categoryCell = row.getCell(category_column);
			if (categoryCell == null){
				rowError = false;
				errorMsg.append("行" + rowDisplay + ": 产品种类为空. ");
			} else {
				String categoryString = categoryCell.getStringCellValue().trim();
				try {
					int categoryId = Integer.parseInt(categoryString.split(SPLITTER)[1]);
					Category category = categoryDaoImpl.get(categoryId, true);
					if (category == null){
						rowError = false;
						errorMsg.append("行" + rowDisplay + ": 种类无法获取. " + categoryString);
					}
				} catch (Exception e){
					rowError = false;
					errorMsg.append("行" + rowDisplay + ": 种类处理出问题. " + categoryString);
				}
			}
			

			try {
			   double recCost = row.getCell(recCost_column).getNumericCellValue();
			   if (recCost < 0) {
			     rowError = false;
				 errorMsg.append("行" + rowDisplay + ": 进价("+ recCost+")错误. ");
			   }
			} catch (Exception e){
				rowError = false;
				errorMsg.append("行" + rowDisplay + ": 进价错误. ");
			}
			
			try {
				   double factoryPrice = row.getCell(factoryPrice_column).getNumericCellValue();
				   if (factoryPrice < 0) {
				     rowError = false;
					 errorMsg.append("行" + rowDisplay + ": 厂家零售价("+ factoryPrice+")错误. ");
				   }
				} catch (Exception e){
					rowError = false;
					errorMsg.append("行" + rowDisplay + ": 厂家零售价错误. ");
				}

			try {
				   double discount = row.getCell(discount_column).getNumericCellValue();
				   if (discount < 0) {
				     rowError = false;
					 errorMsg.append("行" + rowDisplay + ": 折扣("+ discount+")错误. ");
				   }
				} catch (Exception e){
					rowError = false;
					errorMsg.append("行" + rowDisplay + ": 折扣错误. ");
				}
			
			try {
				   double salePrice = row.getCell(salePrice_column).getNumericCellValue();
				   if (salePrice < 0) {
				     rowError = false;
					 errorMsg.append("行" + rowDisplay + ": 零售价("+ salePrice+")错误. ");
				   }
				} catch (Exception e){
					rowError = false;
					errorMsg.append("行" + rowDisplay + ": 零售价错误. ");
				}

			try {
				   double wholePrice = row.getCell(wholePrice_column).getNumericCellValue();
				   if (wholePrice < 0) {
				     rowError = false;
					 errorMsg.append("行" + rowDisplay + ": 发价("+ wholePrice+")错误. ");
				   }
				} catch (Exception e){
					rowError = false;
					errorMsg.append("行" + rowDisplay + ": 发价错误. ");
				}
			String unit = ExcelUtil.getPuzzleString(row.getCell(unit_column));
			
			if (unit.equals("")){
				rowError = false;
				errorMsg.append("行" + rowDisplay + ": 产品单位为空. ");
			} else {
				if (!productUnitDaoImpl.checkExist(unit)){
					rowError = false;
					errorMsg.append("行" + rowDisplay + ": 产品单位 (" + unit + ") 不存在. ");
				}
			}
			
			try {
				   int numPerHand = (int) row.getCell(numPerHand_column).getNumericCellValue();
				   if (numPerHand <= 0) {
				     rowError = false;
					 errorMsg.append("行" + rowDisplay + ": 齐手数量("+ numPerHand+")错误. ");
				   }
				} catch (Exception e){
					rowError = false;
					errorMsg.append("行" + rowDisplay + ": 齐手数量 错误. ");
				}
			
			try {
				   String prodNum = ExcelUtil.getPuzzleString(row.getCell(prodNum_column));
				   if (prodNum.equals("")) {
				     rowError = false;
					 errorMsg.append("行" + rowDisplay + ": 商品编码("+ prodNum+")错误. ");
				   } else {
					   int prodNumInt = Integer.parseInt(prodNum);
					   if (prodNumInt > 5000000){
						     rowError = false;
							 errorMsg.append("行" + rowDisplay + ": 商品编码("+ prodNum+")超过最大限制，联系夏林. ");
					   }
				   }
				} catch (Exception e){
					rowError = false;
					errorMsg.append("行" + rowDisplay + ": 商品编码 错误. ");
				}
			
			
			if (rowError == false){
				validate = false;
				errorMsg.append("<br>");
			}
		}
		
		isSuccess = validate;
		
		validateMsg =  errorMsg.toString();
	}
	
	/**
	 * 通过文件读出条码，返回
	 * @return
	 */
	public void proccess(AreaDaoImpl areaDaoImpl, YearDaoImpl yearDaoImpl, QuarterDaoImpl quarterDaoImpl, BrandDaoImpl brandDaoImpl, CategoryDaoImpl categoryDaoImpl, ColorDaoImpl colorDaoImpl, ProductUnitDaoImpl productUnitDaoImpl, ProductDaoImpl productDaoImpl){
		validate(yearDaoImpl, quarterDaoImpl, brandDaoImpl,categoryDaoImpl, colorDaoImpl, productUnitDaoImpl, productDaoImpl);
		
		area = areaDaoImpl.get(Area.CURRENT_AREA, true);
		
		if (!isSuccess)
			return ;

		HSSFSheet sheet = templateWorkbook.getSheetAt(0);

		for (int i = data_row; ;i++){

			HSSFRow row = sheet.getRow(i);
			if (row == null)
				break;
			
			HSSFCell productNumCell = row.getCell(prodNum_column);
			String prodNum = productNumCell.getStringCellValue().trim();
			
			HSSFCell productUnitCell = row.getCell(unit_column);
			String productUnitString = productUnitCell.getStringCellValue().trim();
			
			HSSFCell numPerHandCell = row.getCell(numPerHand_column);
			int numPerHand = (int)numPerHandCell.getNumericCellValue();
			
			String barcode = ExcelUtil.getPuzzleString(row.getCell(barcode_column));
			
			double salePrice = row.getCell(salePrice_column).getNumericCellValue();
			double recCost = row.getCell(recCost_column).getNumericCellValue();
			double factorySalePrice = row.getCell(factoryPrice_column).getNumericCellValue();
			double discount = row.getCell(discount_column).getNumericCellValue();
			double wholePrice = row.getCell(wholePrice_column).getNumericCellValue();
			String productCode = ExcelUtil.getPuzzleString(row.getCell(productCode_column));
			
			String yearS = ExcelUtil.getPuzzleString(row.getCell(year_column));
			Year year = yearDaoImpl.getYearByName(yearS);
			
			String quarterS = ExcelUtil.getPuzzleString(row.getCell(quarter_column));
			Quarter quarter = quarterDaoImpl.getByName(quarterS);
			
			String brandS = ExcelUtil.getPuzzleString(row.getCell(brand_column));
			int brandId = Integer.parseInt(brandS.split(SPLITTER)[1]);
			Brand brand = brandDaoImpl.get(brandId, true);
			
			HSSFCell categoryCell = row.getCell(category_column);
			String categoryString = categoryCell.getStringCellValue().trim();
			int categoryId = Integer.parseInt(categoryString.split(SPLITTER)[1]);
			Category category = categoryDaoImpl.get(categoryId, true);

			Product product = new Product();
			product.setArea(area);
			product.setSerialNum(prodNum);

			product.setBrand(brand);
			product.setYear(year);
			product.setQuarter(quarter);
			product.setCategory(category);
			product.setChainStore(null);
			product.setCreateDate(Common_util.getToday());
			product.setProductCode(productCode);
			product.setDiscount(discount);
			product.setSalesPrice(salePrice);
			product.setWholeSalePrice(wholePrice);
			product.setUnit(productUnitString);
			product.setNumPerHand(numPerHand);
			product.setSalesPriceFactory(factorySalePrice);
			product.setRecCost(recCost);
			
			HSSFCell colourCell1 = row.getCell(color_column);
			Color color = null;
			if (colourCell1 != null){
			    String colorsString = colourCell1.getStringCellValue().trim();
				if (colorsString.equals(""))
					continue;
				else {
					int colorId = Integer.parseInt(colorsString.split(SPLITTER)[1]);
					color = colorDaoImpl.get(colorId, true);
				}
			}
				
			List<Object> rowData = new ArrayList<Object>();
			rowData.add(product);
			rowData.add(color);
			rowData.add(barcode);
			
			wsData.add(rowData);

		}
	}

	public boolean isSuccess() {
		return isSuccess;
	}


	public String getValidateMsg() {
		return validateMsg;
	}

	public List<Object> getWsData() {
		return wsData;
	}

}
