package com.example.fruitmarket.models;

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
}
