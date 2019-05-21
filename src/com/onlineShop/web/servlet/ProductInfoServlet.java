package com.onlineShop.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.soap.AddressingFeature.Responses;

import com.onlineShop.domain.Product;
import com.onlineShop.service.ProductService;


public class ProductInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获得传递过来的数据
		String pid = request.getParameter("pid");	//pid是字符串
		//System.out.println("pid="+pid);
		String currentPage = request.getParameter("currentPage");
		String cid = request.getParameter("cid");

		
		//把pid传到service层，调用业务完成
		ProductService service = new ProductService();
		Product product = null;
		product = service.findProductByPid(pid);

		//System.out.println(product);
		
		//创建用户端携带的cookie-----把浏览过的商品信息存到cookie(session也行)里
		String pids = pid;
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for (Cookie cookie : cookies) {
				if("pids".equals(cookie.getName())){
					pids = cookie.getValue();
					//【生成pids业务】:
					//原先的pids是：1-3-2,先访问的2，再访问3，最新访问的是1
					// 若本次访问是3，则pids = 3-1-2
					//(1)将pids拆成一个数组
					String[] split = pids.split("-");	//{1,3,2}
					//(2)转成集合
					List<String> asList = Arrays.asList(split);	//先转成List [1,3,2]
					LinkedList<String> list = new LinkedList<>(asList);	//再转成LinkedList [1,3,2]
					//(3)判断集合中是否存在当前pid
					if(list.contains(pid)){
						//包含当前pid
						list.remove(pid);
					}
					list.addFirst(pid);
					//(4)将[3,1,2]转成"3-1-2" 【难点】
					StringBuffer sb = new StringBuffer();
					for(int i = 0; i<list.size() && i<7; i++ ){		//限制浏览记录pids的长度
						sb.append(list.get(i));
						sb.append("-");
					}
					//去掉3-1-2-的最后的-
					pids = sb.substring(0, sb.length()-1);
				}
			}
		}
		//System.out.println(pids);
		
		
		//发送cookie
		Cookie pidsCookie = new Cookie("pids", pids);//第一次赋值pids就是pid
		//cookie之间会覆盖
		response.addCookie(pidsCookie);
		
		
		//传输数据到jsp显示
		request.setAttribute("product", product);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("cid", cid);
		request.getRequestDispatcher("product_info.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}