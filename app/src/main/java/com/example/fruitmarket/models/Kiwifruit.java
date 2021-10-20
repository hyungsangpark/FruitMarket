package com.example.fruitmarket.models;

import java.util.ArrayList;
import java.util.List;

public class Kiwifruit extends Fruit {

    Boolean ripe;
    Boolean hybrid;
    float seedToPulpRatio;
    Boolean containsHLActinidain;
    Boolean outOfSeason;

    public Kiwifruit() {

    }

    public Boolean getRipe() {
        return ripe;
    }

    public void setRipe(Boolean ripe) {
        this.ripe = ripe;
    }

    public Boolean getHybrid() {
        return hybrid;
    }

    public void setHybrid(Boolean hybrid) {
        this.hybrid = hybrid;
    }

    public float getSeedToPulpRatio() {
        return seedToPulpRatio;
    }

    public void setSeedToPulpRatio(float seedToPulpRatio) {
        this.seedToPulpRatio = seedToPulpRatio;
    }

    public Boolean getContainsHLActinidain() {
        return containsHLActinidain;
    }

    public void setContainsHLActinidain(Boolean containsHLActinidain) {
        this.containsHLActinidain = containsHLActinidain;
    }

    public Boolean getOutOfSeason() {
        return outOfSeason;
    }

    public void setOutOfSeason(Boolean outOfSeason) {
        this.outOfSeason = outOfSeason;
    }

    public List<String> getAttributeNames() {
        List<String> names = new ArrayList<>();
        names.addAll(getFruitAttributeNames());
        names.add("Ripe:");
        names.add("Seed/pulp ratio:");
        names.add("Contains actinidain:");
        names.add("Out of season:");

        return names;
    }

    public List<String> getAttributeValues() {
        List<String> values = new ArrayList<>();
        values.addAll(getFruitAttributeValues());

        if (ripe) {
            values.add("Yes");
        } else {
            values.add("No");
        }

        values.add(Float.toString(seedToPulpRatio));

        if (containsHLActinidain) {
            values.add("Yes");
        } else {
            values.add("No");
        }

        if (outOfSeason) {
            values.add("Yes");
        } else {
            values.add("No");
        }

        return values;
    }
}
