package com.example.fruitmarket.util;

public class OrderUtil {
    public OrderUtil() {}

    // List the featured fruits. They are ordered by popularity.
    public List<Fruit> orderByFeatured(List<Fruit> fruits) {
        List<Fruit> output = new ArrayList<>();

        for (Fruit fruit : fruits) {
            if (fruit.getIsFeatured()) {
                output.add(fruit);
            }
        }

        return orderByPopularity(output);
    }

    // Default way of ordering the fruits.
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
