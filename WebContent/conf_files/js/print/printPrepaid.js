var pageMargin,maxLength;
var dfPrinter;
var space = "   ";
var fontSizeHead = 11;
var fontSize = 10;
function printSalesOrder(prepaid){

	dfPrinter=pazu.TPrinter.getDefaultPrinter();

	if (dfPrinter == null){
        alert("还未设置默认小票打印机，请设置后打印小票");
	} else {
		//alert(dfPrinter.TextWidth(space) + "," + dfPrinter.ScaleWidth);
		pageMargin=dfPrinter.TextWidth("   ");        
		maxLength = dfPrinter.ScaleWidth; 
		var printCopy = $("#printCopy").val();
		for (var i = 0; i < printCopy; i++){
		    printOut(prepaid);
		}
	}
}

function printOut(prepaid){
	//print header
	var chainName = prepaid.chainStore.chain_name;
	printHeader(chainName);

	//print sales
	printContent(prepaid);

	
    //print footer
	printFooter(prepaid);
}
/**
 * 销售
 */
function printContent(prepaid){
	var depositType = "";
	if (prepaid.depositType == "C")
		depositType = "现金";
	else if  (prepaid.depositType == "D")
		depositType = "刷卡";
			
	pazu.TPrinter.printToDefaultPrinter("充值金额 : " + (prepaid.amount).toFixed(0) + " " + depositType); 
	pazu.TPrinter.printToDefaultPrinter("实际到账金额 : " + (prepaid.calculatedAmt).toFixed(0));
	pazu.TPrinter.printToDefaultPrinter("剩余充值金额  : " + (prepaid.accumulateVipPrepaid).toFixed(0));  
	pazu.TPrinter.printToDefaultPrinter("备注  : " + prepaid.comment);  
} 



/**
 * 单据头
 */
function printHeader(chainName){
	 dfPrinter.FontSize=fontSizeHead;
	 dfPrinter.FontBold=true;
     var content = "朴与素童装连锁 - 预存金";
       pazu.TPrinter.printToDefaultPrinter(content);
       pazu.TPrinter.printToDefaultPrinter(chainName);
     content = "--------------------";
       pazu.TPrinter.printToDefaultPrinter(content);       
}



/**
 * 单据尾
 */
function printFooter(prepaid){
    pazu.TPrinter.printToDefaultPrinter("");

	dfPrinter.FontSize=fontSize;
    var content = "此小票是充值凭证，请妥善保管";
      pazu.TPrinter.printToDefaultPrinter(content);
    content = "服务热线:400-606-0849";
      pazu.TPrinter.printToDefaultPrinter(content);
    content = "微信号：QXbaby-HK";
      pazu.TPrinter.printToDefaultPrinter(content);
	var date = prepaid.createDate;
    var footer = "欢迎下次光临 " + date; 
      pazu.TPrinter.printToDefaultPrinter("");
    var salesName = prepaid.operator.name;
      pazu.TPrinter.printToDefaultPrinter("收银员 : " + salesName);
      pazu.TPrinter.printToDefaultPrinter(footer);
    
    dfPrinter.EndDoc();
}
