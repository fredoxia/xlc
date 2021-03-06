<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>禧乐仓连锁店管理信息系统</title>
<%@ include file="../../../common/Style.jsp"%>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function editBill(){
	document.financeBillForm.action = "<%=request.getContextPath()%>/action/financeSupplierJSP!editFB";
	document.financeBillForm.submit();
}

function cancelBill(){
	document.financeBillForm.action = "<%=request.getContextPath()%>/action/financeSupplierJSP!cancelFB";
	document.financeBillForm.submit();
}
function updateOrderComment(){
    var url = "<%=request.getContextPath()%>/action/financeSupplierJSON!updateFinanceBillComment";
    var params=$("#financeBillForm").serialize();  
    $.post(url,params, updateOrderCommentBackProcess,"json");	
}
function updateOrderCommentBackProcess(data){
	var returnValue = data.returnCode;
	if (returnValue == SUCCESS)
	    $.messager.alert('消息', data.message, 'info');
	else 
		$.messager.alert('操作失败', data.message, 'error');
}
</script>
</head>
<body>
    <s:form action="/action/financeHQJSP!editFHQBill" method="POST" name="financeBillForm"  id="financeBillForm" theme="simple">
     <s:hidden name="formBean.order.id"/> 
	<table width="70%" align="center"  class="OuterTable">
	    <tr><td>
		 <table width="100%" border="0">
		    <tr>
		      <td colspan="7">
		      		    <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
						<table width="100%"  align="left" border="0" id="org_table">
							<tr class="PBAOuterTableTitale">
	                            <td height="50" colspan="3"> 供应商财务单据  
	                            </td>
		    				</tr>
						   <tr class="InnerTableContent">
						     <td width="180" height="35">单据种类 ： <s:property value="uiBean.order.typeHQS"/>  <s:property value="uiBean.order.id"/></td>
						     <td width="220">供应商 ： <s:property value="uiBean.order.supplier.name"/> </td>
						     <td width="220">过账日期:<s:date name="uiBean.order.createDate" format="yyyy-MM-dd" /></td>
					       </tr>
						   <tr class="InnerTableContent">	      
							 <td height="25">经手人:<s:property value="uiBean.order.creatorHq.user_name"/></td>
							 <td> 状态 ： <s:if test="uiBean.order.status == 3"><font style="color:red"><s:property value="uiBean.order.statusS"/></font></s:if><s:else>
							 				<s:property value="uiBean.order.statusS"/>
							 		   </s:else>
							 </td>
							 <td>备注 ：<input name="formBean.order.comment" id="comment" class="easyui-textbox"  style="width:300px" value="<s:property value="uiBean.order.comment"/>"> <a id="btn2" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="updateOrderComment();">修改备注</a></td>
					       </tr>
					       <tr class="InnerTableContent">	
					         <td height="25">折扣:<s:property value="uiBean.order.invoiceDiscount"/></td>      
							 <td>上欠:<s:property value="uiBean.order.preAcctAmt"/></td>
							 <td>下欠 ： <s:property value="uiBean.order.postAcctAmt"/></td>
					       </tr>
				       </table>
			      </td>
			    </tr>
			    
			    <tr class="InnerTableContent">
			      <td height="4" colspan="7"><hr width="100%" color="#FFCC00"/></td>
			    </tr>
				<tr>
				  <td><table width="100%"  align="left" class="OuterTable" id="org_table">
	                  <tr class="PBAInnerTableTitale" align='left'>
	                    <th width="60" height="35">序号</th>
	                    <th width="110">账户名称</th>
	                    <th width="120">金额</th>
	                    <th width="199">说明</th>
	                  </tr>
	                  <s:iterator value="uiBean.order.financeBillItemList" status="st" id="billItem" >
	                    <tr class="InnerTableContent" <s:if test="#st.even">style="<%=Common_util.EVEN_ROW_BG_STYLE %>"</s:if>>
	                      <td height="25"><s:property value="#st.index + 1"/></td>
	                      <td><s:property value="#billItem.financeCategorySupplier.itemName"/></td>
	                      <td><s:property value="#billItem.total"/></td>
	                      <td><s:property value="#billItem.comment"/></td>
	                    </tr>
	                  </s:iterator>
	                  <tr align='left' class="PBAInnerTableTitale">
	                    <td height="35" colspan="2">总计</td>
	                    <td><s:property value="uiBean.order.invoiceTotal"/></td>
	                    <td></td>
	                  </tr>
	                </table></td>
				</tr>
				<tr class="InnerTableContent">
			      <td height="4" colspan="7">
			          <s:if test="#session.LOGIN_USER.containFunction('financeSupplierJSP!editFB') && formBean.canEdit"> 
			                 <a id="btn2" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="editBill();">修改单据</a>
			          </s:if>
			          <s:if test="#session.LOGIN_USER.containFunction('financeSupplierJSP!cancelFB') && formBean.canCancel"> 
			                 <a id="btn2" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-no'" onclick="cancelBill();">红冲单据</a> 
			          </s:if>
			      </td>
			    </tr>
			  </table>
		   </td></tr>
	   		    <tr class="InnerTableContent">
		      <td height="4" colspan="7"><hr width="100%" color="#FFCC00"/></td>
		    </tr>
	 </table>
	 </s:form>
</body>
</html>