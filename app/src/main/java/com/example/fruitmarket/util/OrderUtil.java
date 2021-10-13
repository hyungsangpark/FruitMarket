package com.example.fruitmarket.util;

public class OrderUtil {
    public OrderUtil() {}

    public List<Fruit> orderByPopularity(List<Fruit> fruits) {
        return fruits.stream().sorted(Comparator.comparingInt(Fruit::getPopularity))
                .collect(Collectors.toList());
    }

    public List<Fruit> orderByName(List<Fruit> fruits) {
        return fruits.stream().sorted((fruit1, fruit2)
                -> fruit1.getName().compareTo(fruit2.getName()));
    }

    public List<Fruit> orderByPrice(List<Fruit> fruits, boolean lowToHigh) {
        if (lowToHigh) {
            return fruits.stream().sorted(Comparator.comparingInt(Fruit::getPrice))
                    .collect(Collectors.toList());
        } else {
            return fruits.stream().sorted(Comparator.comparingInt(Fruit::getPrice)
                    .reversed()).collect(Collectors.toList());
        }
    }
}
