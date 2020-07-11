<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script>
/**
 * once click the button, it will help to search brand
 */
function searchBrand(){

	var brandName = $.trim($("#brandName").val());
	if (brandName != "") {
	    var params= "formBean.productBarcode.product.brand.brand_Name=" + brandName ; 
    
        var url = encodeURI(encodeURI("productJSPAction!scanBrand?" +params));
	
        window.open(url,'_blank','height=400, width=450, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');  
	} else {
        $.messager.alert('失败信息', "请输入品牌名称",'error');
    } 
}

/**
 * user click the brand
 */
function selectBrand(brandName, brandId){
	if (brandName != "" && brandId != "" && brandId != 0){
        $("#brandName").val(brandName);
        $("#brand_ID").val(brandId);

        $("#brandName").attr("disabled", "disabled");
        $("#searchBt").attr("disabled", "disabled");
        $("#clearBt").removeAttr("disabled");
    }
}

function clearBrand(){
    $("#brandName").val("");
    $("#brand_ID").val("");

    $("#brandName").removeAttr("disabled");
    $("#searchBt").removeAttr("disabled");
    $("#clearBt").attr("disabled", "disabled");

    clearAllData();
}
</script>

         <s:textfield name="formBean.productBarcode.product.brand.brand_Name" id="brandName" size="10"/>
         <s:hidden name="formBean.productBarcode.product.brand.brand_ID" id="brand_ID"/> 
         <input id="searchBt" type="button" onclick="searchBrand();" value="查找"/>
         <input id="clearBt" type="button" onclick="clearBrand();" value="清除" disabled/>