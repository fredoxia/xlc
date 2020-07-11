/*
**************************
author:Keel (keel.sike@gmail.com)
**************************

页码显示jquery插件,只需要存在#pageNav,则会在其中显示页码.
调用时可根据需要先重写go方法.(已去除jquery依赖)

**************************
示例(注意：页面中放置id为pageNav的html对象):

//转到页码时触发的自定义方法,p为当前页码,pn为总页数
pageNav.fn = function(p,pn){
	alert(p+","+pn);
};
//初始跳到第3页,共33页
pageNav.go(3,33);

*/
var pageNav = pageNav || {};
pageNav.fn = null;
//p为当前页码,pn为总页数
pageNav.nav = function (p,pn){
	p = parseInt(p);
	pn = parseInt(pn);
	
	//只有一页,直接显示1
	if(pn <= 1){
		this.p = 1;
		this.pn=1;
		return this.pHtml2(1) + " 总共 1 页";
	}
	if (pn < p) {
		p = pn;
	}
	var re = "";
	//第一页
	if(p <= 1){
		p = 1;
	}else{
		//非第一页
		re+=this.pHtml(p-1,pn,"上一页");
		//总是显示第一页页码
		re+=this.pHtml(1,pn,"1");
	}
	//校正页码
	this.p = p;
	this.pn=pn;

	//开始页码
	var start = 2;
	var end = (pn<9)?pn:9;
	//是否显示前置省略号,即大于10的开始页码
	if(p>=7){
		re+="...";
		start = p-4;
		var e =p+4;
		end = (pn<e)?pn:e;
	}
	for (var i=start; i < p; i++) {
		re+=this.pHtml(i,pn);
	};
	re+=this.pHtml2(p);
	for (var i=p+1; i <= end; i++) {
		re+=this.pHtml(i,pn);
	};
	if (end<pn) {re+="...";
	//显示最后一页页码,如不需要则去掉下面这一句
	re+=this.pHtml(pn,pn);
	};
	if (p<pn) {re+=this.pHtml(p+1,pn,"下一页");};
	return re;
};
//显示非当前页
pageNav.pHtml = function(pageNo,pn,showPageNo){
  	showPageNo = showPageNo || pageNo;
	var H = " <a href='javascript:pageNav.go("+ pageNo+","+ pn+  ");' class='pageNum'>"+ showPageNo+"</a> ";
	return H;
	
};
//显示当前页
pageNav.pHtml2 = function(pageNo){
	var H = " <span class='cPageNum'>"+pageNo+"</span> ";
	return H;
};
//输出页码,可根据需要重写此方法
pageNav.go = function (p,pn){
	//$("#pageNav").html(this.nav(p,pn)); //如果使用jQuery可用此句
	//document.getElementById("pageNav").innerHTML = this.nav(p,pn);
	if (this.fn != null) {
		this.fn(p,pn);
	}
};
//清空分页信息
pageNav.clearPager = function (){
	$("#totalPage").attr("value",0);
	$("#totalResult").attr("value",0);
	$("#currentPage").attr("value",0);
};

pageNav.nextPage= function(){
	if (this.p < this.pn){
		pageNav.go(this.p+1, this.pn);
	}
};

pageNav.prePage= function(){
	if (this.p > 1){
		pageNav.go(this.p-1, this.pn);
	}
};

