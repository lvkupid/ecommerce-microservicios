package com.lvecomerce.servicio_carrito.service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class cartdto {
    private Long id;
    private List<cartitemdto> items;
    private Double totalPrice;
}
