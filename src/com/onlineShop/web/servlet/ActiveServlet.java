package com.onlineShop.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onlineShop.service.UserService;

public class ActiveServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获得激活码
		String activeCode = request.getParameter("activeCode");
		
		//传输激活码到service层，实现激活业务
		UserService service = new UserService();
		service.active(activeCode);		//按理说要有返回值，但是没有这么多jsp页面，默认激活都成功了
		
		//跳到首页
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}