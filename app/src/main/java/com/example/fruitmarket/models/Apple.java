package com.example.fruitmarket.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Fruits that correspond to apple.
 */
public class Apple extends Fruit {

    ProduceSeason produceSeason;
    String usage;
    String grade;
    String skinColour;
    RelativeSize size;

    public Apple() {

    }

    public ProduceSeason getProduceSeason() {
        return produceSeason;
    }

    public void setProduceSeason(ProduceSeason produceSeason) {
        this.produceSeason = produceSeason;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSkinColour() {
        return skinColour;
    }

    public void setSkinColour(String skinColour) {
        this.skinColour = skinColour;
    }

    public RelativeSize getSize() {
        return size;
    }

    public void setSize(RelativeSize size) {
        this.size = size;
    }

    /**
     * Return all unique attributes names of this fruit as a list
     * @return List<String> List of all unique attributes names of this fruit
     */
    public List<String> getAttributeNames() {
        List<String> names = new ArrayList<>();
        names.addAll(getFruitAttributeNames());
        names.add("Produce season:");
        names.add("Usage:");
        names.add("Grade:");
        names.add("Skin colour:");
        names.add("Size:");

        return names;
    }

    /**
     * Return all unique attributes values of this fruit as a list
     * @return List<String> List of all unique attributes values of this fruit
     */
    public List<String> getAttributeValues() {
        List<String> values = new ArrayList<>();
        values.addAll(getFruitAttributeValues());
        values.add(produceSeason.toString());
        values.add(usage);
        values.add(grade);
        values.add(skinColour);
        values.add(size.toString());

        return values;
    }
}
