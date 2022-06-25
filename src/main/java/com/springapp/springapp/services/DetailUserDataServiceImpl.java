package com.springapp.springapp.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.springapp.springapp.configuration.constants.UserConstants;
import com.springapp.springapp.configuration.security.DetailUserData;
import com.springapp.springapp.models.UserModel;
import com.springapp.springapp.repositories.UserRepository;

@Component
public class DetailUserDataServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public DetailUserDataServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepository.findByEmail(username);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException(UserConstants.USER_NOT_FOUND);
        }

        return new DetailUserData(user);
    }
    
}
