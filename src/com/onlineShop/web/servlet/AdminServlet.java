 package com.onlineShop.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.onlineShop.domain.Category;
import com.onlineShop.domain.Order;
import com.onlineShop.domain.Product;
import com.onlineShop.service.AdminService;
import com.onlineShop.service.ProductService;
import com.onlineShop.service.impl.AdminServiceImpl;
import com.onlineShop.utils.BeanFactory;

public class AdminServlet extends BaseServlet {

	public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.getRequestDispatcher("product?method=categoryList").forward(request, response);
		
		//提供一个List<Category>转成json字符串
		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		List<Category> categoryList = service.findAllCategory();
		
		//封装成json数据
		Gson gson = new Gson();
		String json = gson.toJson(categoryList);
		
		//发送数据
		response.setContentType("text/json;charset=UTF-8");		//处理乱码，否则无法显示
		response.getWriter().write(json);
		
	}
	
	public void findProductList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//准备数据productList,并存入域中和跳转页面
		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		List<Product> productList = service.findProductList();
		request.setAttribute("productList",productList);
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
	}
	
	
	public void adminUpdateProductUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//接收pid，根据pid获取product数据，传给edit.jsp
		String pid = request.getParameter("pid");
		
		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		Product product = service.findProductByPid(pid);
		List<Category> categoryList =  service.findAllCategory();
		
		request.setAttribute("product", product);
		request.setAttribute("categoryList", categoryList);
		request.getRequestDispatcher("/admin/product/edit.jsp").forward(request, response);
	}
	
	public void adminDelProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//接收数据pid，删除数据库中的参数
		String pid = request.getParameter("pid");
		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		boolean isSuccess = service.delProduct(pid);
		response.sendRedirect(request.getContextPath() + "/admin?method=findProductList");
	}
	
	
	public void findAllOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获得所有的订单信息List<Order>----根据页面需要的信息，确定是单表查询还是多表查询
		
		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		List<Order>orderList = service.findAllOrders();
		
		request.setAttribute("orderList", orderList);
		request.getRequestDispatcher("/admin/order/list.jsp").forward(request, response);
	}
	
	public void findOrderInfoByOid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//模拟高并发的延时状况
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//获得oid
		String oid = request.getParameter("oid");
		
		//AdminService service = new AdminService();
		//用解耦合的方式进行编码-----解web层与service层的耦合
		//使用 工厂 + 反射 + 配置文件（含解析技术）
		AdminService service = (AdminService) BeanFactory.getBean("adminService");

		List<Map<String, Object>> mapList = service.findOrderInfoByOid(oid);
		
		Gson gson = new Gson();
		String json = gson.toJson(mapList);
		
		response.setContentType("text/html;charset=UTF-8");		//没有这句话，就是乱码！！！
		
		response.getWriter().write(json);
	}

}
