package com.user.service.service;

import com.user.service.entity.User;
import com.user.service.modelos.Car;
import com.user.service.modelos.Motorcycle;
import com.user.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private CarroFeignClient carroFeignClient;
//
//    @Autowired
//    private MotoFeignClient motoFeignClient;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<Car> getCarros(int usuarioId) {
        List<Car> carList = restTemplate.getForObject("http://localhost:8002/car/user/" + usuarioId, List.class);
        return carList;
    }

    public List<Motorcycle> getMotos(int usuarioId) {
        List<Motorcycle> motorcycleList = restTemplate.getForObject("http://localhost:8003/motorcycle/user/" + usuarioId, List.class);
        return motorcycleList;
    }

}
