package com.example.spring.batch.demo.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    public String hello() {
        return "Hello Quartz";
    }
}
