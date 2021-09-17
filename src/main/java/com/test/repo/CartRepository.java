package com.test.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.entities.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
	Optional<Cart> findByCofirmToken(String cofirmToken);
}
