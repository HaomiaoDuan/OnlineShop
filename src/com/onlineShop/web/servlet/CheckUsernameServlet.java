package com.onlineShop.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onlineShop.service.UserService;

public class CheckUsernameServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取数据
		String username = request.getParameter("username");	//直接从json的键值对里取
		System.out.println("username:"+username);
		//调用业务层完成检查用户名的业务
		UserService service = new UserService();
		Boolean isExist = service.checkUsername(username);
		
		//将数据写回原页面
		response.getWriter().write("{\"isExist\":" + isExist + "}"); //注意细节，不能用单引号
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}