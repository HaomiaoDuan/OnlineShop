package com.onlineShop.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.onlineShop.dao.AdminDao;
import com.onlineShop.domain.Category;
import com.onlineShop.domain.Order;
import com.onlineShop.domain.Product;

public class AdminService {

	//获得所有类别数据
	public List<Category> findAllCategory() {
		AdminDao dao = new AdminDao();
		List<Category> categoryList = null;
		try {
			categoryList = dao.findAllCategory();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categoryList;
	}

	//根据cid获得Category对象
	public Category findCategoryByCid(String cid) {
		AdminDao dao = new AdminDao();
		Category category = null;
		try {
			category = dao.findCategoryByCid(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return category;
	}

	//把商品添加到数据库
	public boolean addProduct(Product product) {
		AdminDao dao = new AdminDao();
		boolean isSuccess = false;
		try {
			isSuccess = dao.addProduct(product);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuccess;
	}

	//获得所有的商品
	public List<Product> findProductList() {
		AdminDao dao = new AdminDao();
		List<Product> productList = null;
		try {
			productList = dao.findProductList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productList;
	}

	//根据pid寻找商品
	public Product findProductByPid(String pid) {
		AdminDao dao = new AdminDao();
		Product product = null;
		String cid = "";  
		try {
			product = dao.findProductByPid(pid);
			cid = dao.findProductCidByPid(pid);
			Category category = new Category();
			category.setCid(cid);
			product.setCategory(category);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return product;
	}

	//更新product
	public boolean updateProduct(Product product) {
		AdminDao dao = new AdminDao();
		boolean isSuccess = false;
		try {
			isSuccess = dao.updateProduct(product);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuccess;
	}

	//根据pid删除商品
	public boolean delProduct(String pid) {
		AdminDao dao = new AdminDao();
		boolean isSuccess = false;
		try {
			isSuccess = dao.delProduct(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuccess;
	}

	//查全部订单--暂时不补全Order对象
	public List<Order> findAllOrders() {
		AdminDao dao = new AdminDao();
		List<Order> orderList = null;
		try {
			orderList = dao.findAllOrders();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderList;
		
	}

	//查找订单详情所需要的数据
	public List<Map<String, Object>> findOrderInfoByOid(String oid) {
		AdminDao dao = new AdminDao();
		List<Map<String, Object>> mapList = null;
		try {
			mapList = dao.findOrderInfoByOid(oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapList;
	}

}
