package com.perfume.store.service;


import com.perfume.store.model.Product;
import com.perfume.store.model.ProductRequest;
import com.perfume.store.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(String category, String brand) {
        if (category != null && brand != null) {
            return productRepository.findByCategoryAndBrand(category, brand);
        } else if (category != null) {
            return productRepository.findByCategory(category);
        } else if (brand != null) {
            return productRepository.findByBrand(brand);
        } else {
            return productRepository.findAll();
        }
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    public Product addProduct(ProductRequest request) {
        if (request.getStock() == null) {
            throw new IllegalArgumentException("Stock cannot be null");
        }
        if (request.getCategory() == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        if (request.getBrand() == null) {
            throw new IllegalArgumentException("Brand cannot be null");
        }
        if (request.getDescription() == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        if (request.getPrice() == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        if (request.getStock() == null) {
            throw new IllegalArgumentException("Stock cannot be null");
        }
        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setStock(request.getStock());
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductRequest request) {
        Product existingProduct = getProductById(id);
        existingProduct.setName(request.getName());
        existingProduct.setCategory(request.getCategory());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setStock(request.getStock());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        Product existingProduct = getProductById(id);
        productRepository.delete(existingProduct);
    }
}

