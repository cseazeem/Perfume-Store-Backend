package com.perfume.store.model;

public class ProductRequest {

    private String name;
    private String category;
    private String brand;
    private Double price;
    private String description;
    private Double stock;

    public ProductRequest(String name, String category, String brand, Double price, String description, Double stock) {
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getStock() {
        return stock;
    }
    public void setStocks(Double stocks) {
        this.stock = stock;
    }
}
