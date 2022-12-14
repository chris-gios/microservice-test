package com.user.service.feignclients;

import com.user.service.modelos.Motorcycle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@FeignClient(name = "moto-service", url = "https://localhost:8003/motorcycle")
@FeignClient(name = "moto-service", path = "https://localhost:8003", url = "/motorcycle")
//@FeignClient(name = "moto-service", url = "https://localhost:8003")
//@RequestMapping("/motorcycle")
public interface MotorcycleFeignClient {

    @PostMapping
    Motorcycle save(@RequestBody Motorcycle motorcycle);

    @GetMapping("/user/{usuarioId}")
    List<Motorcycle> getMotos(@PathVariable("usuarioId") int usuarioId);

}
