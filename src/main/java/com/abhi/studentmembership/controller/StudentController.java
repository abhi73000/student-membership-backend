package com.abhi.studentmembership.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhi.studentmembership.dto.SStudentDto;
import com.abhi.studentmembership.dto.StudentDto;
import com.abhi.studentmembership.dto.StudentPermitDto;
import com.abhi.studentmembership.service.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController {
	
	private final StudentService studentService;
	
	@GetMapping("/{studentId}")
	public ResponseEntity<SStudentDto> getStudentById(@PathVariable long studentId) throws Exception{
		SStudentDto dto = studentService.getStudentById(studentId);
		
		if(dto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping("/permit")
	public ResponseEntity<?> applyPermit(@RequestBody StudentPermitDto studentPermitDto) {
	    StudentPermitDto submittedStudentPermitDto = studentService.applyPermit(studentPermitDto);

	    if (submittedStudentPermitDto == null) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Failed to submit permit application");
	    } else {
	        return ResponseEntity.status(HttpStatus.CREATED)
	                             .body(submittedStudentPermitDto);
	    }
	}

	
	
	@GetMapping("/permit/{studentId}")
	public ResponseEntity<List<StudentPermitDto>> getAllApliedPermitsByStudentId(@PathVariable long studentId) throws Exception{
		List<StudentPermitDto> studentPermitDtos = studentService.getAllAppliedPermitsById(studentId);
		
		if(studentPermitDtos == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(studentPermitDtos);
	}
	
	
	@PutMapping("/{studentId}")
	public ResponseEntity<?> updateStudent(@PathVariable long studentId, @RequestBody StudentDto studentDto) throws Exception{
		StudentDto createdDto = studentService.updateStudent(studentId, studentDto);
		
		if(createdDto == null) {
			return new ResponseEntity<>("Something went wrong",HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(createdDto);
	}
}
