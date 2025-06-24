package com.lvecomerce.servicio_carrito.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void addItemToCart(Long cartId, CartItem newItem) {
        // Buscamos el carrito interno
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

        // Verificamos si la lista de items es nula (buena práctica)
        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }

        // --- LÓGICA MEJORADA ---
        // Buscamos si ya existe un item para este producto en el carrito
        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(newItem.getProductId()))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            // Si el item ya existe, incrementamos su cantidad
            CartItem existingItem = existingItemOpt.get();
            
            int oldQuantity = existingItem.getQuantity();
            int newQuantity = oldQuantity + newItem.getQuantity();
            
            existingItem.setQuantity(newQuantity);
            
            // ✅ Imprimimos en consola para debug
            System.out.println("Producto ya en carrito. Cantidad actualizada de " + oldQuantity + " a " + newQuantity);
        } else {
            // Si el item no existe, lo agregamos a la lista
            cart.getItems().add(newItem);

            // ✅ También podrías imprimir esta parte si querés ver el caso contrario
            System.out.println("Producto nuevo agregado al carrito con cantidad: " + newItem.getQuantity());
        }

        // Guardamos el carrito actualizado
        cartRepository.save(cart);
    }
}
