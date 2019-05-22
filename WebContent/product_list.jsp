<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员登录</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
	width: 100%;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
</style>

<!-- 
	测试
	<script >
		$(function(){
			alert("${productList}");
		});
	
	</script>
-->
</head>

<body>


	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>


	<div class="row" style="width: 1270px; margin: 0 auto;">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<li><a href="#">首页</a></li>
			</ol>
		</div>
		
		<!-- 循环显示分类的商品图片 -->
		<c:forEach items="${pageBean.productList}" var="product">
			<div class="col-md-2" style="height:250px">		 <!-- 因为商品名称不同，导致高度变化，影响排版。这时候最好不用auto的高度 -->
				<a href="${pageContext.request.contextPath}/product?method=productInfo&pid=${product.pid}&cid=${pageBean.cid}&currentPage=${pageBean.currentPage}"> <img src="${product.pimage}"
					width="170" height="170" style="display: inline-block;">
				</a>
				<p>
					<a href="${pageContext.request.contextPath}/product?method=productInfo&pid=${product.pid}&cid=${pageBean.cid}&currentPage=${pageBean.currentPage}" style='color: green'>${product.pname}</a>
				</p>
				<p>
					<font color="#FF0000">商城价：&yen;${product.shop_price}</font>
				</p>
			</div>
		</c:forEach>


	</div>

	<!--分页 -->
	<div style="width: 380px; margin: 0 auto; margin-top: 50px;">
		<ul class="pagination" style="text-align: center; margin-top: 10px;">
			
			<!-- 按照按钮的排列顺序编写代码  -->
			
			<!-- 前一页 -->
			<!-- 如果是第一页，就不变 -->
			<c:if test="${pageBean.currentPage == 1}">
				<li class="disabled"><a href="javascript:void(0)" aria-label="Previous"> <span aria-hidden="true">&raquo;</span> </a></li>
			</c:if>
			<!-- 如果不是第一页就像后跳一页 -->
			<c:if test="${pageBean.currentPage != 1}">
				<li><a href="${pageContext.request.contextPath}/product?method=productList&currentPage=${pageBean.currentPage - 1}&cid=${pageBean.cid}"
				 aria-label="Previous"> <span aria-hidden="true">&raquo;</span> </a></li>
			</c:if>
			
			
			<!-- 循环写出页码的按钮-->
			<c:forEach begin="1" end="${pageBean.totalPage}"  var="page">
				<!-- 判断如果page是当前页，就不跳 -->
				<c:if test="${pageBean.currentPage == page}">
					<li class="active"><a href="javascript:void(0);">${page}</a></li>	<!-- 固定写法 -->
				</c:if>
				<!-- 判断如果page不是当前页，则【跳页】 -->
				<c:if test="${pageBean.currentPage != page }">
					<li><a href="${pageContext.request.contextPath}product?method=productList&currentPage=${page}&cid=${pageBean.cid}">${page}</a></li>
				</c:if>
			</c:forEach>
			
			
			<!-- 后一页 -->
			<!-- 如果是最后一页，点击不跳页（或者不让点） -->
			<c:if test="${pageBean.currentPage == pageBean.totalPage}">
				<li class="disabled">
					<a href="javascript:void(0);" aria-label="Next"> 
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
			</c:if>
			<!-- 如果不是，可以点击跳页 -->
			<c:if test="${pageBean.currentPage != pageBean.totalPage}">
				<li>
					<a href="${pageContext.request.contextPath}/product?method=productList&currentPage=${pageBean.currentPage + 1}&cid=${pageBean.cid}" aria-label="Next"> 
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
			</c:if>
			
			

		</ul>
	</div>
	<!-- 分页结束 -->

	<!--商品浏览记录-->
	<div
		style="width: 1210px; margin: 0 auto; padding: 0 9px; border: 1px solid #ddd; border-top: 2px solid #999; height: 246px;">

		<h4 style="width: 50%; float: left; font: 14px/30px 微软雅黑">浏览记录</h4>
		<div style="width: 50%; float: right; text-align: right;">
			<a href="">more</a>
		</div>
		<div style="clear: both;"></div>

		<div style="overflow: hidden;">

			<ul style="list-style: none;">
			
				<!-- 循环显示浏览记录图片 -->
				<c:forEach items="${historyProductList}" var="product">
					<li style="width: 150px; height: 216; float: left; margin: 0 8px 0 0; padding: 0 18px 15px; text-align: center;">
						<img src="${product.pimage}" width="130px" height="130px" />
					</li>
				</c:forEach>
				
			</ul>

		</div>
	</div>


	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>

</html>