<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>禧乐仓连锁店管理信息系统</title>

</head>
<body>
<script>
function saveNumPerHand(){
	var params=$("#updateNumPerHandForm").serialize();

    $.post("basicDataJSON!updateNumPerHand",params, updateNumPerHandBKProcess,"json");	
}
function updateNumPerHandBKProcess(data){
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
    <s:form id="updateNumPerHandForm" name="updateNumPerHandForm" method="post" action="" theme="simple">
	    <table width="100%" border="0">
	       <tr>
	          <td colspan="2"></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td>齐码数量:</td><td><s:hidden name="formBean.numPerHand.id"/>
	          <s:textfield name="formBean.numPerHand.numPerHand" cssClass="easyui-numberspinner" style="width:80px;"  
        required="required" data-options="min:1,max:20,editable:false"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td colspan="2">
	          <a onclick="saveNumPerHand();" href="javascript:void(0);" class="easyui-linkbutton">保存</a>
	          </td>
	       </tr>
	    </table>
	    </s:form>
      
</body>
</html>