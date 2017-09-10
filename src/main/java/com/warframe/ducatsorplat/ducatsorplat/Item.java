package com.warframe.ducatsorplat.ducatsorplat;

public class Item {
    private String name;
    private Integer platValue;
    private Integer ducatValue;

    public Item() {
    }

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, Integer plat, Integer ducat) {
        this.name = name;
        this.platValue = plat;
        this.ducatValue = ducat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDucatValue() {
        return ducatValue;
    }

    public void setDucatValue(Integer ducatValue) {
        this.ducatValue = ducatValue;
    }

    public Integer getPlatValue() {
        return platValue;
    }

    public void setPlatValue(Integer platValue) {
        this.platValue = platValue;
    }
}
