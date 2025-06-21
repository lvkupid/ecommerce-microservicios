package com.lvecomerce.servicio_carrito.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class productdto {
    private Long id;
    private String name;
    private String description;
    private Double price;
}
