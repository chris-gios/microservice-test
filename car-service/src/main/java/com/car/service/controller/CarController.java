package com.car.service.controller;

import com.car.service.entity.Car;
import com.car.service.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> listarCarros(){
        List<Car> cars = carService.getAll();
        if(cars.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> obtenerCarro(@PathVariable("id") int id){
        Car car = carService.getCarroById(id);
        if(car == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(car);
    }

    @PostMapping
    public ResponseEntity<Car> guardarCarro(@RequestBody Car car){
        Car newCar = carService.save(car);
        return ResponseEntity.ok(newCar);
    }

}
