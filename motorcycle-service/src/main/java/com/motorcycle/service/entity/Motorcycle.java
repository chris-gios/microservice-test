package com.motorcycle.service.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "motorcycle")
public class Motorcycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String marca;
    private String modelo;
    private int usuarioId;

}
