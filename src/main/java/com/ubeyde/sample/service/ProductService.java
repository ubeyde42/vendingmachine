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

    private static final int EMAIL_NOTIFY_LIMIT = 85;
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }


    @EventListener
    public void handleProductBoughtEvent(ProductBoughtEvent event) {
        //when a product sold, its stock quaintity will be updated, check its remaining quantity and send email if necessary
        Product product = getProductById(event.getProductId());
        if(product.getStockQuantity() < EMAIL_NOTIFY_LIMIT) {
            System.out.println("EMAIL SENT FOR PRODUCT QUANTITY");
        }
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
