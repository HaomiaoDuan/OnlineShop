package com.onlineShop.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onlineShop.domain.PageBean;
import com.onlineShop.domain.Product;
import com.onlineShop.service.CategoryService;
import com.onlineShop.service.ProductService;

public class ProductListByCidServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取数据
		String cidStr = request.getParameter("cid");
		int cid = Integer.parseInt(cidStr);
				
		
		//分页操作(服务)，一开始模拟前台数据，不需要处理请求
				
	    //前台能提供的信息(一)
		//1. 从前台获取当前页页数 currentPage
		String currentPageStr = (String) request.getParameter("currentPage");	//和后面的product_list.jsp的跳页功能结合起来
		//System.out.println(currentPageStr);
		if(currentPageStr==null){
			currentPageStr = "1";
		}
		int currentPage = Integer.parseInt(currentPageStr);
		
	    //2. 设定每页显示条数 currentCount 为12条 
		int currentCount = 12;
		
		//封装分页信息实体
		//PageBean<Product> pageBean = new PageBean(); 	//创建pageBean对象和封装数据不在servlet完成
		PageBean<Product> pageBean = null;
		ProductService service = new ProductService();
		pageBean = service.findPageBean(currentPage,currentCount,cid);
		//System.out.println(pageBean.getProductList().size());	//测试
		
		
		//选择显示的jsp，传递数据
		request.setAttribute("pageBean", pageBean);
		
		//定义一个记录历史商品信息的集合
		List<Product> historyProductList = new ArrayList<Product>();
		//获得客户端携带的pids的cookie----浏览器历史
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				if("pids".equals(cookie.getName())){
					String pids = cookie.getValue();
					String[] split = pids.split("-");
					for(String pid:split){
						Product product = service.findProductByPid(pid);
						historyProductList.add(product);
					}
				}
			}
		}
		
		//将历史记录的集合放到域中historyProductList
		request.setAttribute("historyProductList", historyProductList);
			
		request.getRequestDispatcher("product_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}