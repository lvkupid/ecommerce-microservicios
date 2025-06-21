package com.lvecomerce.servicio_carrito.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvecomerce.servicio_carrito.model.Cart;
import com.lvecomerce.servicio_carrito.model.CartItem;
import com.lvecomerce.servicio_carrito.repository.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    //find by id
    public Cart findCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + id));
    }

    //add item to cart
    public void addItemToCart(Long cartId, CartItem item) {
        Cart cart = findCartById(cartId);
        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }
        cart.getItems().add(item);
        cartRepository.save(cart);
    }
}
