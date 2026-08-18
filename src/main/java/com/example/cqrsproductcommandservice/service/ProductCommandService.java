package com.example.cqrsproductcommandservice.service;

import com.example.cqrsproductcommandservice.dto.ProductEvent;
import com.example.cqrsproductcommandservice.entity.Product;
import com.example.cqrsproductcommandservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductCommandService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private KafkaTemplate<String, ProductEvent> kafkaTemplate;

    public Product createProduct(ProductEvent productEvent) {
        Product productDto =  productRepository.save(productEvent.getProduct());
        ProductEvent event = new ProductEvent("CreateProduct",productDto);
        kafkaTemplate.send("product-event-topic", event);
        return productDto;
    }

    public Product updateProduct(Long id,ProductEvent productEvent) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            Product newProduct = productEvent.getProduct();
            // Update the product fields
            existingProduct.setName(newProduct.getName());
            existingProduct.setPrice(newProduct.getPrice());
            Product productDto =  productRepository.save(existingProduct);
            ProductEvent event = new ProductEvent("UpdateProduct",productDto);
            kafkaTemplate.send("product-event-topic", event);
            return productDto;
        }
        throw new RuntimeException("Product not found");
    }

}
