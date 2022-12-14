package com.user.service.service;

import com.user.service.entity.User;
import com.user.service.feignclients.CarFeignClient;
import com.user.service.feignclients.MotorcycleFeignClient;
import com.user.service.modelos.Car;
import com.user.service.modelos.Motorcycle;
import com.user.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CarFeignClient carFeignClient;

    @Autowired
    private MotorcycleFeignClient motorcycleFeignClient;

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

    public Car saveCarro(int usuarioId, Car car) {
        car.setUsuarioId(usuarioId);
        Car newCar = carFeignClient.save(car);
        return newCar;
    }

    public Motorcycle saveMoto(int usuarioId, Motorcycle motorcycle) {
        motorcycle.setUsuarioId(usuarioId);
        Motorcycle newMotorcycle = motorcycleFeignClient.save(motorcycle);
        return newMotorcycle;
    }

    public Map<String, Object> getUsuarioAndVehiculos(int usuarioId){
        Map<String,Object> resultado = new HashMap<>();
        User usuario = userRepository.findById(usuarioId).orElse(null);

        if(usuario == null) {
            resultado.put("Mensaje", "El usuario no existe");
            return resultado;
        }

        resultado.put("Usuario",usuario);
        List<Car> carros = carFeignClient.getCarros(usuarioId);
        if(carros.isEmpty()) {
            resultado.put("Carros", "El usuario no tiene carros");
        }
        else {
            resultado.put("Carros", carros);
        }

        List<Motorcycle> motos = motorcycleFeignClient.getMotos(usuarioId);
        if(motos.isEmpty()) {
            resultado.put("Motos", "El usuario no tiene motos");
        }
        else {
            resultado.put("Motos", motos);
        }
        return resultado;
    }

}
