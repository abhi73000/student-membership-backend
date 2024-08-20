package com.abhi.studentmembership.dto;

import lombok.Data;

@Data
public class AuthRequest {
	
	private String email;
	
	private String password;

}
