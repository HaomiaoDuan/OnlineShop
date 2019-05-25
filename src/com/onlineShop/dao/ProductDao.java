package com.onlineShop.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.onlineShop.domain.Order;
import com.onlineShop.domain.OrderItem;
import com.onlineShop.domain.Product;
import com.onlineShop.utils.DataSourceUtils;

public class ProductDao {

	//获取热门商品
	public List<Product> findHotProductList() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot = ? limit ?,?";
		List<Product> list = runner.query(sql, new BeanListHandler<Product>(Product.class),1,0,9);
		//网页上面只能放置有限的图片，所以用limit限制
		return list;
	}

	//获得最新商品
	public List<Product> findNewProductList() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product order by pdate desc limit ?,?";
		List<Product> list = runner.query(sql, new BeanListHandler<Product>(Product.class),0,9);
		//sql排序语句Order默认是升序排序，1对应是最小的,降序排列的话，1对应是最大日期的，也就是最新日期的。
		return list;
	}

	//根据cid获取商品信息
	public List<Product> findProductListByCid(int cid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid =?";
		List<Product> productList = runner.query(sql, new BeanListHandler<Product>(Product.class),cid);
		return productList;
	}

	//获取分页信息之总条数
	public int getTotalCount(int cid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from product where cid =?";
		Long query = (Long)runner.query(sql, new ScalarHandler(),cid);	//标量其实是Long型
		return query.intValue();	//Long型强转成int型
	}

	//获取分页信息之商品数据
	public List<Product> findProductListForPageBean(int index, int currentCount, int cid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid =? limit ?,?";
		List<Product> query = runner.query(sql, new BeanListHandler<Product>(Product.class),cid,index,currentCount);
		return query;
	}
	
	//根据商品的pid查询其信息
	public Product findProductByPid(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid = ?";
		Product query = runner.query(sql, new BeanHandler<>(Product.class),pid);
		return query;
	}

	//存储order数据
	public void addOrders(Order order) throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		runner.update(DataSourceUtils.getConnection(),sql,order.getOid(),
				order.getOrdertime(),order.getTotal(),order.getState(),
				order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid());
	}

	//存储orderitem数据
	public void addOrderItems(Order order) throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			runner.update(DataSourceUtils.getConnection(), sql, orderItem.getItemid(), orderItem.getCount(),
					orderItem.getSubtotal(), orderItem.getProduct().getPid(), order.getOid());
		}
	}

	//存储剩余的order信息
	public void updatePrderAddr(Order order) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());	//不用事务控制
		String sql = "update orders set address=? ,name=?,telephone=? where oid=?";
		runner.update(sql , order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
	}

	//更新订单状态
	public void updateOrderState(String oid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());	//不用事务控制
		String sql = "update orders set state=? where oid=?";
		runner.update(sql , 1, oid);
	}	

}
