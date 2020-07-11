<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="java.util.Date,java.text.SimpleDateFormat,com.onlineMIS.common.Common_util" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>禧乐仓连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<script src="<%=request.getContextPath()%>/conf_files/js/datetimepicker_css.js" type=text/javascript></script>
<link href="<%=request.getContextPath()%>/conf_files/css/pagination.css" rel="stylesheet" type="text/css"/>
<SCRIPT src="<%=request.getContextPath()%>/conf_files/js/pagenav1.1.js" type=text/javascript></SCRIPT>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function getPurchaseDetail(id){
	window.open ('purchaseAction!getPurchase?formBean.order.order_ID='+id,'采购单据','height=800, width=1000, toolbar=no,scrollbars=yes, resizable=yes,  menubar=no, location=no, status=no');
}
pageNav.fn = function(page,totalPage){                
	$("#currentPage").attr("value",page);
    var params=$("#purchaseOrderSearch").serialize();  
    $.post("purchaseJSONAction!searchOrders",params, searchOrdersBackProcess,"json");	
};
function searchOrders(){
	resetSearchForm();
    pageNav.fn($("#currentPage").val(),$("#totalPage").val());
}
function searchOrdersBackProcess(data){
	var orders = data.orders;
	var pager = data.pager;
	
   $('#orders tr').each(function () {                
       $(this).remove();
   });

   if (orders.length != 0){
	    for (var i = 0; i < orders.length; i++){
	    	var bg = "";
	    	if ((i % 2) == 0)
	    		bg = "style='<%=Common_util.EVEN_ROW_BG_STYLE%>'";
	    		
	        if (orders[i] != "")  
		          $("<tr class='InnerTableContent'" + bg +" align='center' height='28'><td>"+
				          orders[i].id+"</td><td>"+
				          orders[i].startTime+"</td><td>"+
				          orders[i].clientName+"</td><td>"+
				          orders[i].orderTypeC+"</td><td>"+
				          orders[i].totalQ+"</td><td>"+
				          <s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')">orders[i].totalWholeSales</s:if><s:else>"-"</s:else>+"</td><td>"+
				          orders[i].totalRetailSales+"</td><td>"+
				          orders[i].comment+"</td><td>"+
				          orders[i].chainStatusS+"</td><td>"+
				          orders[i].chainConfirmComment+"</td><td>"+
				          "<s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!getPurchase')"><a href='javascript:getPurchaseDetail(" + orders[i].id+ ")'><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a></s:if><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseJSONAction!headqResetPurchaseOrderStatus')"><a href='javascript:resetOrder(" + orders[i].id+ ")'>重设单据</a></s:if></td></tr>").appendTo("#orders");
	    }
	    renderPaginationBar(pager.currentPage, pager.totalPage);
	    
		$("#org_table tr").mouseover(function(){      
			$(this).addClass("over");}).mouseout(function(){    
			$(this).removeClass("over");}); 
   }else {
   	$("<tr class='InnerTableContent'"+ bg +" align='center'><td colspan=8><font color='red'>对应条件没有查询信息</font> </td></tr>").appendTo("#orders");
   }

   $("#ordersDiv").show();
}
function changeChainStore(chainId){

}

function resetOrder(id){
	$.messager.prompt("密码验证","一旦确认,当前单据状态和库存将会重设:", function(password){
		if (password == "vj7683c688"){
			$.messager.progress({
				title : '提示',
				text : '数据处理中，请稍后....'
			});
			var param = "";
			$.post('purchaseJSONAction!headqResetPurchaseOrderStatus?formBean.order.order_ID='+id, param, 
					function(result) {
				  
						if (result.returnCode == SUCCESS) {
							$.messager.progress('close'); 
							$.messager.alert('信息', result.message, 'info');
						} else {
							$.messager.progress('close'); 
							$.messager.alert('失败警告', result.message, 'error');
						}
					}, 'JSON');
		} else {
			alert("密码错误");
		}	   
	});
}
</script>
</head>
<body>
    <s:form id="purchaseOrderSearch" name="purchaseOrderSearch" action="/actionChain/purchaseAction!searchOrders" theme="simple" method="POST">
		   <input type="hidden" id="indicator" name="formBean.indicator" value="-1"/>
			<input type="hidden" id="accessLevel" name="formBean.accessLevel" value="4"/>			
		   <%@ include file="../../common/pageForm.jsp"%>
		   <s:hidden id="chainStore" name="formBean.chainSalesOrder.chainStore.chain_id" value="-1"/>
		   <table width="100%" align="center"  class="OuterTable">
		    <tr><td>
				 <table width="100%" border="0">		    
				 <tr>
			       <td height="50" colspan="7">
			            <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
				   		<table width="100%" border="0">
						    <tr class="InnerTableContent">
						      <td width="44" height="25">&nbsp;</td>
						      <td width="82"><strong>连锁店</strong></td>
						      <td width="220"><%@ include file="../include/SearchChainStore.jsp"%>

						      </td>
						      <td width="70"><strong>单据种类</strong></td>
						      <td width="130"><s:select name="formBean.order.order_type" list="uiBean.typesMap" listKey="key" listValue="value" headerKey="-1" headerValue="---全部---" />		
						      </td>
						      <td width="70"><strong>单据状态</strong></td>
						      <td><s:select id="chainConfirmStatus" name="formBean.order.chainConfirmStatus"  list="uiBean.chainConfirmList" listKey="key" listValue="value"  headerKey="-1" headerValue="---全部---"/></td>
						    </tr>
						    <tr class="InnerTableContent">
						      <td height="40">&nbsp;</td>
						      <td><strong>总部下账日期</strong></td>
						      <td colspan="3">
						         <s:textfield id="startDate" name="formBean.search_Start_Time" cssClass="easyui-datebox" data-options="width:100,editable:false"/>			      
					      			&nbsp; 至&nbsp;
					      		 <s:textfield id="endDate" name="formBean.search_End_Time" cssClass="easyui-datebox" data-options="width:100,editable:false"/>		  
							   </td>
						      <td>&nbsp;</td>
						      <td>&nbsp;</td>
						      </tr>
						     <tr class="InnerTableContent">
					      		<td height="30">&nbsp;</td>
					      		<td><strong>包含货品</strong></td>
					      		<td colspan="5"><%@ include file="../include/SearchProduct.jsp"%></td>
					   		 </tr>	
						    <tr class="InnerTableContent">
						      <td height="15">&nbsp;</td>
						      <td>&nbsp;</td>
						      <td colspan="3"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseJSONAction!searchOrders')"><input name="submit" type="button" value="搜索单据" onclick="searchOrders();"/></s:if></td>
						      <td>&nbsp;</td>
						      <td>&nbsp;</td>
						    </tr>
						   </table>
				        </td>
				      </tr>
				      <tr class="InnerTableContent">
					      <td height="4" colspan="7"><hr width="100%" color="#FFCC00"/></td>
					  </tr>
					  <tr>
					      <td colspan="7">
					         <!-- table to display the draft order information -->
					         <div id="ordersDiv" style="display: none">
				             <!-- table to display the staff information -->
								 <table width="100%" border="0" id="org_table">
								    <tr class="PBAInnerTableTitale">
								      <th width="40" height="32">编号</th>
								      <th width="120">订单初始日期</th>
								      <th width="60">连锁店</th>
								      <th width="102">单据种类</th>
								      <th width="60">数量</th>
								      <th width="80">成本金额</th>
								      <th width="80">零售金额</th>
								      <th width="140">总部备注</th>
								      <th width="60">收货状态</th>
								      <th width="100">收货备注</th>								      
								      <th width="60">&nbsp;</th>
								    </tr>
								    <tbody id="orders">
						   			</tbody>
						   			<tr class="InnerTableContent" id="pager">	      
						      			<td colspan="10"><div id="pageNav"></div></td>
						  			</tr>	
								  </table>
							  </div>
					      </td>
					    </tr>
				  </table>
		   </td></tr>
		  </table>
      </s:form>
      
</body>
</html>