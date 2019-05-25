<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="js/jquery-1.11.3.min.js" type="text/javascript" ></script>
<!DOCTYPE html>

<!-- 登录 注册 购物车... -->
<div class="container-fluid">
	<div class="col-md-4">
		<img src="img/logo2.png" />
	</div>
	<div class="col-md-5">
		<img src="img/header.png" />
	</div>
	<div class="col-md-3" style="padding-top:20px">
		<ol class="list-inline">
		
			<!-- 登录成功后的页面显示 -->
			<c:if test="${empty user}">
				<li><a href="login.jsp">登录</a></li>
				<li><a href="register.jsp">注册</a></li>
			</c:if>
			<c:if test="${!empty user }">
				<li>欢迎你,${user.username}</li>
				<li><a href="#">退出</a></li>
			</c:if>
			
			<li><a href="cart.jsp">购物车</a></li>
			<li><a href="order_list.jsp">我的订单</a></li>
		</ol>
	</div>
</div>

<!-- 导航条 -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath}">首页</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="categoryUl">
				
					<%--
						<%
							//在header的里面，准备分类数据，也是可以的
							CategoryService service2 = new CategoryService();
							List<Category> categoryList = service2.findAllCategory();
						%> 
					--%>
					
					
					<!-- 循环显示类别 -->
					<%-- 
						<c:forEach items="${categoryList}" var="category">
							<li><a href="#">${category.cname}</a></li>					
						</c:forEach> 
					--%>
					
				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>
		<script type="text/javascript">
			//header.jsp加载完毕后，去服务器端获得所有的category数据
			$(function(){
				var content = "";
 				$.post(
 					"${pageContext.request.contextPath}/product?method=categoryList",
 					function(data){
 						//数据格式[{"cid":"xx","cname":"yyyy"},{},{}]
 						//动态创建 <li><a href="#">${category.cname}</a></li>	
 						//【拼html命令字符串】
 						for(var i=0; i<data.length;i++){	//js写循环
 							content += "<li><a href='${pageContext.request.contextPath}/product?method=productList&cid="+data[i].cid+"'>" + data[i].cname + "</a></li>";
 						}
 						
 						//将拼接好的<li>放到导航栏中
						$("#categoryUl").html(content);
 					},
 					"json"
 				);
			});
		</script>
		
	</nav>
</div>