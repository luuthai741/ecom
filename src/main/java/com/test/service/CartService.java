package com.test.service;

import java.util.List;

import com.test.dto.CartRequest;
import com.test.dto.CustomerRequest;
import com.test.entities.Cart;
import com.test.entities.CartItems;
import com.test.entities.security.User;

public interface CartService {
	void createOrderForUser(User user, List<CartRequest> list, String link);
	void createOrderForCustomer(CustomerRequest customer, List<CartRequest> list, String link);
	
	boolean enabled(String cofirmToken);
	
	
	List<Cart> findAll();
	List<CartItems> findItemsByCartId(int id);
	
	void deleteByID(int id);
}
