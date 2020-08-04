<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
var layout_west_tree;
var layout_west_tree2;
$(function() {
		layout_west_tree = $('#treeMenu').tree({
			onClick : function(node) {
				if (node.attributes && node.attributes.url) {
					var url;
					url = node.attributes.url;
					parent.$.messager.progress({
								title : '提示',
								text : '数据处理中，请稍后....'
							});

     				addTab({
						url : url,
						title : node.text,
						iconCls : node.iconCls
					});

				}
			},
			onLoadSuccess : function(node, data) {
				parent.$.messager.progress('close');
			}
		});
		layout_west_tree2 = $('#treeMenu2').tree({
			onClick : function(node) {
				if (node.attributes && node.attributes.url) {
					var url;
					url = node.attributes.url;
					parent.$.messager.progress({
								title : '提示',
								text : '数据处理中，请稍后....'
							});

     				addTab({
						url : url,
						title : node.text,
						iconCls : node.iconCls
					});

				}
			},
			onLoadSuccess : function(node, data) {
				parent.$.messager.progress('close');
			}
		});
	}); 
</script>
<div id="menuAccordian" class="easyui-accordion" style="width:auto;height:auto;">  
    <div title="系统菜单" data-options="selected:true" style="padding:5px;">
        <ul id="treeMenu" class="easyui-tree"  data-options="lines:true,animate:true">  
		      <s:if test="#session.LOGIN_USER.containFunction('basicData!preMaintainBasic') || #session.LOGIN_USER.containFunction('productJSPAction!preCreateProduct') || #session.LOGIN_USER.containFunction('productJSPAction!preSearch')||#session.LOGIN_USER.containFunction('productJSPAction!preCreateProduct') || #session.LOGIN_USER.containFunction('productJSPAction!preBrandPriceIncrease')">
			  	<li data-options="iconCls:'icon-text_1',state:'open',border:false">
			  	    <span>条型码管理</span>
			  		<ul>
			  			<!--  <s:if test="#session.LOGIN_USER.containFunction('basicData!preMaintainBasic')"><li data-options="iconCls:'icon-text_1',attributes:{url:'basicData!preMaintainBasic'}">条型码基础资料管理</li></s:if>-->
			    		<s:if test="#session.LOGIN_USER.containFunction('productJSPAction!preCreateProduct')"><li data-options="iconCls:'icon-text_1',attributes:{url:'productJSPAction!preCreateProduct'}">新建条型码</li></s:if>
						<s:if test="#session.LOGIN_USER.containFunction('productJSPAction!preSearch')"><li data-options="iconCls:'icon-text_1',attributes:{url:'productJSPAction!preSearch'}">查询修改条型码</li></s:if>
						<s:if test="#session.LOGIN_USER.containFunction('productJSPAction!preBatchDeleteBarcode')"><li data-options="iconCls:'icon-text_1',attributes:{url:'productJSPAction!preBatchDeleteBarcode'}">批量修改删除条型码</li></s:if>
						<s:if test="#session.LOGIN_USER.containFunction('productJSPAction!preBatchInsertBarcode')"><li data-options="iconCls:'icon-text_1',attributes:{url:'productJSPAction!preBatchInsertBarcode'}">批量导入条型码</li></s:if>
						<s:if test="#session.LOGIN_USER.containFunction('productJSPAction!preBrandPriceIncrease')"><li data-options="iconCls:'icon-text_1',attributes:{url:'productJSPAction!preBrandPriceIncrease'}">品牌调价</li></s:if>
			    	</ul>  
			    </li>
			    </s:if>
			 
			 	
			 	<li  data-options="iconCls:'icon-status_online',state:'open',border:false">
			  	    <span>供应商&客户管理</span>
			  		<ul style="width: 150%">
			  		    <s:if test="#session.LOGIN_USER.containFunction('headQSupplierMgmtJSPAction!preSupplier')"><li data-options="iconCls:'icon-status_online',attributes:{url:'headQSupplierMgmtJSPAction!preSupplier'}">供应商信息管理 </li></s:if>
			    		<li data-options="iconCls:'icon-status_online',attributes:{url:'headQCustMgmtJSPAction!preCust'}">客户信息管理 </li>
			    	</ul>
			    </li>
			    <s:if test="#session.LOGIN_USER.containFunction('supplierPurchaseJSP!preEditPurchase') || #session.LOGIN_USER.containFunction('supplierPurchaseJSP!preEditPurchaseReturn') || #session.LOGIN_USER.containFunction('supplierPurchaseJSP!preSearchPurchase')">
			  	<li  data-options="iconCls:'icon-images',state:'open',border:false">
			  	    <span>供应商单据管理</span>
			  		<ul style="width: 150%">
			    		<s:if test="#session.LOGIN_USER.containFunction('supplierPurchaseJSP!preEditPurchase')"><li data-options="iconCls:'icon-images',attributes:{url:'supplierPurchaseJSP!preEditPurchase'}">采购单据录入 </li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('supplierPurchaseJSP!preEditPurchaseReturn')"><li data-options="iconCls:'icon-images',attributes:{url:'supplierPurchaseJSP!preEditPurchaseReturn'}">采购退货单据录入 </li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('supplierPurchaseJSP!preEditPurchaseFree')"><li data-options="iconCls:'icon-images',attributes:{url:'supplierPurchaseJSP!preEditPurchaseFree'}">采购赠送单据录入 </li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('supplierPurchaseJSP!preSearchPurchase')"><li data-options="iconCls:'icon-images',attributes:{url:'supplierPurchaseJSP!preSearchPurchase'}">搜索采购单据 </li></s:if>
			    	</ul>
			    </li>
			    </s:if>
			    <s:if test="#session.LOGIN_USER.containFunction('financeSupplierJSP!preCreate') || #session.LOGIN_USER.containFunction('financeSupplierJSP!preSearchFiance')">
			  	<li  data-options="iconCls:'icon-money_yen',state:'open',border:false">
			  	    <span>供应商财务管理</span>
			  		<ul style="width: 150%">
			    		<s:if test="#session.LOGIN_USER.containFunction('financeSupplierJSP!preCreate')"><li data-options="iconCls:'icon-money_yen',attributes:{url:'financeSupplierJSP!preCreate'}">创建供应商财务单据</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('financeSupplierJSP!preSearchFH')"><li data-options="iconCls:'icon-money_yen',attributes:{url:'financeSupplierJSP!preSearchFH'}">搜索供应商财务单据 </li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('financeSupplierJSP!preSearchAcctFlow')"><li data-options="iconCls:'icon-money_yen',attributes:{url:'financeSupplierJSP!preSearchAcctFlow'}">查询供应商往来账户</li></s:if>
			    	    
			    	</ul>
			    </li>
			    </s:if>	
	 	
			 	<s:if test="#session.LOGIN_USER.containFunction('inventoryOrder!create') || #session.LOGIN_USER.containFunction('inventoryOrder!createReturnOrder') || #session.LOGIN_USER.containFunction('inventoryOrder!search') || #session.LOGIN_USER.containFunction('inventoryOrder!preSearch')">
			  	<li  data-options="iconCls:'icon-images',state:'open',border:false">
			  	    <span>批发销售管理</span>
			  		<ul style="width: 150%">
			    		<s:if test="#session.LOGIN_USER.containFunction('inventoryOrder!create')"><li data-options="iconCls:'icon-images',attributes:{url:'inventoryOrder!create'}">批发销售单据录入 </li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('inventoryOrder!createReturnOrder')"><li data-options="iconCls:'icon-images',attributes:{url:'inventoryOrder!createReturnOrder'}">批发退货单据录入 </li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('inventoryOrder!createFreeOrder')"><li data-options="iconCls:'icon-images',attributes:{url:'inventoryOrder!createFreeOrder'}">批发赠送单据录入 </li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('inventoryOrder!preSearch')"><li data-options="iconCls:'icon-images',attributes:{url:'inventoryOrder!preSearch?formBean.order.order_Status=<%=InventoryOrder.STATUS_PDA_COMPLETE%>'}">PDA完成单据</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('inventoryOrder!preSearch')"><li data-options="iconCls:'icon-images',attributes:{url:'inventoryOrder!preSearch?formBean.order.order_Status=<%=InventoryOrder.STATUS_DRAFT%>'}">仓库草稿单据</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('inventoryOrder!preSearch')"><li data-options="iconCls:'icon-images',attributes:{url:'inventoryOrder!preSearch?formBean.order.order_Status=<%=InventoryOrder.STATUS_ACCOUNT_PROCESS%>'}">等待审核单据</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('inventoryOrder!preSearch')"><li data-options="iconCls:'icon-images',attributes:{url:'inventoryOrder!preSearch'}">搜索批发单据 </li></s:if>
			    	</ul>
			    </li>
			    </s:if>
			   
			    <s:if test="#session.LOGIN_USER.containFunction('financeHQJSP!preCreateFHQ') || #session.LOGIN_USER.containFunction('financeHQJSP!preSearchFHQ')">
			  	<li  data-options="iconCls:'icon-money_yen',state:'open',border:false">
			  	    <span>批发财务管理</span>
			  		<ul style="width: 150%">
			    		<s:if test="#session.LOGIN_USER.containFunction('financeHQJSP!preCreateFHQ')"><li data-options="iconCls:'icon-money_yen',attributes:{url:'financeHQJSP!preCreateFHQ'}">创建批发财务单据</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('financeHQJSP!preSearchFHQ')"><li data-options="iconCls:'icon-money_yen',attributes:{url:'financeHQJSP!preSearchFHQ'}">搜索批发财务单据 </li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('financeHQJSP!preSearchAcctFlow')"><li data-options="iconCls:'icon-money_yen',attributes:{url:'financeHQJSP!preSearchAcctFlow'}">查询客户往来账户</li></s:if>
			    	</ul>
			    </li>
			    </s:if>

		</ul>

	</div>  
    <div title="报表与管理功能" style="padding:5px;">  
    	<ul id="treeMenu2" class="easyui-tree"  data-options="lines:true,animate:true" >  
        		 <li  data-options="iconCls:'icon-database',state:'open',border:false">
			  	    <span>库存管理</span>
			  		<ul style="width: 150%">
			    		<s:if test="#session.LOGIN_USER.containFunction('headqInventoryFlowJSPAction!preInventoryRpt')"><li data-options="iconCls:'icon-database',attributes:{url:'headqInventoryFlowJSPAction!preInventoryRpt'}">当前库存统计</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('headqInventoryFlowJSPAction!preCreateInventoryFlowOrder')"><li data-options="iconCls:'icon-database',attributes:{url:'headqInventoryFlowJSPAction!preCreateInventoryFlowOrder?formBean.order.type=2'}">报损单</li></s:if>
			    	</ul>
			    </li>
			    <s:if test="#session.LOGIN_USER.containFunction('expenseHeadqJSP!preCreateExpenseHeadq') || #session.LOGIN_USER.containFunction('expenseHeadqJSP!preSearchExpenseHeadq')">
				<li data-options="iconCls:'icon-money_yen',state:'open'">
					<span>总部费用管理</span>
					<ul>
						<s:if test="#session.LOGIN_USER.containFunction('expenseHeadqJSP!preCreateExpenseHeadq')">
							<li data-options="iconCls:'icon-money_yen',attributes:{url:'expenseHeadqJSP!preCreateExpenseHeadq'}">创建总部费用</li>
						</s:if>
						<s:if test="#session.LOGIN_USER.containFunction('expenseHeadqJSP!preSearchExpenseHeadq')">
							<li data-options="iconCls:'icon-money_yen',attributes:{url:'expenseHeadqJSP!preSearchExpenseHeadq'}">查找总部费用</li>
						</s:if>		
														
					</ul>
				</li>
				 </s:if>	
			    <li  data-options="iconCls:'icon-chart_bar',state:'open',border:false">
			    	<span>总部报表功能</span>
				  	<ul style="width: 150%">
				  	    <s:if test="#session.LOGIN_USER.containFunction('financeSupplierJSP!preFinanceRpt')"><li data-options="iconCls:'icon-chart_bar',attributes:{url:'financeSupplierJSP!preFinanceRpt'}">供应商财务报表</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('financeHQJSP!preFinanceRpt')"><li data-options="iconCls:'icon-chart_bar',attributes:{url:'financeHQJSP!preFinanceRpt'}">批发财务报表</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('headqReportJSP!preGenerateHQExpenseReport')"><li data-options="iconCls:'icon-chart_bar',attributes:{url:'headqReportJSP!preGenerateHQExpenseReport'}">总部费用报表</li></s:if>
					    <s:if test="#session.LOGIN_USER.containFunction('headqReportJSP!preGeneratePurchaseReport')"><li data-options="iconCls:'icon-chart_bar',attributes:{url:'headqReportJSP!preGeneratePurchaseReport'}">总部采购统计报表</li></s:if>
					    <s:if test="#session.LOGIN_USER.containFunction('headqReportJSP!preGenerateSalesReport')"><li data-options="iconCls:'icon-chart_bar',attributes:{url:'headqReportJSP!preGenerateSalesReport'}">总部销售统计报表</li></s:if>
					    <s:if test="#session.LOGIN_USER.containFunction('headqReportJSP!preGenerateCustAcctFlowReport')"><li data-options="iconCls:'icon-chart_bar',attributes:{url:'headqReportJSP!preGenerateCustAcctFlowReport'}">连锁客户往来帐报表</li></s:if>
					    <s:if test="#session.LOGIN_USER.containFunction('headqReportJSP!preGenerateSupplierAcctFlowReport')"><li data-options="iconCls:'icon-chart_bar',attributes:{url:'headqReportJSP!preGenerateSupplierAcctFlowReport'}">供应商往来帐报表</li></s:if>
					</ul>
			 	</li>
			 	<s:if test="#session.LOGIN_USER.containFunction('userJSP!preEdit')">
			    <li data-options="iconCls:'icon-group',state:'closed',border:false">
			        <span>人力资源管理</span>
				  	<ul>
			    		<s:if test="#session.LOGIN_USER.containFunction('userJSP!preEdit')"><li data-options="iconCls:'icon-group',attributes:{url:'userJSP!preEdit'}">员工信息管理</li></s:if>
			    		<!-- <s:if test="#session.LOGIN_USER.containFunction('hrEvalJSP!preEvaluationConf')"><li data-options="iconCls:'icon-group',attributes:{url:'hrEvalJSP!preEvaluationConf'}">绩效考核管理配置</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('hrEvalJSP!preEvaluationMgmtNew')"><li data-options="iconCls:'icon-group',attributes:{url:'hrEvalJSP!preEvaluationMgmtNew'}">新建员工绩效考核</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('hrEvalJSP!preEvaluationMgmtSearch') || #session.LOGIN_USER.containFunction(14)"><li data-options="iconCls:'icon-group',attributes:{url:'hrEvalJSP!preEvaluationMgmtSearch'}">查询员工绩效考核</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('hrEvalJSP!preEvaluationSummaryMgmtSearch')"><li data-options="iconCls:'icon-group',attributes:{url:'hrEvalJSP!preEvaluationSummaryMgmtSearch'}">查询员工考核月报表</li></s:if>-->
					</ul>
			 	</li>
			 	</s:if>				
			    <li  data-options="iconCls:'icon-status_online',state:'open',border:false">
			    	<span>我的系统</span>
				  	<ul style="width: 150%">
			    		<!--  <li data-options="iconCls:'icon-status_online',attributes:{url:'hrEvalJSP!preEvaluationSummaryPersonalSearch'}">我的考核月报表</A></li>-->
			    		<li data-options="iconCls:'icon-status_online',attributes:{url:'userJSP!preUpdateMyAcct'}">我的账户</A></li>
					</ul>
			 	</li>
				
				<s:if test="#session.LOGIN_USER.containFunction('userJSP!preEditFunctionality') || #session.LOGIN_USER.containFunction('userJSP!retrieveLog') || #session.LOGIN_USER.containFunction('userJSP!swithToChain') || #session.LOGIN_USER.roleType == 99">
				<li data-options="iconCls:'icon-cog',state:'open',border:false">
				<span>管理员</span>
				  		<ul style="width: 150%">
				    		<s:if test="#session.LOGIN_USER.containFunction('userJSP!preEditFunctionality') || #session.LOGIN_USER.roleType == 99"><li data-options="iconCls:'icon-cog',attributes:{url:'userJSP!preEditFunctionality'}">总部权限管理</li></s:if>
				    		<s:if test="#session.LOGIN_USER.containFunction('chainSMgmtJSP!preEditChainPriceIncre')"><li data-options="iconCls:'icon-cog',attributes:{url:'chainSMgmtJSP!preEditChainPriceIncre'}">连锁店价格涨幅管理</li></s:if>
				    		<s:if test="#session.LOGIN_USER.containFunction('chainSMgmtJSP!preEditChainInfor')"><li data-options="iconCls:'icon-cog',attributes:{url:'chainSMgmtJSP!preEditChainInfor'}">连锁店管理</li></s:if>
				    		<s:if test="#session.LOGIN_USER.containFunction('chainSMgmtJSP!preCreateInitialStock')"><li data-options="iconCls:'icon-cog',attributes:{url:'chainSMgmtJSP!preCreateInitialStock'}">连锁店期初库存</li></s:if>
				    		<s:if test="#session.LOGIN_USER.containFunction('chainSMgmtJSP!preEditChainGroup')"><li data-options="iconCls:'icon-cog',attributes:{url:'chainSMgmtJSP!preEditChainGroup'}">连锁店关联管理</li></s:if>
				    		<s:if test="#session.LOGIN_USER.containFunction('userJSP!retrieveLog') || #session.LOGIN_USER.roleType == 99"><li data-options="iconCls:'icon-cog',attributes:{url:'userJSP!retrieveLog'}">系统运行日志</li></s:if>
				    	</ul>
				 </li>
		        </s:if>
		</ul>
    </div>  
</div>
