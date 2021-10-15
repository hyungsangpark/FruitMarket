package com.example.fruitmarket.fruit;

public class Orange extends Fruit {

    ProduceSeason season;
    String usage;
    String grade;
    Boolean seedless;
    Boolean sweetened;

    public Orange() {

    }

    public ProduceSeason getSeason() {
        return season;
    }

    public void setSeason(ProduceSeason season) {
        this.season = season;
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
}
