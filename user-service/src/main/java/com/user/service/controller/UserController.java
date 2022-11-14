package com.user.service.controller;

import com.user.service.entity.User;
import com.user.service.modelos.Car;
import com.user.service.modelos.Motorcycle;
import com.user.service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> listarUsuarios(){
        List<User> usuarios = userService.getAll();
        if(usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> obtenerUsuario(@PathVariable("id") int id){
        User usuario = userService.getUserById(id);
        if(usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<User> guardarUsuario(@RequestBody User user){
        User newUser = userService.save(user);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/car/{usuarioId}")
    @CircuitBreaker(name="carsCB", fallbackMethod = "fallBackGetCars")
    public ResponseEntity<List<Car>> listarCarros(@PathVariable("usuarioId") int id){
        User user = userService.getUserById(id);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        List<Car> carList = userService.getCarros(id);
        return ResponseEntity.ok(carList);
    }

    @GetMapping("/motorcycle/{usuarioId}")
    @CircuitBreaker(name="motorcyclesCB", fallbackMethod = "fallBackGetMotorcycles")
    public ResponseEntity<List<Motorcycle>> listarMotos(@PathVariable("usuarioId") int id){
        User usuario = userService.getUserById(id);
        if(usuario == null) {
            return ResponseEntity.notFound().build();
        }

        List<Motorcycle> motorcycleList = userService.getMotos(id);
        return ResponseEntity.ok(motorcycleList);
    }

    @PostMapping("/car/{usuarioId}")
    @CircuitBreaker(name="carsCB", fallbackMethod = "fallBackSaveCar")
    public ResponseEntity<Car> guardarCarro(@PathVariable("usuarioId") int usuarioId,@RequestBody Car car){
        Car newCar = userService.saveCarro(usuarioId, car);
        return ResponseEntity.ok(newCar);
    }

    @PostMapping("/motorcycle/{usuarioId}")
    @CircuitBreaker(name="motorcyclesCB", fallbackMethod = "fallBackSaveMotorcycle")
    public ResponseEntity<Motorcycle> guardarMoto(@PathVariable("usuarioId") int usuarioId,@RequestBody Motorcycle motorcycle){
        Motorcycle newMotorcycle = userService.saveMoto(usuarioId, motorcycle);
        return ResponseEntity.ok(newMotorcycle);
    }

    @GetMapping("/todos/{usuarioId}")
    @CircuitBreaker(name="todosCB", fallbackMethod = "fallBackGetTodos")
    public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable("usuarioId") int usuarioId){
        Map<String,Object> resultado = userService.getUsuarioAndVehiculos(usuarioId);
        return ResponseEntity.ok(resultado);
    }

    private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable("usuarioId") int id,
                                                          RuntimeException excepcion) {
        return new ResponseEntity("El usuario : " + id + " tiene los carros en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Car> fallBackSaveCar(@PathVariable("usuarioId") int id, @RequestBody Car car,
                                                    RuntimeException excepcion) {
        return new ResponseEntity("El usuario : " + id + " no tiene dinero para los carros", HttpStatus.OK);
    }

    private ResponseEntity<List<Motorcycle>> fallBackGetMotorcycles(@PathVariable("usuarioId") int id, RuntimeException excepcion) {
        return new ResponseEntity("El usuario : " + id + " tiene las motos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Motorcycle> fallBackSaveMotorcycle(@PathVariable("usuarioId") int id, @RequestBody Motorcycle motorcycle,
                                                  RuntimeException excepcion) {
        return new ResponseEntity("El usuario : " + id + " no tiene dinero para las motos", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> fallBackGetTodos(@PathVariable("usuarioId") int id,
                                                                 RuntimeException excepcion) {
        return new ResponseEntity("El usuario : " + id + " tiene los vehiculos en el taller", HttpStatus.OK);
    }

}