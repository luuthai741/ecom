package com.test.service.impl;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.test.dto.CartRequest;
import com.test.dto.CustomerRequest;
import com.test.entities.Book;
import com.test.entities.Cart;
import com.test.entities.CartItems;
import com.test.entities.UserInfo;
import com.test.entities.security.User;
import com.test.mail.SendMail;
import com.test.repo.CartDetailRepository;
import com.test.repo.CartRepository;
import com.test.service.BookService;
import com.test.service.CartService;

import lombok.AllArgsConstructor;

@Service
public class CartServiceImpl implements CartService {
	private static final Logger LOG = LoggerFactory.getLogger(CartServiceImpl.class);
	
	private CartRepository cartRepo;
	private CartDetailRepository detailRepo;
	private BookService bookService;
	private SendMail sendMail;

	@Autowired
	public CartServiceImpl(CartRepository cartRepo, CartDetailRepository detailRepo, BookService bookService,
			SendMail sendMail) {
		super();
		this.cartRepo = cartRepo;
		this.detailRepo = detailRepo;
		this.bookService = bookService;
		this.sendMail = sendMail;
	}

	@Override
	public boolean enabled(String cofirmToken) {
		Cart cart = cartRepo.findByCofirmToken(cofirmToken).orElse(null);
		if (cart == null || cart.isEnabled()) {
			return false;
		}
		cart.setEnabled(true);
		cart.setCofirmToken(null);
		cartRepo.save(cart);
		LOG.info("{cart in db{}}", cartRepo.findById(cart.getId()));
		List<CartItems> items = detailRepo.findByCartId(cart.getId());
		for (CartItems item : items) {
			Book book = bookService.findById(item.getBooktId());
			if (book != null) {
				book.setQuantity(book.getQuantity() - item.getQuantity());
				if (book.getQuantity() <= 0) {
					book.setEnabled(false);
				}
				bookService.save(book);
				LOG.info("{book in db{}}", bookService.findById(book.getId()));
			}
		}
		return true;
	}

	
	@Cacheable("orders")
	@Override
	public List<Cart> findAll() {
		return cartRepo.findAll();
	}

	@Cacheable("itemsByOrderId")
	@Override
	public List<CartItems> findItemsByCartId(int id) {
		return detailRepo.findByCartId(id);
	}

	@Override
	public void deleteByID(int id) {
		List<CartItems> items = detailRepo.findByCartId(id);
		cartRepo.deleteById(id);
		for (CartItems item : items) {
			detailRepo.deleteById(item.getId());
		}
	}

	@Override
	public void createOrderForUser(User user, List<CartRequest> list, String link) {
		UserInfo info = user.getInfo();
		Cart order = Cart.builder().userId(user.getId()).userName(info.getFirstName() + " " + info.getLastName())
				.email(user.getEmail()).phone(info.getPhoneNumber()).address(info.getAddress()).build();
		cartRepo.save(order);

		int total = 0;
		for (CartRequest item : list) {
			Book book = bookService.findById(item.getBookId());
			if (book.getQuantity() < item.getQuantity()) {
				throw new RuntimeException("Số lượng sách đặt lớn hơn số lượng sách trong kho");
			}
			CartItems detail = CartItems.builder().cartId(order.getId()).booktId(item.getBookId())
					.bookName(item.getBookName()).price(item.getPrice()).quantity(item.getQuantity())
					.total(item.getQuantity() * item.getPrice()).build();
			total += detail.getTotal();
			detailRepo.save(detail);
		}
		order.setTotal(total);
		order.setCofirmToken(UUID.randomUUID().toString());
		cartRepo.save(order);
		try {
			sendMail.enabledOrder(user.getEmail(), link, order.getCofirmToken());
		} catch (Exception e) {
			e.getMessage();
		}

	}

	@Override
	public void createOrderForCustomer(CustomerRequest customer, List<CartRequest> list, String link) {
		Cart cart = Cart.builder().userId(0).userName(customer.getName()).email(customer.getEmail())
				.phone(customer.getPhone()).address(customer.getAddress()).build();
		cartRepo.save(cart);

		int total = 0;
		for (CartRequest item : list) {
			Book book = bookService.findById(item.getBookId());
			if (book.getQuantity() < item.getQuantity()) {
				throw new RuntimeException("Số lượng sách đặt lớn hơn số lượng sách trong kho");
			}
			CartItems detail = CartItems.builder().cartId(cart.getId()).booktId(item.getBookId())
					.bookName(item.getBookName()).price(item.getPrice()).quantity(item.getQuantity())
					.total(item.getQuantity() * item.getPrice()).build();
			total += detail.getTotal();
			detailRepo.save(detail);

		}
		cart.setTotal(total);
		cart.setCofirmToken(UUID.randomUUID().toString());
		cartRepo.save(cart);
		try {
			sendMail.enabledOrder(customer.getEmail(), link, cart.getCofirmToken());
		} catch (Exception e) {
			e.getMessage();
		}
	}

	
}
