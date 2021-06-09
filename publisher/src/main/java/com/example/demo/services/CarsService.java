package com.example.demo.services;

import com.example.demo.dto.Cars;
import com.example.demo.repository.CarsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarsService {

    private final CarsRepository repository;

    public CarsService(CarsRepository repository) {
        this.repository = repository;
    }

    public List<Cars> getAll(){
        return repository.findAll();
    }
}
