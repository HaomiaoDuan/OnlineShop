package com.onlineShop.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {

	//该购物车中存储的n个购物项
	private Map<String,CartItem> cartItems = new HashMap<>();
	//本来购物车对象设计成购物项的List就可以，但是后期删除购物项的时候，再一个个找再删很麻烦
	//就加了另一个索引（商品的pid）组成Map，且根据pid可以唯一确定购物项，所以易于删除。
	
	//商品的总计
	private double total;

	public Map<String, CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Map<String, CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	
}
