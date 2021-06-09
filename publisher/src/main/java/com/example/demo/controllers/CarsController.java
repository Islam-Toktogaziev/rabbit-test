package com.example.demo.controllers;

import com.example.demo.dto.Cars;
import com.example.demo.services.CarsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarsController {

    private final CarsService service;

    public CarsController(CarsService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cars> getAll(){
        return service.getAll();
    }
}
