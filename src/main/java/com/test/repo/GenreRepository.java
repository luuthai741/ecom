package com.test.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.test.entities.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
	Genre findByName(String name);
}
