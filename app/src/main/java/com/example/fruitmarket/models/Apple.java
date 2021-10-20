package com.example.fruitmarket.models;

public class Apple extends Fruit{

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
}
