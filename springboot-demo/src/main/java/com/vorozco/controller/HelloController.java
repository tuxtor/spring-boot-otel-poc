package com.vorozco.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.util.Random;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String sayHello() throws UnknownHostException {

        Random rand = new Random();

        try {
            Thread.sleep(1000*(rand.nextInt(10)+1));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Get local IP
        String ip = java.net.InetAddress.getLocalHost().getHostAddress();

        return "Micrometer Tracing es genial " + ip;
    }
}
