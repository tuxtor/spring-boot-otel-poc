package com.vorozco.springboot_client_demo.controller;

import com.vorozco.springboot_client_demo.repository.AdmBookClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trace-demo")
public class TraceDemoController {
    
    @Autowired
    AdmBookClient admBookClient;

    @GetMapping
    public String execDemo(){
        return "Obtuve del segundo microservicio: "+admBookClient.getBooks().toString();
    }
    
}