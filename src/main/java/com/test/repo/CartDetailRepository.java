package com.test.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.test.entities.CartItems;

@Repository
public interface CartDetailRepository extends JpaRepository<CartItems, Integer> {
	@Query("SELECT i FROM CartItems i where i.cartId=?1")
	List<CartItems> findByCartId(int cartId);
}
