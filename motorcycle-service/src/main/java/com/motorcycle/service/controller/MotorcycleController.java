package com.motorcycle.service.controller;

import com.motorcycle.service.entity.Motorcycle;
import com.motorcycle.service.service.MotorcycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motorcycle")
public class MotorcycleController {

    @Autowired
    private MotorcycleService motorcycleService;

    @GetMapping
    public ResponseEntity<List<Motorcycle>> listarMotos(){
        List<Motorcycle> motorcycleList = motorcycleService.getAll();
        if(motorcycleList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motorcycleList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motorcycle> obtenerMoto(@PathVariable("id") int id){
        Motorcycle motorcycle = motorcycleService.getMotoById(id);
        if(motorcycle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(motorcycle);
    }

    @PostMapping
    public ResponseEntity<Motorcycle> guardarMoto(@RequestBody Motorcycle motorcycle){
        Motorcycle nuevaMoto = motorcycleService.save(motorcycle);
        return ResponseEntity.ok(nuevaMoto);
    }

}