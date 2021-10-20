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
        categoryImages.put("Apples", R.drawable.appleCatImg);
        categoryImages.put("Blueberries", R.drawable.blueberriesCatImg);
        categoryImages.put("Feijoas", R.drawable.feijoasCatImg);
        categoryImages.put("Kiwifruit", R.drawable.kiwifruitCatImg);
        categoryImages.put("Oranges", R.drawable.orangesCatImg);

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
}
