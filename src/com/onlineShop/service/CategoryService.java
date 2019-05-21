package com.onlineShop.service;

import java.sql.SQLException;
import java.util.List;

import com.onlineShop.dao.CategoryDao;
import com.onlineShop.domain.Category;

public class CategoryService {

	//获取所有的类别
	public List<Category> findAllCategory() {
		CategoryDao dao = new CategoryDao();
		List<Category> list = null;
		try {
			list = dao.findAllCategory();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return list;
	}

}
