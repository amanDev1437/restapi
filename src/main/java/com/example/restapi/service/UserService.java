package com.example.restapi.service;

import com.example.restapi.models.User;
import com.example.restapi.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User createUser(User user){

        return userRepo.save(user);
    }

    public List<User> getAllUser(){
        return userRepo.findAll();
    }

    public User getUserById(int id){
        Optional<User> user = userRepo.findById(id);

        return user.orElse(null);
    }

    public void deleteUser(int id){
        userRepo.deleteById(id);
    }


}
