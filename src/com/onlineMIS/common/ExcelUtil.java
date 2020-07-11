package com.onlineMIS.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtil {
	
	public static Double getPuzzleNum(HSSFCell cell){
		if (cell == null)
			return null;
		
		int cellType = cell.getCellType();
		if (cellType == HSSFCell.CELL_TYPE_STRING)
			return Double.parseDouble(cell.getStringCellValue());
		else if (cellType == HSSFCell.CELL_TYPE_NUMERIC)
			return cell.getNumericCellValue();
		else 
			return null;
	}
	
	public static String getPuzzleString(HSSFCell cell){
		String value = "";
		if (cell == null)
			return null;
		
		int cellType = cell.getCellType();
		if (cellType == HSSFCell.CELL_TYPE_STRING)
			value = cell.getStringCellValue();
		else if (cellType == HSSFCell.CELL_TYPE_NUMERIC)
			value = String.valueOf((int)cell.getNumericCellValue());
		else 
			value = null;
		
		return value;
	}
	
	public static ByteArrayInputStream convertExcelToInputStream(HSSFWorkbook wb){
		ByteArrayInputStream byteArrayInputStream;   
		ByteArrayOutputStream os = new ByteArrayOutputStream();   
		try {   
		    wb.write(os);   
		} catch (IOException e) {   
			loggerLocal.error(e);
	    }   
	    byte[] content = os.toByteArray();   
	    byteArrayInputStream = new ByteArrayInputStream(content);   
	    
	    return byteArrayInputStream;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
