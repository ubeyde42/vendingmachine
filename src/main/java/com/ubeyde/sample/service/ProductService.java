package com.ubeyde.sample.service;

import com.ubeyde.sample.dto.ProductSaveRequest;
import com.ubeyde.sample.entity.Product;
import com.ubeyde.sample.event.ProductBoughtEvent;
import com.ubeyde.sample.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @EventListener
    public void handleProductBoughtEvent(ProductBoughtEvent event) {
        //when a product sold, its stock quaintity will be updated
        Product product = getProductById(event.getProductId());
        product.setStockQuantity(product.getStockQuantity()-1);
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void addNewProduct(ProductSaveRequest model) {
        Product newProduct = new Product();
        newProduct.setName(model.getName());
        newProduct.setPrice(model.getPrice());
        newProduct.setStockQuantity(model.getStockQuantity());

        productRepository.save(newProduct);
    }

    public void updateProductStock(Long id, int quantity) {
        Product product = getProductById(id);
        if (product != null) {
            product.setStockQuantity(quantity);
            productRepository.save(product);
        }
    }
}
