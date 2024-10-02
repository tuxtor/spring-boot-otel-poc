package com.vorozco.springboot_client_demo.repository;


import com.vorozco.springboot_client_demo.model.AdmBook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(value="books", url="http://localhost:8080/books")
public interface AdmBookClient {
    
    @GetMapping
    List<AdmBook> getBooks();
    
}