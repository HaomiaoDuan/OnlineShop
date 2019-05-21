package com.onlineShop.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onlineShop.domain.Category;
import com.onlineShop.domain.Product;
import com.onlineShop.service.CategoryService;
import com.onlineShop.service.ProductService;

public class IndexServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProductService service1 = new ProductService();
		
		//准备热门商品-- List<Product>
		List<Product> hotProductList = service1.findHotProductList();
		
		//准备最新商品-- List<Product>
		List<Product> newProductList = service1.findNewProductList();
		
		//以下功能内置到header.jsp里了，用ajax实现
//		//准备分类数据
//		CategoryService service2 = new CategoryService();
//		List<Category> categoryList = service2.findAllCategory();
//		
		request.setAttribute("hotProductList",hotProductList);
		request.setAttribute("newProductList",newProductList);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}