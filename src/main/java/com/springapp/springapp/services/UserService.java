package com.springapp.springapp.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import com.springapp.springapp.models.UserModel;
import com.springapp.springapp.repositories.UserRepository;

@Service
public class UserService {

    UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserModel save(UserModel user) {
        return repository.save(user);
    }

    public Page<UserModel> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

}
