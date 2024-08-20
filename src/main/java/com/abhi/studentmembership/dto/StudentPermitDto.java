package com.abhi.studentmembership.dto;

import java.util.Date;

import com.abhi.studentmembership.enums.StudentPermitStatus;

import lombok.Data;

@Data
public class StudentPermitDto {
	
    private long id;
	
	private String subject;
	
	private String body;
	
	private Date date;
	
	private StudentPermitStatus studentPermitStatus;
	
	private Long userid;
	

}
