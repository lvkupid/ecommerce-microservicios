package com.lvecomerce.servicio_carrito.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lvecomerce.servicio_carrito.model.Cart;
import com.lvecomerce.servicio_carrito.model.CartItem;
import com.lvecomerce.servicio_carrito.repository.CartRepository;
import com.lvecomerce.servicio_carrito.service.dto.cartdto;
import com.lvecomerce.servicio_carrito.service.dto.cartitemdto;
import com.lvecomerce.servicio_carrito.service.dto.productdto;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RestTemplate restTemplate;

    //find by id
    public cartdto findCartById(Long id) {
        // 1. Buscamos el carrito interno como antes
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + id));

        // 2. Creamos la lista de items que vamos a devolver
        List<cartitemdto> itemsDTO = new ArrayList<>();
        double total = 0.0;

        // 3. Recorremos los items del carrito interno
        for (CartItem item : cart.getItems()) {
            // 4. POR CADA ITEM, LLAMAMOS AL SERVICIO DE PRODUCTOS
            String productApiUrl = "http://products-service:8080/api/products/" + item.getProductId();
            productdto product = restTemplate.getForObject(productApiUrl, productdto.class);

            // 5. Creamos el CartItemDTO con la info completa
            cartitemdto itemDTO = new cartitemdto();
            itemDTO.setProduct(product);
            itemDTO.setQuantity(item.getQuantity());
            itemsDTO.add(itemDTO);

            // 6. Sumamos al precio total
            if (product != null) {
                total += product.getPrice() * item.getQuantity();
            }
        }

        // 7. Creamos y devolvemos el DTO final del carrito
        cartdto cartDTO = new cartdto();
        cartDTO.setId(cart.getId());
        cartDTO.setItems(itemsDTO);
        cartDTO.setTotalPrice(total);

        return cartDTO;
    }

    //add item to cart
    public void addItemToCart(Long cartId, CartItem item) {
        // Buscamos el carrito interno desde el repositorio, no con el otro método.
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }
        cart.getItems().add(item);
        cartRepository.save(cart);
    }
}
