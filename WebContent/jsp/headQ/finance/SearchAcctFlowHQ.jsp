<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,com.onlineMIS.ORM.entity.headQ.finance.ChainAcctFlowReportItem" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>禧乐仓连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<script>
var ITEM_TYPE_P = "<%=ChainAcctFlowReportItem.ITEM_TYPE_PURCHASE%>";
var ITEM_TYPE_F = "<%=ChainAcctFlowReportItem.ITEM_TYPE_FINANCE%>";
$(document).ready(function(){
	parent.$.messager.progress('close'); 

	var params= $.serializeObject($('#SalesOrderSearchForm'));
	
	$('#dataGrid').datagrid({
		url : 'financeHQJSON!searchAcctFlow',
		queryParams: params,
		fit : true,
		fitColumns : true,
		border : false,
		checkOnSelect : false,
		selectOnCheck : false,
		singleSelect:true,
		rownumbers:true,
		nowrap : false,
		columns : [ [
					{field:'date', width:100,title:'单据日期'},
					{field:'chainName', width:80,title:'客户',fixed:true,
						formatter: function (value, row, index){
							return row.cust.name;
						}},
					{field:'itemTypeName', width:60,title:'单据种类'},
					{field:'id', width:50,title:'单据号'},
					{field:'quantity', width:60,title:'单据数量',
						formatter: function (value, row, index){
							return parseQuantity(row.quantity);
						}},
					{field:'amount', width:60,title:'单据金额'},
					{field:'comment', width:160,title:'单据摘要'},
					{field:'acctIncrease', width:60,title:'应收增加',
						formatter: function (value, row, index){
							return parseNumberValue(row.acctIncrease);
						}},
					{field:'acctDecrease', width:65,title:'应收减少',
						formatter: function (value, row, index){
							return parseNumberValue(row.acctDecrease);
						}},
					{field:'postAcct', width:90,title:'+应收/-应付金额',
						formatter: function (value, row, index){
							return (row.postAcct).toFixed(0);
						}},
					{field:'action', width:65,title:'',
						formatter: function (value, row, index){
							var url = "";
							var id = row.id;
							if (row.itemType == ITEM_TYPE_P)
								  url = "inventoryOrder!loadOrder?formBean.order.order_ID=" + id ;
							else if (row.itemType == ITEM_TYPE_F)
								  url = "financeHQJSP!getFHQ?formBean.order.id="+id;
							
							var str = '';
							if (row.id != 0)
							    str += $.formatString('<a href="#" onclick="addTab3(\'{0}\',\'{1}\');"><img border="0" src="{2}" title="查看"/></a>', url, row.itemTypeName + id, '<%=request.getContextPath()%>/conf_files/easyUI/themes/icons/text_1.png' );
							return str;
						}}
			     ]],
					toolbar : '#toolbar',
		});
	});

function searchAcctFlow(){
	var financeBillChainStore = $("#clientID").val();

	if (financeBillChainStore == 0){
		$.messager.alert('警告', '客户  - 不能为空', 'warning');
		return ;
	}
	var params= $.serializeObject($('#SalesOrderSearchForm'));
	$('#dataGrid').datagrid('load',params); 
}
function downloadAcctFlow(){
	if ($("#clientID").val()==0 || $("#clientID").val()==""){
		$.messager.alert('警告', '客户  - 不能为空', 'warning');
		return ;
	}
	document.SalesOrderSearchForm.action="headqReportJSP!downloadCustAcctFlowExcel";
	document.SalesOrderSearchForm.submit();
}
function changeChainStore(chainId){

}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',border:false" style="height: 90px;">
               <s:form id="SalesOrderSearchForm" name="SalesOrderSearchForm" action="/actionChain/salesAction!searchOrders" theme="simple" method="POST"> 
                <table width="100%" border="0">
			    <tr class="InnerTableContent">
			      <td width="45" height="25">&nbsp;</td>
			      <td width="76"><strong>客户</strong></td>
			      <td width="284"><%@ include file="../include/ClientInput.jsp"%>
			      				  
			      </td>
			      <td width="71"></td>
			      <td width="200"></td>
			      <td></td>
			    </tr>				
			    <tr class="InnerTableContent">
			      <td height="25">&nbsp;</td>
			      <td><strong>日期</strong></td>
			      <td>
			      	 <s:textfield id="startDate" name="formBean.searchStartTime" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>			      
			      			&nbsp; 至&nbsp;
			         <s:textfield id="endDate" name="formBean.searchEndTime" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>	
				  </td>
			      <td></td>
			      <td></td>
			      <td></td>
			  </table>
             </s:form>
		  </div>
		  <div data-options="region:'center',border:false">
			    <table id="dataGrid">			       
		        </table>
		        <div id="toolbar" style="display: none;">
			         <a onclick="searchAcctFlow();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询往来账目</a>
			         <a onclick="downloadAcctFlow();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">下载往来账目</a>
	            </div>
		  </div>
	</div>	
</body>
</html>