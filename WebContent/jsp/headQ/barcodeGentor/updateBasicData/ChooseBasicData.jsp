<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
function changeBasicData(value){
    if (value != ""){
    	document.basicDataForm.action="basicData!changeBasicData";
    	document.basicDataForm.submit(); 
    }
}
</script>
<s:form id="basicDataForm" name="basicDataForm" action="" theme="simple">
	 <table width="100%" border="0">
	    <tr class="InnerTableContent">
	      <td width="101" height="19">
	         <strong>基础资料类别：</strong>
	      </td>
		  <td>
		      <s:select name="formBean.basicData" cssClass="easyui-combobox"  data-options="width:100,editable:false,onChange:function(param1, param2){changeBasicData(param2);}" onchange="changeBasicData(this.value);"  list="#{'brand':'品牌'}" listKey="key" listValue="value"  headerKey="-"  headerValue="请选择"/>
		  </td>
	    </tr>
	 </table>
</s:form>