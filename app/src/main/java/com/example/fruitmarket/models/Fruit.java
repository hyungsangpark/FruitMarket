package com.example.fruitmarket.models;

import java.util.ArrayList;
import java.util.List;

public abstract class Fruit implements IProduct {
    long id;
    String name;
    String category;
    String producer;
    ArrayList<String> images;
    String variety;
    String description;
    float price;
    PriceMetric priceMetric;
    boolean inStock;
    int popularity;
    boolean isFeatured;

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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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
    public boolean getInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public int getPopularity() {
        return popularity;
    }

    public void incrementPopularity() {
        popularity++;
    }

    public boolean checkIfFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public abstract List<String> getAttributeNames();

    public List<String> getFruitAttributeNames() {
        List<String> names = new ArrayList<>();
        names.add("Variety:");
        names.add("Price:");
        names.add("In stock:");

        return names;
    }

    public abstract List<String> getAttributeValues();

    public List<String> getFruitAttributeValues() {
        List<String> values = new ArrayList<>();
        values.add(variety);
        values.add("$" + Float.toString(price) + " " + priceMetric.toString());

        if (inStock) {
            values.add("Yes");
        } else {
            values.add("No");
        }

        return values;
    }
}
