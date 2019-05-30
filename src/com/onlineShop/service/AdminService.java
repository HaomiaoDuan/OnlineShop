package com.onlineShop.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.onlineShop.dao.AdminDao;
import com.onlineShop.domain.Category;
import com.onlineShop.domain.Order;
import com.onlineShop.domain.Product;

public interface AdminService {

	//获得所有类别数据
	public List<Category> findAllCategory() ;
	
	//根据cid获得Category对象
	public Category findCategoryByCid(String cid);

	//把商品添加到数据库
	public boolean addProduct(Product product);

	//获得所有的商品
	public List<Product> findProductList() ;

	//根据pid寻找商品
	public Product findProductByPid(String pid);

	//更新product
	public boolean updateProduct(Product product);

	//根据pid删除商品
	public boolean delProduct(String pid) ;

	//查全部订单--暂时不补全Order对象
	public List<Order> findAllOrders();

	//查找订单详情所需要的数据
	public List<Map<String, Object>> findOrderInfoByOid(String oid);

}
