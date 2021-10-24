package com.example.fruitmarket.models;

import java.util.List;

public interface IProduct {

    public long getId();
    public String getName();
    public String getCategory();
    public String getProducer();
    public String getDescription();
    public boolean getInStock();
    public int getPopularity();
    public void incrementPopularity();
    public List<String> getAttributeNames();
    public List<String> getAttributeValues();
    public List<String> getImages();
}
