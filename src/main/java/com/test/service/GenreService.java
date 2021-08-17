package com.test.service;

import java.util.List;

import com.test.entities.Genre;

public interface GenreService extends GenericService<Genre> {
	Genre findByName(String name);
}
