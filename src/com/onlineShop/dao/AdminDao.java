package com.onlineShop.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.onlineShop.domain.Category;
import com.onlineShop.domain.Order;
import com.onlineShop.domain.Product;
import com.onlineShop.utils.DataSourceUtils;

public class AdminDao {

	//获得所有类别数据
	public List<Category> findAllCategory() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		List<Category> query = runner.query(sql, new BeanListHandler<>(Category.class));
		return query;
	}

	//根据cid获得cname
	public Category findCategoryByCid(String cid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category where cid =?";
		Category query = runner.query(sql, new BeanHandler<>(Category.class),cid);
		return query;
	}

	//把商品添加到数据库
	public boolean addProduct(Product product) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		int update = runner.update(sql,product.getPid(),product.getPname(),product.getMarket_price(),product.getShop_price(), product.getPimage(),
				product.getPdate(),product.getIs_hot(),	product.getPdesc(),	product.getPflag(),product.getCategory().getCid());
		return update==1?true:false;
	}

	//获得所有的商品
	public List<Product> findProductList() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product";
		List<Product> query = runner.query(sql, new BeanListHandler<>(Product.class));
		return query;
	}

	public Product findProductByPid(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid =?";
		Product  product = runner.query(sql, new BeanHandler<>(Product.class),pid);
		return product;
	}

	public String findProductCidByPid(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select cid from product where pid =?";
		String cid = (String) runner.query(sql, new ScalarHandler(),pid);
		return cid;
	}

	//更新product的数据
	public boolean updateProduct(Product product) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update product set pname=?,market_price=?,shop_price=?,pimage=?,pdate=?,is_hot=?,pdesc=?,cid=? where pid=?";
		int update = runner.update(sql,product.getPname(), product.getMarket_price(), product.getShop_price(), product.getPimage(),
				product.getPdate(), product.getIs_hot(), product.getPdesc(),product.getCategory().getCid(),product.getPid());
		return update!=0?true:false;
	}

	//删除商品
	public boolean delProduct(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from product where pid =?";
		int update = runner.update(sql,pid);
		return update!=0?true:false;
	}

	//获取所有订单
	public List<Order> findAllOrders() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders";
		List<Order> orderList = runner.query(sql, new BeanListHandler<>(Order.class));
		return orderList;
	}

	//多表查询
	public List<Map<String, Object>> findOrderInfoByOid(String oid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select i.count ,i.subtotal, p.pname,p.market_price,p.pimage "
				+ "from orderitem i,product p "
				+ "where p.pid=i.pid and i.oid=?";
		List<Map<String, Object>> mapList = runner.query(sql, new MapListHandler(),oid);
		return mapList;
	}

}
