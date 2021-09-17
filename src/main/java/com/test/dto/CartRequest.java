package com.test.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int bookId;
	private String bookName;
	private int quantity;
	private int price;
	private String image;
}
