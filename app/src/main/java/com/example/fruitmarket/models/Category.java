package com.example.fruitmarket.models;

import com.example.fruitmarket.R;

import java.util.HashMap;
import java.util.Map;

public class Category {
    String categoryName;
    Integer categoryImageName;
    String categoryDisplayName;
    Map<String, Integer> categoryImages = new HashMap<>();

    public Category(String name){
        categoryName = name;
        categoryImages.put("Apples", R.drawable.apple_cat_img);
        categoryImages.put("Blueberries", R.drawable.blueberries_cat_img);
        categoryImages.put("Feijoas", R.drawable.feijoas_cat_img);
        categoryImages.put("Kiwifruits", R.drawable.kiwifruit_cat_img);
        categoryImages.put("Oranges", R.drawable.oranges_cat_img);

        switch (name) {
            case "apples":
                categoryDisplayName = "Apples";
                break;
            case "blueberries":
                categoryDisplayName = "Blueberries";
                break;
            case "feijoas":
                categoryDisplayName = "Feijoas";
                break;
            case "kiwifruits":
                categoryDisplayName = "Kiwifruits";
                break;
            default:
                categoryDisplayName = "Oranges";
                break;
        }

        categoryImageName = categoryImages.get(getCategoryDisplayName());

    }

    public String getCategoryName(){
        return categoryName;
    }

    public Integer getCategoryImageName() {
        return categoryImageName;
    }

    public String getCategoryDisplayName(){ return categoryDisplayName; }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                ", categoryImageName=" + categoryImageName +
                ", categoryDisplayName='" + categoryDisplayName + '\'' +
                ", categoryImages=" + categoryImages +
                '}';
    }
}
