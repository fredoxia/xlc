<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.headQ.barcodeGentor.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>禧乐仓连锁店管理信息系统</title>
<script>

</script>
</head>
<body>
<script>
function saveBrand(){
	var brandName = $("#brand_Name").val();
	var supplier = $("#supplier").val();
	if (brandName =="" || supplier =="")
		alert("品牌名称 和 供应商都不应该为空");
	else {
		var params=$("#updateBrandForm").serialize();
    	$.post("barcodeGenJSON!updateBrand",params, updateBrandBKProcess,"json");	
	}
}
function updateBrandBKProcess(data){
	var response = data.response;
	var returnCode = response.returnCode;
	if (returnCode != SUCCESS)
		alert(response.message);
	else {
		$.modalDialog.handler.dialog('close');
		$("#dataGrid").datagrid('reload');
	}		
}
</script>
    <s:form id="updateBrandForm" name="updateBrandForm" method="post" action="action/basicData!saveUpdateBrand" theme="simple" onsubmit="return validateForm();">
	    <table width="100%" border="0">
	       <tr class="PBAOuterTableTitale">
	          <td colspan="2">新建/更新品牌信息</td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td>品牌名称    :</td><td>
	          <s:hidden name="formBean.brand.brand_ID"/>
	          <s:textfield id="brand_Name" name="formBean.brand.brand_Name" required="required"  maxlength="20"/>*</td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td>供货商         :</td><td><s:textfield id="supplier" name="formBean.brand.supplier" required="required"  maxlength="20"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td>所属         :</td><td>
	                               <s:property value="formBean.brand.chainStore.chain_name"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	       <td colspan="2">
	          <a onclick="saveBrand();" href="javascript:void(0);" class="easyui-linkbutton">保存</a>
	       </td>
	       </tr>
	    </table>
	    </s:form>

	      
</body>
</html>