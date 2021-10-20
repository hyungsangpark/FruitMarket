package com.example.fruitmarket.models;

import java.util.ArrayList;
import java.util.List;

public class Orange extends Fruit {

    ProduceSeason produceSeason;
    String usage;
    String grade;
    Boolean seedless;
    Boolean sweetened;

    public Orange() {

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

    public Boolean getSeedless() {
        return seedless;
    }

    public void setSeedless(Boolean seedless) {
        this.seedless = seedless;
    }

    public Boolean getSweetened() {
        return sweetened;
    }

    public void setSweetened(Boolean sweetened) {
        this.sweetened = sweetened;
    }

    public List<String> getAttributeNames() {
        List<String> names = new ArrayList<>();
        names.addAll(getFruitAttributeNames());
        names.add("Produce season:");
        names.add("Usage:");
        names.add("Grade:");
        names.add("Seedless:");
        names.add("Sweetened:");

        return names;
    }

    public List<String> getAttributeValues() {
        List<String> values = new ArrayList<>();
        values.addAll(getFruitAttributeValues());
        values.add(produceSeason.toString());
        values.add(usage);
        values.add(grade);

        if (seedless) {
            values.add("Yes");
        } else {
            values.add("No");
        }

        if (sweetened) {
            values.add("Yes");
        } else {
            values.add("No");
        }

        return values;
    }
}
