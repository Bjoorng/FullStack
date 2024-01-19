package com.personal.cafe.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.cafe.entities.CartItem;
import com.personal.cafe.entities.Product;
import com.personal.cafe.entities.ShoppingCart;
import com.personal.cafe.entities.User;
import com.personal.cafe.repositories.CartItemRepository;
import com.personal.cafe.repositories.ShoppingCartRepository;

@Service
public class ShoppingCartsServiceIMPL implements ShoppingCartsService{

	@Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
	
	@Override
	public ShoppingCart addToCart(Product product, Integer quantity, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShoppingCart updateCart(Product product, Integer quantity, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShoppingCart deleteProduct(Product product, User user) {
		ShoppingCart cart = user.getCart();
        Set<CartItem> cartItems = cart.getCartItem();
        CartItem item = findItemInCart(cartItems, product.getId());
        cartItems.remove(item);
        cartItemRepository.delete(item);
        double totalPrice = totalPrice(cartItems);
        int totalItems = totalItems(cartItems);

        cart.setCartItem(cartItems);
        cart.setItems(totalItems);
        cart.setTotalPrice(totalPrice);

        return shoppingCartRepository.save(cart);
	}

	private int totalItems(Set<CartItem> cartItems) {
		int totalItems = 0;
        for(CartItem item : cartItems){
            totalItems += item.getQuantity();
        }
        return totalItems;
	}

	private double totalPrice(Set<CartItem> cartItems) {
		double totalPrice = 0.0;
        for(CartItem item : cartItems){
            totalPrice += item.getPrice();
        }
        return totalPrice;
	}

	private CartItem findItemInCart(Set<CartItem> cartItems, Long id) {
		CartItem cartItem = null;
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == id) {
                cartItem = item;
            }
        }
        return cartItem;
	}

	@Override
	public void deleteById(Long id) {
		ShoppingCart cart = shoppingCartRepository.getById(id);
		shoppingCartRepository.delete(cart);		
	}

}
