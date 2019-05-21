package com.onlineShop.service;

import java.sql.SQLException;
import java.util.List;

import com.onlineShop.dao.ProductDao;
import com.onlineShop.domain.PageBean;
import com.onlineShop.domain.Product;

public class ProductService {

	//获取热门商品
	public List<Product> findHotProductList() {
		ProductDao dao = new ProductDao();
		List<Product> hotProductList = null;
		try {
			hotProductList = dao.findHotProductList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hotProductList;
	}

	//获得最新商品
	public List<Product> findNewProductList() {
		ProductDao dao = new ProductDao();
		List<Product> NewProductList = null;
		try {
			NewProductList = dao.findNewProductList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NewProductList;
	}

	//根据cid获取商品信息
	public List<Product> findProductListByCid(int cid) {
		ProductDao dao = new ProductDao();
		List<Product> productList = null;
		try {
			productList = dao.findProductListByCid(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productList;
	}

	//获取分页信息的数据并封装成PageBean
	public PageBean<Product> findPageBean(int currentPage, int currentCount, int cid)  {
		
		//(一)创建实体，封装前台数据
		PageBean<Product> pageBean = new PageBean<>();
		
		//1.封装当前页 currentPage
		pageBean.setCurrentPage(currentPage);
		
		//2.封装当前页显示的条数 currentCount
		pageBean.setCurrentCount(currentCount);
		
		//3.封装类别cid
		pageBean.setCid(cid);
		
		//（二）获取后台数据
		ProductDao dao = new ProductDao();
		
		//1.获取总条数 totalCount 并封装
		int totalCount = 0;
		try {
			totalCount = dao.getTotalCount(cid);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		
		//2.计算总页数 totalPage 并封装
		// 公式：总页数 = Math.ceil(总条数 /当前页显示的条数) 
		int totalPage = (int) Math.ceil(1.0 * totalCount/currentCount);	//1.0乘的作用是让int先变成double型，再相除
		pageBean.setTotalPage(totalPage);
		
		//3.获取每页需要需要显示的商品数据
		//借助中间变量索引，来界定取数据的区间
		//索引 index = (页数 - 1) *每页显示条数
		int index = (currentPage - 1) * currentCount;
		List<Product> productList = null;
		try {
			productList = dao.findProductListForPageBean(index,currentCount,cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageBean.setProductList(productList);
		
		return pageBean;
	}

	//根据商品的pid查询其信息
	public Product findProductByPid(String pid){
		ProductDao dao = new ProductDao();
		Product product = null;
		try {
			product = dao.findProductByPid(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}


}
