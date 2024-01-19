package com.personal.cafe.service;

import java.util.List;

import com.personal.cafe.entities.Order;
import com.personal.cafe.entities.ShoppingCart;

public interface OrdersService {
	
	Order save(ShoppingCart cart);
	List<Order> findAllByUser(String username);
	List<Order> findAllOrders();
}
