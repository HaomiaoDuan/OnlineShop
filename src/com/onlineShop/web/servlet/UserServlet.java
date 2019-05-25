package com.onlineShop.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.onlineShop.domain.User;
import com.onlineShop.service.UserService;
import com.onlineShop.utils.CommonUtils;
import com.onlineShop.utils.MailUtils;

public class UserServlet extends BaseServlet {
/*
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("method");
		System.out.println(methodName);
		switch(methodName){
		case "active":
			active(request,response);
			break;
		case "checkUsername":
			checkUsername(request,response);
			break;
		case "register":
			register(request,response);
			break;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	*/
	
	
	
	//模块中的功能是通过方法区分的
	
	//获取激活码并激活账户
	public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获得激活码
		String activeCode = request.getParameter("activeCode");
		
		//传输激活码到service层，实现激活业务
		UserService service = new UserService();
		service.active(activeCode);		//按理说要有返回值，但是没有这么多jsp页面，默认激活都成功了
		
		//跳到首页
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}

	//检查用户名是否存在（被jsp的ajax调用）
	public void checkUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取数据
		String username = request.getParameter("username");	//直接从json的键值对里取
		//System.out.println("username:"+username);
		//调用业务层完成检查用户名的业务
		UserService service = new UserService();
		Boolean isExist = service.checkUsername(username);
		
		//将数据写回原页面
		response.getWriter().write("{\"isExist\":" + isExist + "}"); //注意细节，不能用单引号
	}
	
	//根据表单信息来注册用户（包含发邮件功能）
	public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//0. 解决中文乱码
		request.setCharacterEncoding("UTF-8");
		
		//1. 获得并封装表单数据--BeanUtils
		User user = new User();
		Map<String, String[]> parameterMap = request.getParameterMap();
		try {
			//解决问题： User里面有一个属性是Date型的，表单的数据没法直接放进入。
			//【类型转换器】解释：
			//populate()方法将properties中的属性转换到User的属性中，发现需要转换后的类型是Date.class类型
			//就执行convert()方法 （重写后功能为：将String型变量value转换为Date类型）
			
			//自己指定一个类型转换器（将String转成Date）
			ConvertUtils.register(new Converter() {
				
				@Override
				public Object convert(Class clazz, Object value) {
					//将String转成Date
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date parse = null;
					try {
						parse = format.parse(value.toString());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return parse;
				}
			}, Date.class);
			
			BeanUtils.populate(user, parameterMap);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//1.5 补齐表单没有的数据:uid,telephone,state,code
		user.setUid(CommonUtils.getUUID());
		user.setTelephone(null);
		user.setState(0);
		String activeCode = CommonUtils.getUUID();	//保存激活码，后面要发给邮箱
		user.setCode(activeCode);
		
		//2. 将user传递给service层,调用相关方法，完成注册业务
		UserService service = new UserService();
		Boolean isRegister = service.register(user);
		System.out.println("isRegister = " + isRegister);
		
		//3. 处理返回值--判断是否注册成功
		if(isRegister){
			//3.5 发送激活邮件
			String email = user.getEmail();
			String emailMsg = "恭喜你注册成功，请点击以下链接进行激活 <a href = 'http://localhost:8080/OnlineShop/user?method=active&activeCode='"+activeCode+"'>"+
					" http://localhost:8080/OnlineShop/user?method=active&activeCode="+activeCode+"</a>";
			try {
				MailUtils.sendMail(email, emailMsg);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//4.跳转到注册成功的页面
			response.sendRedirect(request.getContextPath() + "/registerSuccess.jsp");
		}else{
			//4.跳转到注册失败的页面
			response.sendRedirect(request.getContextPath() + "/registerFail.jsp");
		}
	}
	
	//登录
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取登录数据
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//传数据到service层，用户确认
		UserService service = new UserService();
		User user = service.login(username,password);
		if(user != null){
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			response.sendRedirect("default.jsp");
		}else{
			String loginInfo = "用户名或密码错误！";
			request.setAttribute("loginInfo", loginInfo);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}