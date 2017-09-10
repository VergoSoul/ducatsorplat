package com.warframe.ducatsorplat.ducatsorplat;

import lombok.Data;


@Data
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
}
