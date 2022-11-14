package com.user.service.controller;

import com.user.service.entity.User;
import com.user.service.modelos.Car;
import com.user.service.modelos.Motorcycle;
import com.user.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<Car>> listarCarros(@PathVariable("usuarioId") int id){
        User user = userService.getUserById(id);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        List<Car> carList = userService.getCarros(id);
        return ResponseEntity.ok(carList);
    }

    @GetMapping("/motorcycle/{usuarioId}")
    public ResponseEntity<List<Motorcycle>> listarMotos(@PathVariable("usuarioId") int id){
        User usuario = userService.getUserById(id);
        if(usuario == null) {
            return ResponseEntity.notFound().build();
        }

        List<Motorcycle> motorcycleList = userService.getMotos(id);
        return ResponseEntity.ok(motorcycleList);
    }

    @PostMapping("/car/{usuarioId}")
    public ResponseEntity<Car> guardarCarro(@PathVariable("usuarioId") int usuarioId,@RequestBody Car car){
        Car newCar = userService.saveCarro(usuarioId, car);
        return ResponseEntity.ok(newCar);
    }

    @PostMapping("/motorcycle/{usuarioId}")
    public ResponseEntity<Motorcycle> guardarMoto(@PathVariable("usuarioId") int usuarioId,@RequestBody Motorcycle motorcycle){
        Motorcycle newMotorcycle = userService.saveMoto(usuarioId, motorcycle);
        return ResponseEntity.ok(newMotorcycle);
    }

    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable("usuarioId") int usuarioId){
        Map<String,Object> resultado = userService.getUsuarioAndVehiculos(usuarioId);
        return ResponseEntity.ok(resultado);
    }

}