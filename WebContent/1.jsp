<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="jsp/common/Style.jsp"%>
<title>禧乐仓管理信息系统 </title>
<!-- 总部登录页面 -->
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function login(){
    var params=$("#loginForm").serialize(); 
    if (validateLoginForm())
        $.post("<%=request.getContextPath()%>/action/loginJSON!login",params, loginBackProcess,"json");
}
function loginBackProcess(data){
	var response = data.response;
	var returnCode = response.returnCode;
	if (returnCode != SUCCESS){
		$.messager.alert('错误提示',response.message,'warning');
	} else {
		var returnValue = response.returnValue;
		if (returnValue == 2){
		    window.location.href = "<%=request.getContextPath()%>/action/login!myTask";
		} else if (returnValue == 1)
			window.location.href = "<%=request.getContextPath()%>/action/userJSP!swithToChain";	
		else 
			$.messager.alert('错误提示',"无法找到对应链接",'warning');			    
	}
}
function pdaLogin(){
	window.location.href = "<%=request.getContextPath()%>/indexHeadQPDA.jsp";
}
</script>
</head>
<body>
<div id="loginDialog" class="easyui-dialog" title="禧乐仓 管理系统" data-options="iconCls:'icon-status_online',resizable:false,modal:true,draggable:false,closable:false,buttons:[{text:'登陆',handler:function(){ login(); }},{text:'PDA登录',handler:function(){ pdaLogin(); }}]" style="width:330px;height:230px;padding:5px">
	  <s:form id="loginForm" name="loginForm" method="post" action="" theme="simple">
		  <table width="100%">
		    <tr>
		      <td colspan="2" align="center"><strong>总部用户登录 </strong></td>
		    </tr>
		    <tr>
		      <td width="85" height="30">用户名：</td>
		      <td width="180">
		      <s:textfield name="user.user_name" id="userName" cssClass="easyui-validatebox" data-options="required:true"/></td>
		    </tr>
		    <tr>
		      <td height="30">密码：</td>
		      <td>
		      <s:password name="user.password" id="password" cssClass="easyui-validatebox" data-options="required:true"/>

		      </td>
		    </tr>
		    <tr>
		      <td height="30"></td>
		      <td align="left"><input type="checkbox" name="isAdmin" id="isAdmin" value="true"/>登陆到连锁店</td>
		    </tr>
		  </table>
		</s:form>
</div>


</body>
</html>