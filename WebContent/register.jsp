<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head></head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员注册</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.8.3.js" type="text/javascript"></script>
<!-- 引入表单校验的插件  -->
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/messages_zh.js"></script>

<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}

font {
	color: #3164af;
	font-size: 18px;
	font-weight: normal;
	padding: 0 10px;
}
.error{
	color:red	
}
</style>
<script>
	//自定义校验规则
	$.validator.addMethod(
		//规则的名称
		"isExist",
		//校验的函数
		function(value,element,param){
			//value:输入的内容
			//element:被校验的元素对象，一般不用
			//param:代表规则对应的参数值，用于判断
			/*  if(param == true){}        //自动判断，没必要加      */
			
			//定义 一个全局变量的标志
			var flag = false;	 
			
			//同步执行ajax（get和post都是默认异步）
			//异步会导致flag的值在线程之间传递，难以判断顺序，最终的值也是不确定的
			$.ajax({
				"async":false,
				"url":"${pageContext.request.contextPath}/user?method=checkUsername",
				"data":{"username":value},
				"type":"POST",
				"dataType":"json",
				"success": function(data){
					flag = data.isExist; //json直接取属性
				}
			});
			 
			//返回false代表该校验器不通过
			return !flag;
 
		}
	);
	
	//validate用到的是表单元素的name
	$(function(){
		$("#checkForm").validate({
			rules:{
				"username":{
					"required":true,
					"isExist":true 
				},
				"password":{
					"required":true,
					"rangelength":[6,12]
				},
				"repassword":{
					"required":true,
					"rangelength":[6,12],
					"equalTo":"#password"	//这里用id选择器
				},
				"email":{
					"required":true,
					"email":true
				},
				"sex":{
					"required":true
				}
			},
			"messages":{
				"username":{
					"required":"用户名不能为空",
					"isExist":"用户名已存在"
				},
				"password":{
					"required":"不能为空",
					"rangelength":"密码长度6-12位",
				},
				"repassword":{
					"required":"不能为空",
					"rangelength":"密码长度6-12位",
					"equalTo":"两次密码不一致"
				},
				"email":{
					"required":"邮箱不能为空",
					"email":"邮箱格式不正确"
				},
				//如果在body里写明了错误提示的label，就不需要在message里写了
				"sex":{
					"required":"性别没有第三种选择"
				}
			}
		});
	}); 
</script>

</head>
<body>
	
	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>
		
	<div class="container"
		style="width: 100%; background: url('image/regist_bg.jpg');">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8"
				style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
				<font>会员注册</font>USER REGISTER
				<%-- <form class="form-horizontal" id="checkForm" style="margin-top: 5px;" action="${pageContext.request.contextPath}/user?method=register"  method="post"> --%>
				<form class="form-horizontal" id="checkForm" style="margin-top: 5px;" action="#"  method="post">
					<div class="form-group">
						<label for="username" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="username" name="username"
								placeholder="请输入用户名">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="password" name="password"
								placeholder="请输入密码">
						</div>
					</div>
					<div class="form-group">
						<label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="repassword" name = "repassword"
								placeholder="请输入确认密码">
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
						<div class="col-sm-6">
							<input type="email" class="form-control" id="email" name="email"
								placeholder="Email">
						</div>
					</div>
					<div class="form-group">
						<label for="usercaption" class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="name" name="name"
								placeholder="请输入姓名">
						</div>
					</div>
					<div class="form-group opt">
						<label for="inlineRadio1" class="col-sm-2 control-label">性别</label>
						<div class="col-sm-6">
							<label class="radio-inline">
								<input type="radio" name="sex" id="sex1" value="male"> 男 
							</label> 
							<label class="radio-inline"> 
								<input type="radio"  name="sex" id="sex2" value="female"> 女 
							</label>
							<!-- <label class="error" for="sex" generated="false" style="display:none">
								性别不能为空
							</label> -->
						</div>
					</div>
					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">出生日期</label>
						<div class="col-sm-6">
							<input type="date" class="form-control" name="birthday">
						</div>
					</div>

					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">验证码</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="checkcode">

						</div>
						<div class="col-sm-2">
							<img src="./image/captcha.jhtml" />
						</div>

					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" width="100" value="注册" name="submit"
								style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
						</div>
					</div>
				</form>
			</div>

			<div class="col-md-2"></div>

		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include> 

</body>
</html>




