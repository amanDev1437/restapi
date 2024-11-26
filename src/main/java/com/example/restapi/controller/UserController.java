package com.example.restapi.controller;

import com.example.restapi.exception.UserNotFoundException;
import com.example.restapi.models.User;
import com.example.restapi.service.UserService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = userService.createUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();


    }

    @GetMapping("/users")
    public MappingJacksonValue getUsers(){
        List<User> allUser = userService.getAllUser();

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(allUser);

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("password");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserFilter", filter);

        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    @GetMapping("/users/{id}")
    public MappingJacksonValue getUser(@PathVariable int id){
        User user = userService.getUserById(id);
        if(user==null){
            throw new UserNotFoundException("User not found with id:"+id);
        }
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("password");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserFilter", filter);

        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

//    @GetMapping("/users/{id}")
//    public EntityModel<User> getUser(@PathVariable int id){
//        User user = userService.getUserById(id);
//        if(user==null){
//            throw new UserNotFoundException("User not found with id:"+id);
//        }
//        EntityModel<User> entityModel = EntityModel.of(user);
//        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsers());
//        entityModel.add(link.withRel("all-users"));
//        return entityModel;
//    }

    @DeleteMapping("users/{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }



}
