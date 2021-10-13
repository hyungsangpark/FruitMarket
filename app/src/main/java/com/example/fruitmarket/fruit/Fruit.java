package com.example.fruitmarket.fruit;

import com.example.fruitmarket.IProduct;
import com.example.fruitmarket.PriceMetric;

import java.util.ArrayList;

abstract class Fruit implements IProduct {
    private long id;
    private String name;
    private String category;
    private String producer;
    private ArrayList<String> images;
    private String variety;
    private String[] description;
    private float price;
    private PriceMetric priceMetric;
    private boolean inStock;
    private int popularity;
    private boolean isFeatured;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    @Override
    public String[] getDescription() {
        return description;
    }

    public void setDescription(String[] description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public PriceMetric getPriceMetric() {
        return priceMetric;
    }

    public void setPriceMetric(PriceMetric priceMetric) {
        this.priceMetric = priceMetric;
    }

    @Override
    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public boolean checkIfFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
    }
}
