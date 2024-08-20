package com.abhi.studentmembership.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.abhi.studentmembership.entities.StudentPermit;
import com.abhi.studentmembership.entities.User;
import org.springframework.stereotype.Service;

import com.abhi.studentmembership.dto.SStudentDto;
import com.abhi.studentmembership.dto.StudentDto;
import com.abhi.studentmembership.dto.StudentPermitDto;
import com.abhi.studentmembership.enums.StudentPermitStatus;
import com.abhi.studentmembership.repository.StudentPermitRepository;
import com.abhi.studentmembership.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImp implements StudentService {

    private final UserRepository userRepository;

    private final StudentPermitRepository studentPermitRepository;


    @Override
    public SStudentDto getStudentById(long id) throws Exception {
        Optional<User> opt = userRepository.findById(id);

        if (opt.isPresent()) {
            SStudentDto sDto = new SStudentDto();
            sDto.setStudentDto(opt.get().getStudentDto());
            return sDto;
        }
        throw new Exception("User not found with this " + id + " id");
    }


    @Override
    public StudentPermitDto applyPermit(StudentPermitDto studentPermitDto) {
        Optional<User> opt = userRepository.findById(studentPermitDto.getUserid());

        if (opt.isPresent()) {
            StudentPermit studenPermit = new StudentPermit();
            studenPermit.setSubject(studentPermitDto.getSubject());
            studenPermit.setDate(new Date());
            studenPermit.setBody(studentPermitDto.getBody());
            studenPermit.setStudentPermitStatus(StudentPermitStatus.Pending);
            studenPermit.setUser(opt.get());
            StudentPermit submittedStudentPermit = studentPermitRepository.save(studenPermit);
            StudentPermitDto studentPermitDto1 = new StudentPermitDto();
            studentPermitDto1.setId(submittedStudentPermit.getId());
            return studentPermitDto1;
        }
        return null;
    }


    @Override
    public List<StudentPermitDto> getAllAppliedPermitsById(long studentId) {
        return studentPermitRepository.findAllByUserId(studentId).stream().map(StudentPermit::getStudentPermitDto).collect(Collectors.toList());
    }


    @Override
    public StudentDto updateStudent(long studentId, StudentDto studentDto) throws Exception {
        Optional<User> opt = userRepository.findById(studentId);

        if (opt.isPresent()) {
            User user = opt.get();
            user.setName(studentDto.getName());
            user.setAddress(studentDto.getAddress());
            user.setBirthDate(studentDto.getBirthDate());
            user.setEmail(studentDto.getEmail());
            user.setGender(studentDto.getGender());
            user.setStudentClass(studentDto.getStudentClass());

            User updatedUser = userRepository.save(user);
            StudentDto updatedDto = new StudentDto();
            updatedDto.setId(updatedUser.getId());

            return updatedDto;
        }

        throw new Exception("User doesn't exist");

    }


}
	


