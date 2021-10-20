package com.example.fruitmarket.models;

public class Category {
    String categoryName;
    String categoryImageName;
    String categoryDisplayName;

    public Category(String name){
        categoryName = name;
        categoryImageName = name;
        if(name.equals("apples")){
            categoryDisplayName = "Apples";
        } else if (name.equals("blueberries")){
            categoryDisplayName= "Blueberries";
        } else if (name.equals("feijoas")){
            categoryDisplayName = "Feijoas";
        } else if (name.equals("kiwifruits")){
            categoryDisplayName = "Kiwifruits";
        } else if(name.equals("oranges")){
            categoryDisplayName = "Oranges";
        } else {
        }
    }

    public String getCategoryName(){
        return categoryName;
    }

    public String getCategoryImageName() {
        return categoryImageName;
    }

    public String getCategoryDisplayName(){ return categoryDisplayName; }
}
