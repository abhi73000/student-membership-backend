package com.abhi.studentmembership.service;

import java.util.List;

import com.abhi.studentmembership.dto.SStudentDto;
import com.abhi.studentmembership.dto.StudentDto;
import com.abhi.studentmembership.dto.StudentPermitDto;

public interface StudentService {

    SStudentDto getStudentById(long id) throws Exception;

    StudentPermitDto applyPermit(StudentPermitDto studentPermitDto);

    List<StudentPermitDto> getAllAppliedPermitsById(long studentId);

    StudentDto updateStudent(long studentId, StudentDto studentDto) throws Exception;

}
