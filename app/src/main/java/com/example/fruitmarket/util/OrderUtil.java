package com.example.fruitmarket.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.fruitmarket.fruit.Fruit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderUtil {
    public OrderUtil() {}

    // List the featured fruits. They are ordered by popularity.
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Fruit> orderByFeatured(List<Fruit> fruits) {
        List<Fruit> output = new ArrayList<>();

        for (Fruit fruit : fruits) {
            if (fruit.checkIfFeatured()) {
                output.add(fruit);
            }
        }

        return orderByPopularity(output);
    }

    // Default way of ordering the fruits.
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Fruit> orderByPopularity(List<Fruit> fruits) {
        return fruits.stream().sorted(Comparator.comparingInt(Fruit::getPopularity))
                .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Fruit> orderByName(List<Fruit> fruits) {
        Stream<Fruit> sortedStream = fruits.stream().sorted(Comparator.comparing(Fruit::getName));
        return sortedStream.collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Fruit> orderByPrice(List<Fruit> fruits, boolean lowToHigh) {
        if (lowToHigh) {
            Collections.sort(fruits, new FruitPriceComparator());
            return fruits;
        } else {
            return fruits;
        }
    }

    class FruitPriceComparator implements Comparator<Fruit> {
        @Override
        public int compare(Fruit first, Fruit second) {
            return Float.compare(first.getPrice(), second.getPrice());
        }
    }
}
