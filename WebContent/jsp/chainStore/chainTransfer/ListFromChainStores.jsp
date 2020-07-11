<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>禧乐仓连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<LINK href="<%=request.getContextPath()%>/conf_files/css/qxbaby_css.css" type=text/css rel=STYLESHEET>
<link href="<%=request.getContextPath()%>/conf_files/css/pagination.css" rel="stylesheet" type="text/css"/>
<SCRIPT src="<%=request.getContextPath()%>/conf_files/js/pagenav1.1.js" type=text/javascript></SCRIPT>
<script>
$(document).ready(function(){
	renderPaginationBar($("#currentPage").val(), $("#totalPage").val());
	});
	
pageNav.fn = function(page,totalPage){                
	$("#currentPage").attr("value",page);
    document.chainListForm.action="chainTransferJSPAction!listFromChainStore";
    document.chainListForm.submit();
};

function selectFromChainStore(chainId, chainName){
	 window.opener.selectFromChainStore(chainId, chainName); 
	 window.close();
}
</script>
</head>
<body>
    <%@ include file="../../common/Style.jsp"%>
   <s:form action="/actionChain/chainTransferJSPAction!listFromChainStore" method="POST" name="chainListForm" id="chainListForm" theme="simple"> 
	<%@ include file="../../common/pageForm.jsp"%>
	<table width="100%" border="0">
		    <tr>
		      <td colspan="3">
		            <!-- table to display the staff information -->
					<table width="100%"  align="left" class="OuterTable" id="org_table">
					  <tr class="PBAInnerTableTitale" align='left'>
					    <th width="20" height ="25">No.</th>
					    <th width="20"></th>
					    <th width="140">连锁店名称</th>
					    <th width="120">连锁店经营者</th>
					    <th></th>
					  </tr>
				      <s:iterator value="uiBean.fromChainStores" status="st" id="chainStore" >
						    <tr class="InnerTableContent" <s:if test="#st.even">style='background-color: rgb(255, 250, 208);'</s:if>>	      
						      <td><s:property value="#st.index +1 + formBean.pager.firstResult"/></td>
						      <td>&nbsp;<s:property value="#chainStore.pinYin.substring(0,1) "/></td>
						      <td><s:property value="#chainStore.chain_name"/></td>
						      <td><s:property value="#chainStore.owner_name"/></td>
						      <td><a href='#' onclick="selectFromChainStore(<s:property value="#chainStore.chain_id"/>,'<s:property value="#chainStore.chain_name"/>');"><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a></td>
						    </tr>
				       </s:iterator>	
				       <s:if test="uiBean.fromChainStores.size == 0">
						    <tr class="InnerTableContent">	      
						      <td colspan="5">没有记录</td>
						     </tr>				       
				       </s:if><s:else>
						    <tr class="InnerTableContent">	      
						      <td colspan="5"><div id="pageNav"></div></td>
						    </tr>					       
				       </s:else>  
					</table></td>

		    </tr>
		  </table>
	 </s:form>
</body>
</html>