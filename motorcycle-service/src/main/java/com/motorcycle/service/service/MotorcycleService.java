package com.motorcycle.service.service;

import com.motorcycle.service.entity.Motorcycle;
import com.motorcycle.service.repository.MotorcycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotorcycleService {

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    public List<Motorcycle> getAll() {
        return motorcycleRepository.findAll();
    }

    public Motorcycle getMotoById(int id) {
        return motorcycleRepository.findById(id).orElse(null);
    }

    public Motorcycle save(Motorcycle motorcycle) {
        Motorcycle newMotorcycle = motorcycleRepository.save(motorcycle);
        return newMotorcycle;
    }

}
