package com.onlineMIS.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.Collator;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;




import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;


public class Common_util {
	public static final DecimalFormat pf = new DecimalFormat("#0.0%");
	public static final DecimalFormat df = new DecimalFormat("#.00");
	public static final DecimalFormat df2 = new DecimalFormat("#.0");
	public static  final SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd");
	public static  final SimpleDateFormat dateFormat_f =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static  final Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}");
	public static  final Pattern pattern_f = Pattern.compile("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}");
	public static  final Pattern pattern_productCode = Pattern.compile("^[0-9][0-9]-[A-Za-z0-9-]+");
	public static HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();  
    static {
		hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);   
	    hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);  
    }

	
	public static final String LOGIN_USER = "LOGIN_USER";
	public static final String LOGIN_CHAIN_USER = "LOGIN_CHAIN_USER";
	public static final String IS_CHAIN_BOSS = "IS_CHAIN_BOSS";
	public static final String AREA_LIST = "AREA_LIST";
	public static final String YEAR_LIST = "YEAR_LIST";
	public static final String QUARTER_LIST = "QUARTER_LIST";
	public static final String BRAND_LIST = "BRAND_LIST";
	public static final String CATEGORY_LIST = "CATEGORY_LIST";
	public static final String UNIT_LIST = "UNIT_LIST";
	public static final String NUM_PER_HAND_LIST = "NUM_PER_HAND_LIST";
	
	public static final String AREA = "AREA";
	public static final String YEAR = "YEAR";
	public static final String QUARTER = "QUARTER";
	public static final String BRAND= "BRAND";
	public static final String CATEGORY = "CATEGORY";
	public static final String CUSTOMER = "CUSTOMER";
	
	public static final String CUSTOMER_DB = "CUSTOMER_DB";
	public static final String AREA_DB = "AREA_DB";
	
	public static final String ALL_USER = "ALL_USER";
	public static final String SYSTEM_LOG_LIST = "SYSTEM_LOG_LIST";
	public static final String PREVIEW_ORDER = "PREVIEW_ORDER";
	public static final String INVENTORY_ORDER = "INVENTORY_ORDER";
	
	public static final String CHAIN_SALES_REPORT_RESULT = "CHAIN_SALES_REPORT_RESULT";
	
	public static final String ERROR_PRICE_INCONSISTANT = "ERROR_PRICE_INCONSISTANT";
	public static final String ERROR_NOT_FOUND = "ERROR_NOT_FOUND";
	public static final String ERROR_PRODUCT_CODE_INCONSISTANT = "ERROR_PRODUCT_CODE_INCONSISTANT";
	
	public static final int ALL_RECORD = -1;
	/**
	 * 在新的程序，不输入值默认就是全部
	 */
	public static final int ALL_RECORD_NEW = 0;
	public static final int NULL_VALUE = -2;
	/**
	 * 每周排多少个名次,显示出去
	 */
	public static final int WEEK_RANK = 7;
	/**
	 * 日销售，前几名连锁店
	 */	
	public static final int DAILY_TOP_AVG_SALES_NUM = 5;
	/**
	 * 每周需要排名的热销品牌个数和产品个数
	 */
	public static final int HOT_BRAND_NUM = 15;
	public static final int HOT_PRODUCT_NUM = 6;
	
	public static final int MONTHLY_HOT_PRODUCT_NUM = 8;
	
	/**
	 * vip 日, 每月八号
	 */
	public static final int VIP_DATE = 8;
	/**
	 * vip 积分换现金
	 */
	public static final double VIP_CASH_RATIO = 0.01;
	
	/**
	 * chain store list的查看等级
	 * 	      1. 严格 只能查看当前登录连锁店
	 *        2. 中等 owner账号可以查看关联连锁店, 其他账号不行
	 *        3. 松 所有账号都可以查看关联连锁店
	 *        4. 严格 只能看当前登录连锁店和子连锁店
	 */
	public static final int CHAIN_ACCESS_LEVEL_1 = 1;
	public static final int CHAIN_ACCESS_LEVEL_2 = 2;
	public static final int CHAIN_ACCESS_LEVEL_3 = 3;
	public static final int CHAIN_ACCESS_LEVEL_4 = 4;
    /**
     * common variables such as row background
     */
	public static final String EVEN_ROW_BG_STYLE = "background-color: rgb(255, 250, 208);";
	public static final String CANCEL_ROW_FONT_COLOR = "color: red;";
	public static final String DRAFT_ROW_FONT_COLOR = "color: blue;";
	
	public static final String[] BASE_ORDER_ATTR_FILTER = new String[]{"typeHQMap","typeChainMap","statusMap"} ;

	/**
	 * 保存自动扎帐的轮次，一般一年一次
	 */
	public static final int AUTO_BAR_ACCT_ROUND = 1;
	

	
	
	/**
	 * sales analysis report的必要参数
	 * SALES_ANALYSIS_BAD_ORDER 每单超过一定数量，认定为统计废单
	 * SALES_ANALYSIS_BAD_STORE 一段时间内，准确单没有达到一定数量，不予统计
	 */
	public static final int SALES_ANALYSIS_BAD_ORDER = 20;
	public static final int SALES_ANALYSIS_BAD_STORE = 5;
	

	public static final List<String> getMonth(){
		List<String> months = new ArrayList<String>();
		for (int i = 1; i <=12; i++)
			months.add(String.valueOf(i));
		return months;
	}
	
	public static int compareString(String a, String b){
		 a = a.toUpperCase();
		 b = b.toUpperCase();
		 int size_a = a.length();
		 int size_b = b.length();
		 int size;
		 if (size_a < size_b)
			size = size_a;
		 else 
			size = size_b;
		 
		 int result = 0;
		 Comparator<Object>  cmp = Collator.getInstance(java.util.Locale.CHINA);
		 for (int i =0; i < size; i++){
			 char a_char = a.charAt(i);
			 char b_char = b.charAt(i);
			 
			result = cmp.compare(String.valueOf(a_char), String.valueOf(b_char));
           if (result != 0)
           	break;
		 }
		 
		 if (result == 0){
			 if (size_a < size_b)
				 return -1;
			 else if (size_a == size_b)
				 return 0;
			 else
				 return 1;
		 } else {
			 return result;
		 }
	 }
	
	public static Double roundDouble(double val, int precision) {   
		  Double ret = null;   
		        try {   
		           double factor = Math.pow(10, precision);   
		           ret = Math.floor(val * factor + 0.5) / factor;   
		        } catch (Exception e) {   
		           e.printStackTrace();
		           return val;
		        }   
		       return ret;   
	} 
	
	public static Date formStartDate(Date date){
		Date formatDate = new Date(date.getTime());

		formatDate.setHours(0);
		formatDate.setMinutes(0);
		formatDate.setSeconds(0);

		return formatDate;
	}

	public static Date formEndDate(Date date){
		Date formatDate = new Date(date.getTime());

		formatDate.setHours(23);
		formatDate.setMinutes(59);
		formatDate.setSeconds(59);
		return formatDate;
	}
	
	public static String getOrderTypeClient(int type_i){
		if (type_i == InventoryOrder.TYPE_SALES_ORDER_W)
			return "采购入库";
		else if  (type_i == InventoryOrder.TYPE_SALES_RETURN_ORDER_W)
			return "采购退货";
		else 
			return "错误单据";
	}
	
	public static Map<Integer, String> getGender(){
		Map<Integer, String> genderMap = new HashMap<Integer, String>();
		genderMap.put(1, "男");
		genderMap.put(2, "女");
		return genderMap;
	}
	
	public static Map<String, Integer> getGenderS(){
		Map<String, Integer> genderMap = new HashMap<String, Integer>();
		genderMap.put("男" , 1 );
		genderMap.put("女" , 2 );
		return genderMap;
	}
	
	public static String getBarcode(String barcode){
		int prefixLeng = 12 - barcode.length();
		StringBuffer barBuffer = new StringBuffer(barcode);

		for (int i = 0;  i < prefixLeng; i++){
			barBuffer.insert(0, '0');
		}
		
		return barBuffer.toString();
	}
	
	/**
	 * after get the today date and calculate its expire date
	 * @param today
	 * @return
	 */
	public static Date calculateVIPCardExpireDate(Date today){
		int year = today.getYear();
		
		Date newDate = new Date(today.getTime());
		newDate.setYear(year + 2);
		
		return newDate;
	}
	
	public static Date calcualteDate(Date day, int diffDay){
		int date = day.getDate();
		
		Date newDate = new Date(day.getTime());
		newDate.setDate(date + diffDay);
		
		return newDate;
	}
	
	public static String decode(String var){
		String var_decode = "";
		try {
			var_decode =  java.net.URLDecoder.decode(var,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			var_decode = var;
			loggerLocal.error(e);
		}
		return var_decode;
	}
	
	public static String encodeAttachment(String originalStr){
		try {
			return java.net.URLEncoder.encode(originalStr,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return originalStr;
		} 
	}
	
	public static boolean isTrue(){
		double a = Math.random();
		double b = 0;
		if (a >= b)
			return true;
		else 
			return false;
	}
	
	public static double getDecimalDouble(double value){
		return Double.parseDouble(df.format(value));
	}

	public static double getDouble(Object object) {
		if (object == null)
		    return 0;
		else {
			return Double.parseDouble(object.toString());
		}
	}
	
	public static java.sql.Date getDate(Object object) {
		if (object == null)
		    return null;
		else {
			return (java.sql.Date)object;
		}
	}
	
	public static int getInt(Object object) {
		if (object == null)
		    return 0;
		else {
			return Integer.parseInt(object.toString());
		}
	}
	
	public static String getString(Object object){
		if (object == null)
		    return null;
		else {
			return object.toString().trim();
		}
	}
	
	/**
	 * function to get the pinyin code's first combination
	 * etc: 我是中国人 -> wszgr
	 *      我是:中国人 -> wszgr
	 *      我是:chinese -> wschinese
	 * @param src
	 * @return
	 */
	public static String getPinyinCode(String src, boolean isCapital){
		StringBuffer pinyinCodes = new StringBuffer("");
		
		if (src != null) {
			char[] charArray = src.toCharArray();
			for (char charc: charArray){
				if(String.valueOf(charc).matches("[\\u4E00-\\u9FA5]+")){  
					try {
						String[] pinyinStrings = PinyinHelper.toHanyuPinyinStringArray(charc, hanYuPinOutputFormat);
						char[] pinyinCode = pinyinStrings[0].toCharArray();
						pinyinCodes.append(pinyinCode[0]);
						
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						loggerLocal.error("Error to get the Pinyin for : " + src + " , " + charc);
					}
				} else if(((int)charc>=65 && (int)charc<=90) || ((int)charc>=97 && (int)charc<=122) || ((int)charc>=48 && (int)charc<=57)){  
					pinyinCodes.append(charc);
				}
			}
		}
		
		if (isCapital)
		    return pinyinCodes.toString().toUpperCase();
		else 
			return pinyinCodes.toString();
		
	}
	
	/**
	 * to get the projections' single value
	 * @param values
	 * @return
	 */
	public static int getProjectionSingleValue(List<Object> values){
		if (values != null && values.size() >0 && values.get(0) != null){
			return Integer.valueOf(values.get(0).toString());
		} else 
			return 0;
	}

	/**
	 * to get the projections' sum value
	 * @param values
	 * @return
	 */
	public static double getProjectionDoubleValue(List<Object> values){
		if (values != null && values.size() >0 && values.get(0) != null){
			return Double.valueOf(values.get(0).toString());
		} else 
			return 0;
	}

	public static java.sql.Date getToday() {
		Date todayDate = new Date();
		java.sql.Date today = new java.sql.Date(todayDate.getTime());
		return today;
	}
	
	public static java.sql.Date getYestorday(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		java.sql.Date today = new java.sql.Date(calendar.getTimeInMillis());
		return today;
	}
	
	public static java.sql.Date getLastYearDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1);
		java.sql.Date today = new java.sql.Date(calendar.getTimeInMillis());
		return today;
	}
	
	public static java.sql.Date getDate(int offSet){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, offSet);
		java.sql.Date date = new java.sql.Date(calendar.getTimeInMillis());
		return date;
	}

	public static List<java.sql.Date> getDateBetween(java.sql.Date startDate,
			java.sql.Date endDate) {
		List<java.sql.Date> dates = new ArrayList<java.sql.Date>();
		
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		
		while (!start.after(end)){
			java.sql.Date day = new java.sql.Date(start.getTimeInMillis());
			
			dates.add(day);
			
			start.add(Calendar.DAY_OF_MONTH, 1);
		}
		return dates;
	}
	
	public static java.sql.Date getLastMonthDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		java.sql.Date today = new java.sql.Date(calendar.getTimeInMillis());
		return today;
	}
	

	/**
	 * 计算chart的宽度
	 * @param xElements
	 * @return
	 */
	public static int calculateChartWidth(int xElements) {
		if (xElements < 20)
			return 700;
		else if (xElements > 90)
			return 1400;
		else 
			return 700 + (xElements - 20) * 10;
	}

	/**
	 * 验证date2比baseD至少早一天否
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isBefore(Date baseD, Date date2) {
		long baseT = baseD.getTime();
		long time2 = date2.getTime();
		long diff = (baseT - time2)/ (24 * 60 * 60 * 1000);
		if (diff > 0)
			return true;
		else 
		    return false;
	}
	
	public static List<java.sql.Date> getWeekDays(Date thisDate){
		List<java.sql.Date> dates = new ArrayList<java.sql.Date>();
		Calendar calDay = Calendar.getInstance();
		calDay.setTimeInMillis(thisDate.getTime());
		calDay.setFirstDayOfWeek(Calendar.MONDAY);
		calDay.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		for (int i = 0; i <7; i++){
			dates.add(new java.sql.Date(calDay.getTimeInMillis()));
			calDay.add(Calendar.DAY_OF_MONTH, 1);
		}

		return dates;
	}

	public static List<java.sql.Date> getLastWeekDays() {
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_MONTH, -7);

		return getWeekDays(today.getTime());
	}
	
	/**
	 * 上个周一
	 * @return
	 */
	public static Date getLastMonday() {
		Calendar today = Calendar.getInstance();
		today.setFirstDayOfWeek(Calendar.MONDAY);
		today.add(Calendar.DAY_OF_MONTH, -7);
		today.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return new Date(today.getTimeInMillis());
	}
	
	public static java.sql.Date getMonday(java.sql.Date day){
		Calendar today = Calendar.getInstance();
		today.setFirstDayOfWeek(Calendar.MONDAY);
		today.setTimeInMillis(day.getTime());
		today.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		return new java.sql.Date(today.getTimeInMillis());
	}
	

	


	
	public static double formatDouble(double beforeValue, DecimalFormat df){
		String stringValue = df.format(beforeValue);
		return Double.valueOf(stringValue);
	}


	public static int getFirstRecord(int page, int rowPerPage) {
		return rowPerPage * (page -1);
	}

	
	public static String getHeadqTraceActionDesp(String actionCode) {
		String actionDesp = "*错误*";
		if (actionCode.equals("SP"))
			actionDesp = "采购";
		else if (actionCode.equals("SR"))
			actionDesp = "采购退货";
		else if (actionCode.equals("RS"))
			actionDesp = "批发销售";
		else if (actionCode.equals("RR"))
			actionDesp = "批发退货";
		else if (actionCode.equals("CO"))
			actionDesp = "盘点报溢";
		else if (actionCode.equals("CL"))
			actionDesp = "盘点报损";
		else if (actionCode.equals("CSP"))
			actionDesp = "采购红冲";
		else if (actionCode.equals("CSR"))
			actionDesp = "采购退货红冲";
		else if (actionCode.equals("CRS"))
			actionDesp = "批发销售红冲";
		else if (actionCode.equals("CRR"))
			actionDesp = "批发退货红冲";
		else if (actionCode.equals("CCO"))
			actionDesp = "盘点报溢红冲";
		else if (actionCode.equals("CCL"))
			actionDesp = "盘点报损红冲";
		else if (actionCode.equals(ChainInOutStock.AUTO_BAR_ACCT))
			actionDesp = "系统扎帐汇总";

		return actionDesp;
	}
	
	public static String getTraceActionDesp(String actionCode) {
		String actionDesp = "*错误*";
		if (actionCode.equals("HS"))
			actionDesp = "总部采购";
		else if (actionCode.equals("HR"))
			actionDesp = "总部退货";
		else if (actionCode.equals("CS"))
			actionDesp = "零售销售-/退货+";
		else if (actionCode.equals("CT"))
			actionDesp = "调货";
		else if (actionCode.equals("CO"))
			actionDesp = "盘点报溢";
		else if (actionCode.equals("CL"))
			actionDesp = "盘点报损";
		else if (actionCode.equals("HSC"))
			actionDesp = "总部采购红冲";
		else if (actionCode.equals("HRC"))
			actionDesp = "总部退货红冲";
		else if (actionCode.equals("CSC"))
			actionDesp = "零售红冲销售+/退货-";
		else if (actionCode.equals("CTC"))
			actionDesp = "调货红冲";
		else if (actionCode.equals("COC"))
			actionDesp = "盘点报溢红冲";
		else if (actionCode.equals("CLC"))
			actionDesp = "盘点报损红冲";
		else if (actionCode.equals(ChainInOutStock.AUTO_BAR_ACCT))
			actionDesp = "系统自动扎帐汇总";

		return actionDesp;
	}

	/**
	 * 获取today周的最前和最后日
	 * @param today
	 * @return
	 */
	public static List<java.sql.Date> getWeekDate(java.util.Date today) {
		List<java.sql.Date> dates = new ArrayList<java.sql.Date>();
		Calendar calDay = Calendar.getInstance();
		calDay.setTimeInMillis(today.getTime());
		calDay.setFirstDayOfWeek(Calendar.MONDAY);
		calDay.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		dates.add(new java.sql.Date(calDay.getTimeInMillis()));
	    calDay.add(Calendar.DATE, 6);
	    dates.add(new java.sql.Date(calDay.getTimeInMillis()));
			
		return dates;
	}
	
	/**
	 * 获取today月的最前和最后日
	 * @param today
	 * @return
	 */
	public static List<java.sql.Date> getMonthDate(java.util.Date today) {
		List<java.sql.Date> dates = new ArrayList<java.sql.Date>();
		Calendar calDay = Calendar.getInstance();
		calDay.setTimeInMillis(today.getTime());
		calDay.set(Calendar.DAY_OF_MONTH, 1);
		
		dates.add(new java.sql.Date(calDay.getTimeInMillis()));
		calDay.add(Calendar.MONTH, 1);
	    dates.add(new java.sql.Date(calDay.getTimeInMillis()));
			
		return dates;
	}
	
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}


	public static List<Integer> getList(String months) {
		List<Integer> monthList = new ArrayList<Integer>();
		if (months == null || months.trim().equals(""))
			return monthList;
		else {
			String[] monthArray = months.split(",");
			for (String month: monthArray)
				monthList.add(Integer.parseInt(month.trim()));
			return monthList;
		}
	}
	
	public static int getDateInterval(Date date1, Date date2){
		   long intervalMilli = date1.getTime() - date2.getTime();
		   
	       return (int) (intervalMilli / (24 * 60 * 60 * 1000));

	}
	
	public static Date getDateOfLastYear(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
		
		Date dateOfLastYear = new Date(calendar.getTimeInMillis());
		return dateOfLastYear;
	}
	
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
    
    /**
     * 过滤掉中文
     * @param str 待过滤中文的字符串
     * @return 过滤掉中文后字符串
     */
    public static String filterChinese(String str) {
        // 用于返回结果
        String result = str;
        boolean flag = isContainChinese(str);
        if (flag) {// 包含中文
            // 用于拼接过滤中文后的字符
            StringBuffer sb = new StringBuffer();
            // 用于校验是否为中文
            boolean flag2 = false;
            // 用于临时存储单字符
            char chinese = 0;
            // 5.去除掉文件名中的中文
            // 将字符串转换成char[]
            char[] charArray = str.toCharArray();
            // 过滤到中文及中文字符
            for (int i = 0; i < charArray.length; i++) {
                chinese = charArray[i];
                flag2 = isChinese(chinese);
                if (!flag2) {// 不是中日韩文字及标点符号
                    sb.append(chinese);
                } else 
                	break;
            }
            result = sb.toString();
        }
        return result;
    }

    /**
     * 判定输入的是否是汉字
     *
     * @param c
     *  被校验的字符
     * @return true代表是汉字
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }


    /**
     * 将产品货号-前面的字符去掉
     * 81-23003 -> 23003
     * @param productCode
     * @return
     */
    public static String cutProductCode(String productCode){
        Matcher m = pattern_productCode.matcher(productCode);
        if (m.find()) {
            return productCode.substring(productCode.indexOf("-")+1);
        }
        return productCode;
    }



	static String[] weekdays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	public static String getWeekDays(int i) {
		return weekdays[i];
	}

	public static void main(String[] args){
		Date date = Common_util.getToday();
		System.out.println(Common_util.getDateOfLastYear(date));
	}

	public static Object correctFileName(String custName) {
		return custName.replaceAll("<", "").replaceAll(">", "").replaceAll("\\?", "").replaceAll(":", "").replaceAll("\\*", "").replaceAll("|", "").replaceAll("//", "");
	}

}
