package com.vorozco.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping("/fibo")
public class FibonacciController {

    @GetMapping
    public String doFibonacci(@RequestParam String n) {
        BigInteger fibonacci = new BigInteger(n);
        return getFibonacciNumber(fibonacci).toString();
    }

    public BigInteger getFibonacciNumber(BigInteger n){
        if(n == BigInteger.valueOf(0)){
            return BigInteger.valueOf(0);
        }
        if(BigInteger.ONE.equals(n) || BigInteger.valueOf(2).equals(n)){
            return BigInteger.ONE;
        }
        return getFibonacciNumber( n.subtract(BigInteger.valueOf(2)) )
                .add(getFibonacciNumber(n.subtract(BigInteger.ONE)));
    }

}
