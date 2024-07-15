package com.TodoApp.service;

import com.TodoApp.model.User;
import com.TodoApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getUsers(){
        return new ArrayList<User>(userRepository.findAll());
    }

    public User getUser(Long id){
        Optional<User> res = userRepository.findById(id);
        return res.orElse(null);
    }

    public Long addUser(User User){
        User newUser = userRepository.save(User);
        return newUser.getId();
    }

    public void updateUser(Long id, User User){
        User.setId(id);
        userRepository.save(User);
    }

    public void deleteUser(Long id){
        userRepository.delete(getUser(id));
    }
}
