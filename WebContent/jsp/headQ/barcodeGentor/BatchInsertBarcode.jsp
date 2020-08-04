<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>禧乐仓连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>

<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function save(){
	if ($("#inventory").val() == "")
		$.messager.alert('失败信息', "批量增加条码文件不能为空",'error');
	else {
		$.messager.progress({
			title : '提示',
			text : '数据处理中，请稍后....'
		});
    	document.barcodeForm.action="productJSPAction!batchInsertBarcode";
    	document.barcodeForm.submit();
	}
}
</script>
</head>
<body>

    <s:form action="/action/productJSPAction!batchInsertBarcode" method="POST" name="barcodeForm" id="barcodeForm" enctype="multipart/form-data" theme="simple">

    <table width="85%" align="center"  class="OuterTable">
	    <tr><td>
	         <div class="errorAndmes"><s:actionmessage cssStyle="color:blue"/></div>
			 <table width="100%" align="left" border="0">
			 	       <tr class="PBAOuterTableTitale" align="left">
	          				<th colspan="4">批量导入条码信息 <br/>
	          				- 请按照格式导入,任何一个错误条码都将导致所有新增条码失败
	          				- 请直接从千禧宝贝系统导出条码
	          				</th>
	       				</tr>
				           <tr class="InnerTableContent">
					         <td>条码文件</td>
					         <td><input type="file" name="formBean.inventory" id="inventory"/></td>
					         <td height="40"></td>
					         <td></td>
				           </tr>
						   <tr class="InnerTableContent">
						    <td height="25" align='left'>&nbsp;</td>
						    <td align='left' colspan="3">
						      <s:if test="#session.LOGIN_USER.containFunction('productJSPAction!batchInsertBarcode')">
						           <a href="#" id="saveButton" class="easyui-linkbutton" onclick="save();">上传导入条码</a>
						      </s:if>				
						    </td>
					      </tr>
			</table>
			<div class="errorAndmes"><s:actionerror cssStyle="color:red"/></div>
	   </td></tr>
	 </table>
	 
	 </s:form>

</body>
</html>