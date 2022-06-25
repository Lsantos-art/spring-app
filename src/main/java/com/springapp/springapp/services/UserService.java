package com.springapp.springapp.services;

import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import com.springapp.springapp.models.UserModel;
import com.springapp.springapp.repositories.UserRepository;

@Service
public class UserService {

    UserRepository repository;
    PasswordEncoder passwordEncoder;
 
    public UserService(UserRepository repository) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserModel save(UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public Page<UserModel> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public UserModel findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(UserModel user) {
        repository.delete(user);
    }

    public UserModel findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public boolean checkPassword(String password, String userPassword) {
        return passwordEncoder.matches(password, userPassword);
    }

}
