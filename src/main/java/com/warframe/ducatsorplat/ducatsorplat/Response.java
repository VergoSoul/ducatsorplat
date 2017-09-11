package com.warframe.ducatsorplat.ducatsorplat;

import lombok.Data;
import java.util.List;

@Data
public class Response {
    boolean render_rank;
    List<BuySellItem> buy;
    List<BuySellItem> sell;
}
