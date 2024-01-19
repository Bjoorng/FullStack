package com.personal.cafe.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.cafe.entities.Order;
import com.personal.cafe.entities.ShoppingCart;
import com.personal.cafe.entities.User;
import com.personal.cafe.repositories.OrdersRepository;
import com.personal.cafe.repositories.ShoppingCartRepository;
import com.personal.cafe.repositories.UserRepository;

@Service
public class OrdersServiceIMPL implements OrdersService{

	@Autowired
	OrdersRepository ordersRepository;
	
	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	ShoppingCartsServiceIMPL shoppingCartsService;
	
	@Autowired
	UserRepository usersRepository;
	
	@Override
	public Order save(ShoppingCart cart) {
		Order o = new Order();
		o.setCart(cart);
		o.setOrderDate(Date.valueOf(LocalDate.now()));
		o.setUser(cart.getUser());
		ordersRepository.save(o);
		shoppingCartsService.deleteById(cart.getId());
		return o;
	}

	@Override
	public List<Order> findAllByUser(String username) {
		Optional<User> customer = usersRepository.findByUsername(username);
        List<Order> orders = customer.get().getOrders();
        return orders;
	}

	@Override
	public List<Order> findAllOrders() {
        return ordersRepository.findAll();
	}

}
