package com.example.fruitmarket;

import java.util.ArrayList;

abstract class Fruit implements IProduct{
    private long id;
    private String name;
    private String category;
    private String producer;
    private ArrayList<String> images;
    private String variety;
    private String[] description;
    private float price;
    private PriceMetric priceMetric;
    private Boolean inStock;

    public String getName(){
        return name;
    };

    public String getCategory(){
        return category;
    }

    public String getProducer(){
        return producer;
    }

    public ArrayList<String> getImages(){
        return images;
    }

    public String getVariety(){
        return variety;
    }

    public String[] getDescription(){
        return description;
    }

    public float getPrice(){
        return price;
    }

    public PriceMetric getPriceMetric(){
        return priceMetric;
    }

    public Boolean getInStock(){
        return inStock;
    }

}
