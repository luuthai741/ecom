package com.test.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.entities.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
	Genre findByName(String name);
}
