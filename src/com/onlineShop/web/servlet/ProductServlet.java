package com.onlineShop.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.onlineShop.domain.Cart;
import com.onlineShop.domain.CartItem;
import com.onlineShop.domain.Category;
import com.onlineShop.domain.PageBean;
import com.onlineShop.domain.Product;
import com.onlineShop.service.CategoryService;
import com.onlineShop.service.ProductService;
import com.onlineShop.utils.JedisPoolUtils;

import redis.clients.jedis.Jedis;

public class ProductServlet extends BaseServlet {

	/*public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获得的请求的哪个方法的method
		String methodName = request.getParameter("method");
		System.out.println(methodName);
		switch(methodName){
		case "productList":
			productList(request, response);
			break;
		case "categoryList":
			categoryList(request, response);
			break;
		case "index":
			index(request, response);
			break;
		case "productInfo":
			productInfo(request, response);
			break;
		default:
			break;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	*/
	
	
	
	//【模块中的功能是通过方法区分的】
	
	//显示商品类别的功能
	public void categoryList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	
	//显示热门商品和最新商品的方法
	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProductService service1 = new ProductService();
		
		//准备热门商品-- List<Product>
		List<Product> hotProductList = service1.findHotProductList();
		
		//准备最新商品-- List<Product>
		List<Product> newProductList = service1.findNewProductList();
		
		//以下功能内置到header.jsp里了，用ajax实现
		request.setAttribute("hotProductList",hotProductList);
		request.setAttribute("newProductList",newProductList);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
	
	//显示商品的详细信息
	public void productInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
	
	//根据商品的类别获取商品的列表
	public void productList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	//将商品添加到购物车
	public void addProductToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获得要放到购物车的商品的Pid
		String pid = request.getParameter("pid");
		
		//获得该商品的购买数
		String buyNumStr = request.getParameter("buyNum");
		int buyNum = -1;	//校验的东西不做了
		if(buyNumStr!=null){
			buyNum = Integer.parseInt(buyNumStr);	
		}
		
		//获得Product对象
		ProductService service = new ProductService();
		Product product = service.findProductByPid(pid);
		
		//计算小计
		double subtotal = product.getShop_price() * buyNum * 1.0;
		
		//封装成CartItem
		CartItem item = new CartItem();
		item.setBuyNum(buyNum);
		item.setProduct(product);
		item.setSubtotal(subtotal);
		
		//获得购物车（没有先创建的思想）
		HttpSession session = request.getSession();	//第一次getSession时创建，后面就一直存在了
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart == null){
			cart = new Cart();
		}
		//添加商品
		//需要先判断购物车是否已经包含此购物项了,再分别处理（逻辑判断在外部进行，就不重写put方法用设计模式了）
		if(cart.getCartItems().keySet().contains(pid)){
			 CartItem cartItem = cart.getCartItems().get(pid);
			 cartItem.setBuyNum(cartItem.getBuyNum() + item.getBuyNum());
			 cartItem.setSubtotal(cartItem.getSubtotal() + item.getSubtotal());
			 item = cartItem;
		}
		
		cart.getCartItems().put(pid, item);	
	
		//计算总计
		cart.setTotal(cart.getTotal() + item.getSubtotal());
		
		
		//再次访问session，存入购物车对象
		session.setAttribute("cart", cart);
		
		//request.getRequestDispatcher("/cart.jsp").forward(request, response);//转发
		//这里转发不行，是因为在购物车页面，用户可能多次刷新，导致重复购买，故网址需要更新
		response.sendRedirect("cart.jsp");	//重定向
	}

	//根据pid删除购物车的商品
	public void delProFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		//获取数据
		String pid = request.getParameter("pid");
		
		//获取购物车对象
		Cart cart = (Cart) session.getAttribute("cart");
		//先重新算总计
		cart.setTotal(cart.getTotal() - cart.getCartItems().get(pid).getSubtotal());
		//再删除相应购物项
		cart.getCartItems().remove(pid);
		//更新
		//session.setAttribute("cart", cart);	//基于引用传递的，可以不用set
		
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}
	
	//清空购物车
	public void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		//清空购物车-- 而不是删除session，里面可能其它用户信息
		session.removeAttribute("cart");
		
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}
	
}
