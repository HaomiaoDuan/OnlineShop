package com.onlineShop.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.onlineShop.domain.User;

public class UserLoginPrivilegeFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		//1.request的强转
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		//2.校验用户是否登录-----校验session中是否存在User对象
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user");
		
		//3.判断用户是否登录
		if(user==null){
			//没有登录
			resp.sendRedirect(req.getContextPath()+"/login.jsp");
			return ; //后面代码不执行
		}
		
		//4.放行
		chain.doFilter(req, resp);
	}
	
	
	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
