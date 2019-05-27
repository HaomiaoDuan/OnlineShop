package com.onlineShop.domain;

import java.util.ArrayList;
import java.util.List;

public class PageBean<T> {
	
	//1.当前页数
	private int currentPage;
	
	//2.当前页面显示条数
	private int currentCount;
	
	//3.总条数
	private int totalCount;
		
	//4.总页数
	private int totalPage;
		
	//5.每页显示的数据
	private List<T> dataList = new ArrayList<T>();	//泛型，增加通用性
	
// 	//6.类别
// 	private int cid;
		
// 	public int getCid() {
// 		return cid;
// 	}
// 	public void setCid(int cid) {
// 		this.cid = cid;
// 	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getDataList() {
		return dataList;
	}
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}
	
	
}
