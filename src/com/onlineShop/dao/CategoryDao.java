package com.onlineShop.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.onlineShop.domain.Category;
import com.onlineShop.utils.DataSourceUtils;

public class CategoryDao {

	//获取所有的类别
	public List<Category> findAllCategory() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		List<Category> list = runner.query(sql, new BeanListHandler<Category>(Category.class));
		return list;
	}

}
