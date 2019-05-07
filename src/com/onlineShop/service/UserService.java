package com.onlineShop.service;

import java.sql.SQLException;

import com.onlineShop.dao.UserDao;
import com.onlineShop.domain.User;

public class UserService {

	//注册业务
	public Boolean register(User user) {
		UserDao dao = new UserDao();
		int row = 0;
		try {
			row = dao.register(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//dao层不涉及过多的逻辑操作，直接将sql操作的值返回就行，这里是insert的返回值
		return row>0?true:false;
	}

	//激活业务
	public void active(String activeCode) {
		UserDao dao = new UserDao();
		try {
			dao.active(activeCode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//查询用户名是否存在
	public Boolean checkUsername(String username) {
		UserDao dao = new UserDao();
		Long row = 0L;
		try {
			row = dao.checkUsername(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return row==1?true:false;
	}

}
