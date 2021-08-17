package com.test.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Book extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "name")
	private String name;
	@Column(name = "author")
	private String author;
	@Column(name = "image")
	private String image;
	@Column(name = "description")
	private String description;
	@Column(name = "content")
	private String content;
	@Column(name = "price")
	private int price;
	@Column(name = "quantity")
	private int quantity;
	@Column(name = "enabled")
	private boolean enabled;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "genre_id")
	private Genre genre;

	@Transient
	public String getImagePath() {
		if (name == null) {
			return null;
		}
		return "/book-images/" + image;
	}
}
