package com.example.fruitmarket.models;

import java.util.List;

public interface IProduct {

    public String getName();
    public String getCategory();
    public String getProducer();
    public String getDescription();
    public boolean getInStock();
    public List<String> getAttributeNames();
    public List<String> getAttributeValues();
}
