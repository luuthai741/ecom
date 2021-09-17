package com.test.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String phone;
	private String email;
	private String address;
}
