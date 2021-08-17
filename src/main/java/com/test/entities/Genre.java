package com.test.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "genre")
@Builder
public class Genre extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@Column(name = "name")
	private String name;
	@OneToMany(mappedBy = "genre", fetch = FetchType.EAGER)
	private List<Book> books = new ArrayList<Book>();
	
}
