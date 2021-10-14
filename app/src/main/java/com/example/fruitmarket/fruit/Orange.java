package com.example.fruitmarket.fruit;

public class Orange extends Fruit{

    private ProduceSeason produceSeason;
    private String usage;
    private String grade;
    private Boolean seedless;
    private Boolean sweetened;

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
}
