package com.example.fruitmarket.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Fruits that correspond to apple.
 */
public class Blueberry extends Fruit {

    Boolean ripe;
    String colour;
    Boolean outOfSeason;

    public Blueberry() {

    }

    public Boolean getRipe() {
        return ripe;
    }

    public void setRipe(Boolean ripe) {
        this.ripe = ripe;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Boolean getOutOfSeason() {
        return outOfSeason;
    }

    public void setOutOfSeason(Boolean outOfSeason) {
        this.outOfSeason = outOfSeason;
    }

    /**
     * Return all unique attributes names of this fruit as a list
     * @return List<String> List of all unique attributes names of this fruit
     */
    public List<String> getAttributeNames() {
        List<String> names = new ArrayList<>();
        names.addAll(getFruitAttributeNames());
        names.add("Ripe:");
        names.add("Colour:");
        names.add("Out of season:");

        return names;
    }

    /**
     * Return all unique attributes values of this fruit as a list
     * @return List<String> List of all unique attributes values of this fruit
     */
    public List<String> getAttributeValues() {
        List<String> values = new ArrayList<>();
        values.addAll(getFruitAttributeValues());

        if (ripe) {
            values.add("Yes");
        } else {
            values.add("No");
        }

        values.add(colour);

        if (outOfSeason) {
            values.add("Yes");
        } else {
            values.add("No");
        }

        return values;
    }
}
