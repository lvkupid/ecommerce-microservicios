package com.lvecomerce.servicio_carrito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lvecomerce.servicio_carrito.model.CartItem;
import com.lvecomerce.servicio_carrito.service.CartService;
import com.lvecomerce.servicio_carrito.service.dto.cartdto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/carts")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    @Autowired
    private CartService cartService;

    // Add methods to handle HTTP requests here, e.g., to add items to the cart,
    // view the cart, etc. For example:
    @GetMapping("/{id}")
    public cartdto getCart(@PathVariable Long id) {
        return cartService.findCartById(id);
    }

    //add item to cart
    @PostMapping("/{id}/items")
    public ResponseEntity<cartdto> addItemToCart(@PathVariable Long id, @RequestBody CartItem item) {
        try {
            cartService.addItemToCart(id, item);
            return ResponseEntity.ok(cartService.findCartById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
