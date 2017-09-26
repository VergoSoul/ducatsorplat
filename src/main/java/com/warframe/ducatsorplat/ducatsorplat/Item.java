package com.warframe.ducatsorplat.ducatsorplat;

import lombok.Data;
import java.io.Serializable;

@Data
public class Item implements Serializable {
    private String name;
    private String message;
    private Integer platValue;
    private Integer ducatValue;

    public Item() {
    }

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, Integer plat, Integer ducat, String message) {
        this.name = name;
        this.platValue = plat;
        this.ducatValue = ducat;
        this.message = message;
    }
}
