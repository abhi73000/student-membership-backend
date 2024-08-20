package com.abhi.studentmembership.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.abhi.studentmembership.entities.Fee;
import com.abhi.studentmembership.entities.StudentPermit;
import com.abhi.studentmembership.entities.Teacher;
import com.abhi.studentmembership.entities.User;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.abhi.studentmembership.dto.FeeDto;
import com.abhi.studentmembership.dto.SStudentDto;
import com.abhi.studentmembership.dto.STeacherDto;
import com.abhi.studentmembership.dto.StudentDto;
import com.abhi.studentmembership.dto.StudentPermitDto;
import com.abhi.studentmembership.dto.TeacherDto;
import com.abhi.studentmembership.enums.StudentPermitStatus;
import com.abhi.studentmembership.enums.UserRole;
import com.abhi.studentmembership.repository.FeeRepository;
import com.abhi.studentmembership.repository.StudentPermitRepository;
import com.abhi.studentmembership.repository.TeacherRepository;
import com.abhi.studentmembership.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImp implements AdminService {

    private final UserRepository userRepository;

    private final FeeRepository feeRepository;

    private final StudentPermitRepository studentPermitRepository;

    private final TeacherRepository teacherRepository;


    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);

        if (adminAccount == null) {
            User admin = new User();
            admin.setEmail("admin@admin.com");
            admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            admin.setName("Admin");
            admin.setRole(UserRole.ADMIN);
            userRepository.save(admin);
        }


    }


    @Override
    public StudentDto postStudent(StudentDto studentDto) {
        Optional<User> opt = userRepository.findByEmail(studentDto.getEmail());
        if (opt.isEmpty()) {
            User user = new User();
            BeanUtils.copyProperties(studentDto, user);
            user.setPassword(new BCryptPasswordEncoder().encode(studentDto.getPassword()));
            user.setRole(UserRole.STUDENT);
            User createdUser = userRepository.save(user);
            StudentDto createdDto = new StudentDto();
            createdDto.setId(createdUser.getId());
            createdDto.setEmail(createdUser.getEmail());

            return createdDto;
        }
        return null;
    }


    @Override
    public List<StudentDto> getAllStudents() {

        return userRepository.findAllByRole(UserRole.STUDENT).stream().map(User::getStudentDto).collect(Collectors.toList());
    }


    @Override
    public void deleteStudent(long studentId) {
        userRepository.deleteById(studentId);

    }


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
    public StudentDto updateStudent(Long studentId, StudentDto studentDto) throws Exception {
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


    @Override
    public FeeDto payFee(long studentId, FeeDto feeDto) {
        Optional<User> opt = userRepository.findById(studentId);

        if (opt.isPresent()) {
            Fee fee = new Fee();
            fee.setAmount(feeDto.getAmount());
            fee.setCreatedAt(new Date());
            fee.setMonth(feeDto.getMonth());
            fee.setDescription(feeDto.getDescription());
            fee.setGivenBy(feeDto.getGivenBy());
            fee.setUser(opt.get());
            Fee paidFee = feeRepository.save(fee);
            FeeDto fDto = new FeeDto();
            fDto.setId(paidFee.getId());

            return fDto;
        }
        return null;
    }


    @Override
    public List<StudentPermitDto> getAllAppliedPermits() {
        return studentPermitRepository.findAll().stream().map(StudentPermit::getStudentPermitDto).collect(Collectors.toList());
    }


    @Override
    public StudentPermitDto changePermitStatus(Long permitId, String status) {
        Optional<StudentPermit> opt = studentPermitRepository.findById(permitId);

        if (opt.isPresent()) {
            StudentPermit permit = opt.get();
            if (Objects.equals(status, "Approved")) {
                permit.setStudentPermitStatus(StudentPermitStatus.Approved);
            } else {
                permit.setStudentPermitStatus(StudentPermitStatus.Disapproved);
            }
            StudentPermit updatedPermit = studentPermitRepository.save(permit);
            StudentPermitDto updatedDto = new StudentPermitDto();
            updatedDto.setId(updatedPermit.getId());

            return updatedDto;
        }
        return null;
    }


    @Override
    public TeacherDto postTeacher(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherDto, teacher);
        Teacher createdTeacher = teacherRepository.save(teacher);
        TeacherDto createdDto = new TeacherDto();
        createdDto.setId(createdTeacher.getId());

        return createdDto;
    }


    @Override
    public List<TeacherDto> getAllTeachers() {
        return teacherRepository.findAll().stream().map(Teacher::getTeacherDto).collect(Collectors.toList());
    }


    @Override
    public void deleteTeacher(long teacherId) {
        teacherRepository.deleteById(teacherId);

    }


    @Override
    public STeacherDto getTeacherById(long teacherId) throws Exception {
        Optional<Teacher> opt = teacherRepository.findById(teacherId);

        if (opt.isPresent()) {
            STeacherDto tDto = new STeacherDto();
            tDto.setTeacherDto(opt.get().getTeacherDto());
            return tDto;
        }
        throw new Exception("Teacher not found with this " + teacherId + " id");
    }


    @Override
    public TeacherDto updateTeacher(long teacherId, TeacherDto teacherDto) throws Exception {
        Optional<Teacher> opt = teacherRepository.findById(teacherId);

        if (opt.isPresent()) {
            Teacher teacher = opt.get();
            teacher.setName(teacherDto.getName());
            teacher.setDepartment(teacherDto.getDepartment());
            teacher.setQualification(teacherDto.getQualification());
            teacher.setAddress(teacherDto.getAddress());
            teacher.setBirthDate(teacherDto.getBirthDate());
            teacher.setGender(teacherDto.getGender());

            Teacher updatedTeacher = teacherRepository.save(teacher);
            TeacherDto updatedDto = new TeacherDto();
            updatedDto.setId(updatedTeacher.getId());

            return updatedDto;
        }

        throw new Exception("Teacher doesn't exist");

    }
}

