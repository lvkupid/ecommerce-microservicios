package com.lvecomerce.servicio_carrito.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private Long id;
    private List<CartItem> items = new ArrayList<>();

    public Cart(Long id) {
        this.id = id;
    }
}
