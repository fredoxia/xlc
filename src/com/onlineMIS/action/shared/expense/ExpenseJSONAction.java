package com.onlineMIS.action.shared.expense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.chainS.report.ChainReport;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.converter.JSONSQLDateConverter;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;

@Controller
public class ExpenseJSONAction extends ExpenseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1155815720822402087L;


	private Map<String,Object> jsonMap = new HashMap<String, Object>();
	private JSONObject jsonObject;
	private JSONArray jsonArray;
	public JSONArray getJsonArray() {
		return jsonArray;
	}
	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}
	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	/**
	 * 创建 expense headq
	 * @return
	 */
	public String createExpenseHeadq(){
		UserInfor userInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
    	
    	loggerLocal.info(this.getClass().getName()+ "."+"createExpenseHeadq");
    	Response response = new Response();
    	try {
    	   response = expenseService.createExpenseHeadq(userInfor, formBean.getExpense());
    	} catch (Exception e){
    	   response.setFail(e.getMessage());
    	}
    	
		try{
			jsonObject = JSONObject.fromObject(response);
		} catch (Exception e){
			loggerLocal.error(e);
		}
		
    	return SUCCESS;
	}
	
	/**
	 * 创建 expense headq
	 * @return
	 */
	public String changeParentExpenseType(){
		UserInfor userInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
    	
    	loggerLocal.info(this.getClass().getName()+ "."+"changeExpenseType1");
    	Response response = new Response();
    	try {
    	   response = expenseService.changeHeadqExpenseType1(formBean.getParentType().getId());
    	} catch (Exception e){
    	   response.setFail(e.getMessage());
    	}
    	
		try{
			jsonArray = JSONArray.fromObject(response.getReturnValue());
		} catch (Exception e){
			loggerLocal.error(e);
		}
		
    	return "jsonArray";
	}
	
	/**
	 * update expense
	 * @return
	 */
	public String updateExpenseHeadq(){
		UserInfor userInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		loggerLocal.info(this.getClass().getName()+ "."+"updateExpenseHeadq");
    	Response response = new Response();
    	try {
    	   response = expenseService.updateExpenseHeadq(userInfor,formBean.getExpense());
    	} catch (Exception e){
    	   response.setFail(e.getMessage());
    	}
    	
		try{
			jsonObject = JSONObject.fromObject(response);
		} catch (Exception e){
			loggerLocal.error(e);
		}
		
    	return SUCCESS;
	}
	
	/**
	 * 删除某一条expense
	 * @return
	 */
	public String deleteExpenseHeadq(){
		UserInfor userInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		loggerLocal.info(this.getClass().getName()+ "."+"deleteExpenseChain");
    	Response response = new Response();
    	try {
    	   response = expenseService.deleteExpenseHeadq(userInfor, formBean.getExpense().getId());
    	} catch (Exception e){
    	   e.printStackTrace();
    	   response.setFail(e.getMessage());
    	}
    	
		if (response.getReturnCode() == Response.SUCCESS){
			try {
				jsonObject = JSONObject.fromObject(response);
			} catch (Exception e) {
				loggerLocal.error(e);
				response.setReturnCode(Response.FAIL);
			}
		}
		
    	return SUCCESS;
	}
	
	public String searchExpenseHeadq(){
		UserInfor userInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		loggerLocal.info(this.getClass().getName()+ "."+"searchExpenseHeadq");
		
    	Response response = new Response();
    	try {
    	   response = expenseService.searchExpenseHeadq(formBean.getStartDate(), formBean.getEndDate(), this.getPage(), this.getRows());
    	} catch (Exception e){
    	   e.printStackTrace();
    	   response.setFail(e.getMessage());
    	}
    	
		if (response.getReturnCode() == Response.SUCCESS){
			jsonMap = (Map)response.getReturnValue();
			JsonConfig jsonConfig = new JsonConfig();
    		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
    		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());  
			try {
				jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
			} catch (Exception e) {
				loggerLocal.error(e);
				response.setReturnCode(Response.FAIL);
			}
		}
		
    	return SUCCESS;
	}
	
	/**
	 * 创建expense
	 * @return
	 */
	public String saveExpenseChain(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"saveExpenseChain");
    	Response response = new Response();
    	try {
    	   response = expenseService.createExpenseChain(userInfor,formBean.getChainStore(), formBean.getExpense());
    	} catch (Exception e){
    	   response.setFail(e.getMessage());
    	}
    	
		try{
			jsonObject = JSONObject.fromObject(response);
		} catch (Exception e){
			loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"saveExpenseChain");
			loggerLocal.error(e);
		}
		
    	return SUCCESS;
	}

	public String searchExpenseChain(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"searchExpenseChain");
    	Response response = new Response();
    	try {
    	   response = expenseService.searchExpenseChain(formBean.getChainStore(), formBean.getStartDate(), formBean.getEndDate(), this.getPage(), this.getRows());
    	} catch (Exception e){
    	   e.printStackTrace();
    	   response.setFail(e.getMessage());
    	}
    	
		if (response.getReturnCode() == Response.SUCCESS){
			jsonMap = (Map)response.getReturnValue();
			JsonConfig jsonConfig = new JsonConfig();
    		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
    		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JSONSQLDateConverter());  
			try {
				jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
			} catch (Exception e) {
				loggerLocal.error(e);
				response.setReturnCode(Response.FAIL);
			}
		}
		
    	return SUCCESS;
	}
	
	/**
	 * 删除某一条expense
	 * @return
	 */
	public String deleteExpenseChain(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"deleteExpenseChain");
    	Response response = new Response();
    	try {
    	   response = expenseService.deleteExpenseChain(userInfor, formBean.getExpense().getId());
    	} catch (Exception e){
    	   e.printStackTrace();
    	   response.setFail(e.getMessage());
    	}
    	
		if (response.getReturnCode() == Response.SUCCESS){
			try {
				jsonObject = JSONObject.fromObject(response);
			} catch (Exception e) {
				loggerLocal.error(e);
				response.setReturnCode(Response.FAIL);
			}
		}
		
    	return SUCCESS;
	}
	
	/**
	 * 汇总消费报表
	 * @return
	 */
	public String expensReportChain(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		loggerLocal.info(this.getClass().getName()+ ".expensReportChain");
		Response response = new Response();

		try {
		    response = expenseService.getExpenseReportChain(userInfor, formBean.getChainStore(), formBean.getStartDate(), formBean.getEndDate(), formBean.getExpenseRptLevel());
		} catch (Exception e){
			e.printStackTrace();
		}	
		
		try{
			   jsonArray = JSONArray.fromObject(response.getReturnValue());
//			   System.out.println(jsonArray);
			} catch (Exception e){
				e.printStackTrace();
			}	
		
		return "jsonArray";
	}
	
	/**
	 * 更新expense
	 * @return
	 */
	public String updateExpenseChain(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"updateExpenseChain");
    	Response response = new Response();
    	try {
    	   response = expenseService.updateExpenseChain(userInfor,formBean.getExpense());
    	} catch (Exception e){
    	   response.setFail(e.getMessage());
    	}
    	
		try{
			jsonObject = JSONObject.fromObject(response);
		} catch (Exception e){
			loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"updateExpenseChain");
			loggerLocal.error(e);
		}
		
    	return SUCCESS;
	}
}
