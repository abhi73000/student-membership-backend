package com.abhi.studentmembership.service;

import java.util.List;

import com.abhi.studentmembership.dto.FeeDto;
import com.abhi.studentmembership.dto.SStudentDto;
import com.abhi.studentmembership.dto.STeacherDto;
import com.abhi.studentmembership.dto.StudentDto;
import com.abhi.studentmembership.dto.StudentPermitDto;
import com.abhi.studentmembership.dto.TeacherDto;

public interface AdminService {

	
	public void createAdminAccount();

	public StudentDto postStudent(StudentDto studentDto);

	public List<StudentDto> getAllStudents();
	
	public void deleteStudent(long studentId);
	
	public SStudentDto getStudentById(long id) throws Exception;
	
	public StudentDto updateStudent(Long studentId, StudentDto studentDto) throws Exception;

	public FeeDto payFee(long studentId, FeeDto feeDto);

	public List<StudentPermitDto> getAllAppliedPermits();

	public StudentPermitDto changePermitStatus(Long permitId, String status);

	public TeacherDto postTeacher(TeacherDto teacherDto);

	public List<TeacherDto> getAllTeachers();

	public void deleteTeacher(long teacherId);

	public STeacherDto getTeacherById(long teacherId)throws Exception;

	public TeacherDto updateTeacher(long teacherId, TeacherDto teacherDto) throws Exception;
}
