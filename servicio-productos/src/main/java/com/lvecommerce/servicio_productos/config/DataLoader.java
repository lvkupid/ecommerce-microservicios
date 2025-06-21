package com.lvecommerce.servicio_productos.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.lvecommerce.servicio_productos.model.Product;
import com.lvecommerce.servicio_productos.repository.ProductRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    public void run(String... args) throws Exception {
        if(productRepository.count() == 0) {
            Product product1 = new Product();
            product1.setName("Laptop Pro");
            product1.setDescription("Laptop de alto rendimiento para profesionales.");
            product1.setPrice(1200.50);

            Product product2 = new Product();
            product2.setName("Mouse Inalámbrico");
            product2.setDescription("Mouse ergonómico con conexión bluetooth.");
            product2.setPrice(45.00);

            productRepository.saveAll(List.of(product1, product2));
            System.out.println(">>> Datos de prueba cargados <<<");
        }
    }
}
