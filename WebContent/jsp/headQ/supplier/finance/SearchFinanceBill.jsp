<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>禧乐仓连锁店管理信息系统</title>
<%@ include file="../../../common/Style.jsp"%>
<link href="<%=request.getContextPath()%>/conf_files/css/pagination.css" rel="stylesheet" type="text/css"/>
<SCRIPT src="<%=request.getContextPath()%>/conf_files/js/pagenav1.1.js" type=text/javascript></SCRIPT>
<script>
function searchBills(){
	
	pageNav.clearPager();
    pageNav.fn($("#currentPage").val(),$("#totalPage").val());
}
function searchBillsBackProcess(data){
	var bills = data.bills;
	var pager = data.pager;
	
    $('#bills tr').each(function () {                
        $(this).remove();
    });

    if (bills.length != 0){
	    for (var i = 0; i < bills.length; i++){
	    	var j = i+1;
	    	var bg = "";
	    	var color = "";
	    	if ((i % 2) == 0)
	    		bg = "<%=Common_util.EVEN_ROW_BG_STYLE%>";
	    	if (bills[i].status == 3)
		    	color =  "<%=Common_util.CANCEL_ROW_FONT_COLOR%>";
	        var urlLink = "financeSupplierJSP!getFHQ?formBean.order.id=" + bills[i].id;
	        var nodeTitle = "财务单据"+ bills[i].id;
	        if (bills[i] != "")  
		          $("<tr class='InnerTableContent' style='" + bg + color +"' align='center'><td height='25'>"+
				          bills[i].id+"</td><td>"+
				          bills[i].supplier.name+"</td><td>"+
				          bills[i].typeHQS+"</td><td>"+
				          bills[i].createDate+"</td><td>"+
				          bills[i].invoiceTotal+"</td><td>"+
				          bills[i].statusS+"</td><td>"+
				          bills[i].comment+"</td><td><a href='#' onclick='addTab3(\""+urlLink+"\",\""+nodeTitle+"\")'><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a></td></tr>").appendTo("#bills");
	    }
		$("#org_table tr").mouseover(function(){      
			$(this).addClass("over");}).mouseout(function(){    
			$(this).removeClass("over");}); 
    }else {
    	$("<tr class='InnerTableContent'"+ bg +" align='center'><td colspan=7><font color='red'>对应条件没有查询信息</font> </td></tr>").appendTo("#bills");
    }

    renderPaginationBar(pager.currentPage, pager.totalPage);
    $("#billsDiv").show();
    $.messager.progress('close'); 
}
pageNav.fn = function(page,totalPage){
	$.messager.progress({
		title : '提示',
		text : '数据处理中，请稍后....'
	});
	$("#currentPage").attr("value",page);
    var params=$("#financeBillSearchForm").serialize();  
    $.post("financeSupplierJSON!searchFHQBill",params, searchBillsBackProcess,"json");	
};
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});

</script>
</head>
<body>
    <s:form id="financeBillSearchForm" name="financeBillSearchForm" action="/actionChain/inventoryFlowAction!searchInvenOrder" theme="simple" method="POST"> 
     <%@ include file="../../../common/pageForm.jsp"%>
     <table width="95%" align="center"  class="OuterTable">
	    <tr><td>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
			            <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
				   		<table width="100%" border="0">
						    <tr class="PBAOuterTableTitale">
					          <td height="51" colspan="6">搜索供应商财务单据<br />
				         		- 总部管理人员通过此功能可以查看与供应商之间的往来财务单据</td>
						    </tr>
						    <tr class="InnerTableContent">
						      <td width="50" height="25">&nbsp;</td>
						      <td width="78"><strong>开单日期</strong></td>
						      <td width="267">开始日期 ：
						      	<s:textfield id="startDate" name="formBean.searchStartTime" cssClass="easyui-datebox" data-options="width:100,editable:false"/>
						      <td width="72"><strong>供应商</strong></td>
						      <td width="393">
						      		<%@ include file="../SupplierInput.jsp"%>

							 	</td>
						      <td width="193">&nbsp;</td>
						    </tr>
							<tr class="InnerTableContent">
						      <td width="50" height="25">&nbsp;</td>
						      <td width="78">&nbsp;</td>
						      <td width="267">截止日期 ：
						         <s:textfield id="endDate" name="formBean.searchEndTime" cssClass="easyui-datebox" data-options="width:100,editable:false"/>
						      <td width="72"><strong>单据状态</strong></td>
						      <td width="393"><s:select name="formBean.order.status"  list="formBean.order.statusMap" listKey="key" listValue="value" headerKey="-1" headerValue="---全部---" /></td>
						      <td width="193">&nbsp;</td>
						    </tr>
						    <tr class="InnerTableContent">
		                       <td height="25">&nbsp;</td>
		                       <td colspan="2">&nbsp;</td>
		                       <td><strong>单据种类</strong></td>
		                       <td><s:select name="formBean.order.type"  list="formBean.order.typeHQMap" listKey="key" listValue="value" headerKey="-1" headerValue="---全部---" /></td>
		                       <td>&nbsp;</td>
				            </tr>
		
		                    <tr class="InnerTableContent">
						      <td height="20">&nbsp;</td>
						      <td colspan="2">
						               <input type="button" value="搜索单据" onclick="searchBills();"/>
						      </td>
						      <td>&nbsp;</td>
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
			         <div id="billsDiv" style="display: none">
						<table width="90%"  align="left" class="OuterTable" id="org_table">
						    <tr class="PBAInnerTableTitale">
						      <th width="40" height="32">编号</th>
						      <th width="90">供应商名字</th>
						      <th width="102">单据种类</th>
						 	  <th width="150">过账日期</th>
						      <th width="60">金额</th>
						      <th width="60">状态</th>
						      <th width="60">备注</th>
						      <th width="60"></th>
						    </tr>
						    <tbody id="bills">
						   </tbody>
						  <tr class="InnerTableContent" id="pager">	      
						      <td colspan="16"><div id="pageNav"></div></td>
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