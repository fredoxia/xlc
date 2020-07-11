<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>访问失败</title>
<%@ include file="Style.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function viewLog(){
    var exceptionDiv = document.getElementById("exceptionDiv");

    if (exceptionDiv.style.display =="none")
       exceptionDiv.style.display ="block";
    else
       exceptionDiv.style.display ="none";
}
</script>

</head>
<body>
   <br/>
   	     系统在运行时发生错误，无法响应你的请求。请联系管理员查看。<p/>
   	  <s:actionmessage/><s:fielderror/> 
   	     <a href="#" onclick="viewLog();">点击</a> 可以打开错误信息<p/>
   	     
   	     <div id="exceptionDiv" style="display:none;">
             <s:property value="exceptionStack"/>
         </div>
   <a href="javascript:history.go(-1);">返回</a>
</body>
</html>