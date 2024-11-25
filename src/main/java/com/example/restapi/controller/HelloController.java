package com.example.restapi.controller;

import com.example.restapi.HelloWorld;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String helloWorld(){

        return "Hello World";
    }

    @GetMapping("/hello-bean")
    public HelloWorld helloWorldBean(){
        return new HelloWorld("Hello World");
    }

    @GetMapping("/hello/{name}")
    public HelloWorld helloWorldName(@PathVariable String name){

        return new HelloWorld("Hello "+name);
    }
}
