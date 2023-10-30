package com.javafx.workshopjavafxjdbc.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seller implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String email;
	private Date birthDate;
	private Double baseSalary;
	
	private Department department;

}
