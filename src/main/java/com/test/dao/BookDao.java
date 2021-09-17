package com.test.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.test.entities.Book;

@Repository
public class BookDao {
	@Autowired
	@PersistenceContext
	private EntityManager em;

	public List<Book> newBooks() {
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//		CriteriaQuery<Book> cq = cb.createQuery(Book.class);
//		Root<Book> book = cq.from(Book.class);
//		cq.select(book);
//		cq.orderBy(cb.desc(book.get("createdAt")));
//		TypedQuery<Book> tq = em.createQuery(cq).setFirstResult(0).setMaxResults(6);
//		return tq.getResultList();
		return null;
	}
}
