<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户信息</title>
<!-- 引入js -->
<script type="text/javascript" src="/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript">
/*发起ajax请求
$.get 		负责查询
$.post		表单数据提交等
$.getJSON	查询获取json数据
$.getScript	获取js代码
$.ajax({......}) 万能用法
*/
	<%--es6 ${} 获取页面中的变量信息 --%>
	<%--在jQuery中使用${}的形式不能取值,无法直接获取jQuery对象--%>

  /*  发起ajax请求 */
/*  $(function(){
	//alert("页面加载完全,开始调用js")
	//页面取值时,采用对象.属性的方式动态获取.
	$.get("/findAjax",function(result){
			 addTable("tab1",result);
		})
  })	*/ 
  $.ajax({
	  type: "post",			//method
	  url: "/findAjax",		//网址
	  //data: "name=John&location=Boston",  //传递数据
	  dataType:"json",
	  //准备一个函数,实现表格数据的添加.
	  success: function(result){
		  addTable("tab1",result);
		  },
	  error:function(data){
		  alert("提示信息: 当前网络正忙,请稍后再试");
		  },
	  async: true,  //默认: true (默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false)
	  cache: false   //默认: true, dataType为script和jsonp时默认为false) jQuery 1.2 新功能，设置为 false 将不缓存此页面。
	 }); 

	 
	//定义ajax 展现数据函数	 
 function addTable(eleId,data){
	//遍历数据
	   var trs="";
		$(data).each(function(index,user){
		var id = user.id;
		var name = user.name;
		var age = user.age;
		var sex = user.sex;
		trs +="<tr align='center'><td>"+id+"</td><td>"+name+"</td><td>"+age+"</td><td>"+sex+"</td><td>"+"√"+"</td></tr>"
		});
		$("#"+eleId).append(trs);
	 }
	 
</script>
</head>
<body>
	<table id="tab1"  border="1px" width="65%" align="center">
		<tr>
			<td colspan="6" align="center"><h3>用户信息</h3></td>
		</tr>
		<tr>
			<th>编号</th>
			<th>姓名</th>
			<th>年龄</th>
			<th>性别</th>
			<th></th>
		</tr>
	</table>

</body>
</html>