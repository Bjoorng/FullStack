package com.personal.cafe.service;

import com.personal.cafe.entities.Product;
import com.personal.cafe.entities.ShoppingCart;
import com.personal.cafe.entities.User;

public interface ShoppingCartsService {
	
	ShoppingCart addToCart(Product product, Integer quantity, User user);
	ShoppingCart updateCart(Product product, Integer quantity, User user);
	ShoppingCart deleteProduct(Product product, User user);
	void deleteById(Long id);
	
}
