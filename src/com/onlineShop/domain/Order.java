package com.onlineShop.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
	
	/*`oid` varchar(32) NOT NULL,
	  `ordertime` datetime DEFAULT NULL,
	  `total` double DEFAULT NULL,
	  `state` int(11) DEFAULT NULL,
	  `address` varchar(30) DEFAULT NULL,
	  `name` varchar(20) DEFAULT NULL,
	  `telephone` varchar(20) DEFAULT NULL,
	  `uid` varchar(32) DEFAULT NULL,
	 备注：表orders的名称的由来：因为order是关键字，所以加s
	 */
	
	private String oid;		//订单号
	private Date ordertime;	//下单时间
	private double total;	//总计
	private int state;		//订单状态，1代表已付款，0代表未付款
	private String address;	//收获地址
	private String name;	//收货人
	private String telephone;//收货人电话
	private User user;		//用户
	
	private List<OrderItem> orderItems = new ArrayList<>();//该订单有多少订单项
	//为什么表中没有订单项的数据，这里却要封装数据： 为了以后传递数据方便----设计环节：面向对象的思想
	//为什么不用Map，订单项一旦提交到确认环节，除了取消，就不能部分修改了，所以不需要Map
	
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
