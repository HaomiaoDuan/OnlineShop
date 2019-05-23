package com.onlineShop.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");	//可以放在filter里，就放在这里了

		try {
			//1.获得的请求的method名称
			String methodName= req.getParameter("method");
			//2.获得当前被访问的对象的字节码对象
			Class clazz = this.getClass();	//这里的this指此时访问的对象，可能是本类对象，也可能是本类子类对象
			//3.获得当前字节码对象中的指定方法
			Method method = clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			//4.执行相应的功能方法
			method.invoke(this,req,resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}