package com.user.service.feignclients;

import com.user.service.modelos.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@FeignClient(name = "car-service", path = "https://localhost:8002/car")
@FeignClient(name = "car-service", path = "https://localhost:8002", url = "/car")
//@FeignClient(name = "car-service", path = "https://localhost:8002")
//@RequestMapping("/car")
public interface CarFeignClient {

    @PostMapping
    Car save(@RequestBody Car car);

    @GetMapping("/user/{usuarioId}")
    List<Car> getCarros(@PathVariable("usuarioId") int usuarioId);

}
