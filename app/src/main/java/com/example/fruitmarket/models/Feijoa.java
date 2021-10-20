package com.example.fruitmarket.models;

public class Feijoa extends Fruit {
    Boolean ripe;
    String skinColour;
    Boolean outOfSeason;
    float seedToPulpRatio;

    public Feijoa() {

    }

    public Boolean getRipe() {
        return ripe;
    }

    public void setRipe(Boolean ripe) {
        this.ripe = ripe;
    }

    public String getSkinColour() {
        return skinColour;
    }

    public void setSkinColour(String skinColour) {
        this.skinColour = skinColour;
    }

    public Boolean getOutOfSeason() {
        return outOfSeason;
    }

    public void setOutOfSeason(Boolean outOfSeason) {
        this.outOfSeason = outOfSeason;
    }

    public float getSeedToPulpRatio() {
        return seedToPulpRatio;
    }

    public void setSeedToPulpRatio(float seedToPulpRatio) {
        this.seedToPulpRatio = seedToPulpRatio;
    }
}
