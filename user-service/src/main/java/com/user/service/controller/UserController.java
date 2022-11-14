package com.user.service.controller;

import com.user.service.entity.User;
import com.user.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> listarUsuarios(){
        System.out.println("Llego get");
        List<User> usuarios = userService.getAll();
        if(usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> obtenerUsuario(@PathVariable("id") int id){
        System.out.println("Llego get con id");
        User usuario = userService.getUserById(id);
        if(usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<User> guardarUsuario(@RequestBody User user){
        System.out.println("Llego post");
        User newUser = userService.save(user);
        return ResponseEntity.ok(newUser);
    }

}