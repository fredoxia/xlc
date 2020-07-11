package com.onlineMIS.converter;



import java.text.SimpleDateFormat;
import java.util.Date;

import com.onlineMIS.common.Common_util;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JSONUtilDateConverter implements JsonValueProcessor {
	
	public JSONUtilDateConverter() {
		super();
	}

	public JSONUtilDateConverter(String format) {
		super();
	}

	@Override
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		String[] obj = {};
		if (value instanceof Date[]) {
			SimpleDateFormat sf = Common_util.dateFormat_f;
			Date[] dates = (Date[]) value;
			obj = new String[dates.length];
			for (int i = 0; i < dates.length; i++) {
				obj[i] = sf.format(dates[i]);
			}
		}
		return obj;
	}

	@Override
	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		if (value == null)
			value="";
		else if (value instanceof java.util.Date) {
			String str = "";
			if (value != null)
			    str =  Common_util.dateFormat_f.format((Date) value);
			return str;
		}
		return value.toString();
	}
}
