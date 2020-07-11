<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" /> 
<title>禧乐仓连锁店</title>
<%@ include file="../common/Style.jsp"%>
</head>
<body class="easyui-layout">
    <div data-options="region:'north',title:'North Title',split:false,border:false,noheader:true" style="height:49px;overflow:hidden;">
           <%@ include file="include/Header.jsp"%>
    </div>  
    <div data-options="region:'west',title:'功能导航',split:false,border:false" style="width:180px;overflow:hidden;">
           <%@ include file="layout/West.jsp"%>
    </div> 
 
    <div data-options="region:'center'">
		   <%@ include file="layout/Center.jsp"%>
    </div> 
</body>
</html>