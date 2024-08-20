package com.abhi.studentmembership.repository;

import java.util.List;
import java.util.Optional;

import com.abhi.studentmembership.entities.User;
import com.abhi.studentmembership.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

	User findByRole(UserRole admin);

	Optional<User> findByEmail(String email);

	List<User> findAllByRole(UserRole student);

}
