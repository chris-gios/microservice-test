package com.car.service.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String marca;
    private String modelo;


}
