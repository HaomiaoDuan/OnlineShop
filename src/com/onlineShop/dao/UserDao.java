package com.onlineShop.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.tomcat.jdbc.pool.DataSource;

import com.onlineShop.domain.User;
import com.onlineShop.utils.DataSourceUtils;

public class UserDao {

	//新建一条用户数据
	public int register(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user values (?,?,?,?,?,?,?,?,?,?)";
		int update = runner.update(sql,user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),
				user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode());
		
		return update;
	}
	
	//修改用户的激活码
	public void active(String activeCode) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set state = 1 where code =?";
		int update = runner.update(sql,activeCode);
//		System.out.println("update = " + update);
	}

	//查询用户名的行数
	public Long checkUsername(String username) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from user where username=?";
		Long query = (Long) runner.query(sql,new ScalarHandler() ,username);	//返回的值是Long型
		//System.out.println("query"+query);
		return query;
	}

}
