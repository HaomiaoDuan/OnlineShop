package com.onlineShop.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.onlineShop.domain.Category;
import com.onlineShop.service.CategoryService;
import com.onlineShop.utils.JedisPoolUtils;

import redis.clients.jedis.Jedis;

public class CategoryListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		//缓存版读取类别信息（多了1和2的判断）
		//先从缓存中查询categoryList 如果有直接使用 没有再从数据库中查询，存到缓存当中
		//保证redis.server是一直运行的
		
		
		//1.获取jedis对象
		Jedis jedis = JedisPoolUtils.getJedis();
		String categoryListJson = jedis.get("categoryListJson");
		
		//2.判断里面有没有，没有就查询数据库，再存入缓存
		if(categoryListJson == null) {
			
			//3.准备分类数据
			CategoryService service = new CategoryService();
			List<Category> categoryList = service.findAllCategory();
			
			//4.封装数据成json
			Gson gson = new Gson();
			categoryListJson = gson.toJson(categoryList);
			
			jedis.set("categoryListJson", categoryListJson);
			
		}else{	//有就直接读取缓存
			categoryListJson = jedis.get("categoryListJson");
		}
		
		response.getWriter().write(categoryListJson);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}