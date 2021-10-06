package com.example.fruitmarket.util;

public class OrderUtil {
    public OrderUtil() {}

    public List<Fruit> orderByName(List<Fruit> fruits) {
        List<Fruit> sortedList = fruits.stream().sorted((fruit1, fruit2)
                -> fruit1.getName().compareTo(fruit2.getName()));

        return sortedList;
    }

    public List<Fruit> orderByPrice(List<Fruit> fruits, boolean lowToHigh) {
        List<Fruit> sortedList;
        if (lowToHigh) {
            sortedList = fruits.stream().sorted(Comparator.comparingInt(Fruit::getPrice))
                    .collect(Collectors.toList());
        } else {
            sortedList = fruits.stream().sorted(Comparator.comparingInt(Fruit::getPrice)
                            .reversed()).collect(Collectors.toList());
        }

        return sortedList;
    }
}
