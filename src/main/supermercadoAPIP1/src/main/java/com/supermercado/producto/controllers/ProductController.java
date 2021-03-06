package com.proyecto.app.controllers;

import com.proyecto.app.entity.Product;
import com.proyecto.app.repository.ProductRepository;
import com.proyecto.app.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    private Message message = new Message();

    @RequestMapping(value = "api/products/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional> getProduct(@PathVariable Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isPresent()) {
            return message.viewMessage(HttpStatus.OK, "success", "Product found");
        }
        return message.viewMessage(HttpStatus.NOT_FOUND, "Not found", "Product not found");
    }
    @RequestMapping(value= "api/products", method = RequestMethod.POST)
    public ResponseEntity createProduct(@RequestBody Product product){
        Map<String,String> response = new LinkedHashMap<>();
        try{
            product.setName(product.getName());
            productRepository.save(product);
            return message.viewMessage(HttpStatus.OK, "success", "registered product success");
        }catch (Exception e){
            return message.viewMessage(HttpStatus.INTERNAL_SERVER_ERROR, "error", "An error ocurred while registering the product");
        }
    }

    @RequestMapping(value = "api/products", method = RequestMethod.GET)
    public List<Product> listUser(){
        return productRepository.findAll();
    }

    @RequestMapping(value= "api/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity editProduct(@RequestBody Product newProduct, @PathVariable Long id){
        Map<String, String> response = new HashMap<>();
        try{
            Product product = productRepository.findById(id).get();
            product.setName(newProduct.getName());
            product.setDescription(newProduct.getDescription());
            productRepository.save(product);
            return message.viewMessage(HttpStatus.OK, "success", "user edit success");
        }catch(Exception e){
            return message.viewMessage(HttpStatus.NOT_FOUND, "error", "User not found");
        }
    }

    @RequestMapping(value = "api/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProduct(@PathVariable Long id){
        Map<String, String> response = new HashMap<>();
        try{
            Product product = productRepository.findById(id).get();
            productRepository.delete(product);
            return message.viewMessage(HttpStatus.OK, "success", "product delete success");
        }catch(Exception e){
            return message.viewMessage(HttpStatus.NOT_FOUND, "error", "product not found");
        }
    }
}