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
	if ($('#updateBrandForm').form('validate')){
		var params=$("#updateBrandForm").serialize();
    	$.post("basicDataJSON!updateBrand",params, updateBrandBKProcess,"json");	
	}
}
function updateBrandBKProcess(data){
	var response = data.response;
	var returnCode = response.returnCode;
	if (returnCode != SUCCESS)
		$.messager.alert('操作失败', response.message, 'error');
	else {
		$.modalDialog.handler.dialog('close');
		$("#dataGrid").datagrid('reload');
	}		
}
</script>
    <s:form id="updateBrandForm" name="updateBrandForm" cssClass="easyui-form" method="post" action="action/basicData!saveUpdateBrand" theme="simple" onsubmit="return validateForm();">
	    <table width="100%" border="0">

	       <tr class="InnerTableContent">
	          <td>品牌名称    :</td><td>
	          <s:hidden name="formBean.brand.brand_ID"/>
	          <s:textfield id="brand_Name" name="formBean.brand.brand_Name" cssClass="easyui-textbox" data-options="required:true,validType:['required','length[1,20]']"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td>供货商         :</td><td><s:textfield id="supplier" name="formBean.brand.supplier"  cssClass="easyui-textbox" data-options="required:true,validType:['required','length[1,20]']"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td>备注       :</td><td><s:textfield id="comment" name="formBean.brand.comment"  cssClass="easyui-textbox" data-options="required:true,validType:['required','length[1,12]']"/></td>
	       </tr>	       
	       <tr class="InnerTableContent">
	          <td>所属         :</td><td><s:hidden name="formBean.brand.chainStore.chain_id"/>
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