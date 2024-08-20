package com.abhi.studentmembership.dto;

import java.util.Date;

import lombok.Data;


@Data
public class StudentDto {

	
	private long id;
	
	private String name;
	
	private String email;
	
	private String password;
	
	
	private String studentClass;
	
	private Date birthDate;
	
	private String address;
	
	private String gender;
}
