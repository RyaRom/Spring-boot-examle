package com.TodoApp.service;

import com.TodoApp.model.UserAuthDetails;
import com.TodoApp.model.User;
import com.TodoApp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserAuthDetailsService implements UserDetailsService {
    final UserRepository userRepository;

    public UserAuthDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserAuthDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username +  " no such user"));
        return new UserAuthDetails(user);
    }
}
