package com.example.cqrsproductcommandservice.controller;

import com.example.cqrsproductcommandservice.dto.ProductEvent;
import com.example.cqrsproductcommandservice.entity.Product;
import com.example.cqrsproductcommandservice.service.ProductCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductCommandController {

    @Autowired
    private ProductCommandService productCommandService;

    @PostMapping("/create")
    public Product createProduct(@RequestBody ProductEvent productEvent) {
        return productCommandService.createProduct(productEvent);
    }

    @PutMapping("/update/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody ProductEvent productEvent) {
        return productCommandService.updateProduct(id, productEvent);
    }

    }






























