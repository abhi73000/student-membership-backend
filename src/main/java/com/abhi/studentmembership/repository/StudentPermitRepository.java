package com.abhi.studentmembership.repository;

import java.util.List;

import com.abhi.studentmembership.entities.StudentPermit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentPermitRepository extends JpaRepository<StudentPermit, Long> {

	List<StudentPermit> findAllByUserId(long studentId);

}
