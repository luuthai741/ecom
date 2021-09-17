package com.test.service;

import org.springframework.data.domain.Page;

public interface PageService<T> {
	Page<T> paging(int pageNumber);

}
