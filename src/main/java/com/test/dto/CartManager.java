package com.test.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import com.test.entities.Book;

import lombok.Getter;

public class CartManager {
	public static final String KEY = "CART_MANAGER";
	@Getter
	private List<CartRequest> listCart;
	private HttpSession session;

	@SuppressWarnings({ "unchecked" })
	public CartManager(HttpSession session) {
		this.session = session;
		List<CartRequest> list = (List<CartRequest>) session.getAttribute("CART_MANAGER");
		if (list != null && !list.isEmpty()) {
			listCart = list;
		} else {
			listCart = new ArrayList<CartRequest>();
		}
	}

	public void clearItems() {
		listCart = new ArrayList<CartRequest>();
		session.setAttribute(CartManager.KEY, listCart);
	}

	public void add(Book book, int quantity) {
		int count = 0;
		for (CartRequest info : listCart) {
			if (info.getBookId() == book.getId()) {
				info.setQuantity(info.getQuantity() + quantity);
				CartRequest updateItem = info;
				listCart.set(count, updateItem);
				session.setAttribute(CartManager.KEY, listCart);
				return;
			}
			count++;
		}
		CartRequest info = CartRequest.builder().bookId(book.getId()).bookName(book.getName()).image(book.getImage())
				.quantity(quantity).price(book.getPrice()).build();
		listCart.add(info);
		session.setAttribute(CartManager.KEY, listCart);
	}

	public void remove(int bookId) {
		List<CartRequest> newList = listCart.stream().filter(x -> x.getBookId() != bookId).collect(Collectors.toList());
		this.listCart = newList;
		session.setAttribute(CartManager.KEY, listCart);
	}
}
