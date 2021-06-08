package com.example.demo.controllers;

import com.example.demo.services.PublisherService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarsController {

    private final PublisherService service;

    public CarsController(PublisherService service) {
        this.service = service;
    }

    /*@GetMapping
    public void send(){
        service.send();
    }*/
}
