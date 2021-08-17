package com.test.service;

import java.util.List;

public interface GenericService<T> {
	public List<T> findAll();
	public T findById(int id);
	public void save(T t);
	public void deleteByID(int id);
}
