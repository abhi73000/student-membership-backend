package com.abhi.studentmembership.service;

import java.util.ArrayList;
import java.util.Optional;

import com.abhi.studentmembership.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abhi.studentmembership.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImp implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> opt = userRepository.findByEmail(email);

        if (opt.isEmpty()) {
            throw new UsernameNotFoundException("User not found");

        }
        return new org.springframework.security.core.userdetails.User(opt.get().getEmail(), opt.get().getPassword(), new ArrayList<>());

    }

}
