package com.warframe.ducatsorplat.ducatsorplat;

import lombok.Data;

@Data
public class BuySellItem {
    boolean online_ingame;
    String ingame_name;
    boolean online_status;
    Integer count;
    Integer price;
}
