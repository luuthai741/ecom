package com.test.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.test.entities.Genre;
import com.test.repo.GenreRepository;
import com.test.service.GenreService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
	private static final Logger log = LoggerFactory.getLogger(GenreServiceImpl.class);
	private GenreRepository genreRepo;
	
	@Cacheable("genres")
	@Override
	public List<Genre> findAll() {
		log.info("fetch genre data");
		return genreRepo.findAll();
	}
	@Cacheable("gerenByID")
	@Override
	public Genre findById(int id) {
		log.debug("Fetch genre data by id {}", id);
		return genreRepo.findById(id).orElse(null);
	}

	@Override
	public void save(Genre t) {
		log.debug("Save genre {}", t);
		genreRepo.save(t);
	}
	
	@CacheEvict("gerenByID")
	@Override
	public void deleteByID(int id) {
		Genre genre = genreRepo.findById(id).orElse(null);
		if (genre != null) {
			genreRepo.deleteById(id);
		}
	}
	@Cacheable("gerenByName")
	@Override
	public Genre findByName(String name) {
		log.info("Fetch genre data by name {}", name);
		return genreRepo.findByName(name);
	}

}
