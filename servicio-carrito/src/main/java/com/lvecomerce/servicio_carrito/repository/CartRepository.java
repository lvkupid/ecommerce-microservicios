package com.lvecomerce.servicio_carrito.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lvecomerce.servicio_carrito.model.Cart;

@Repository
public class CartRepository {
    private Map<Long, Cart> carts = new HashMap<>();

    public CartRepository() {
        carts.put(1L, new Cart(1L));
    }

    public Optional<Cart> findById(Long id) {
        return Optional.ofNullable(carts.get(id));
    }

    public void save(Cart cart) {
        carts.put(cart.getId(), cart);
    }
}
