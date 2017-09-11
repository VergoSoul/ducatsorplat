package com.warframe.ducatsorplat.ducatsorplat;

import com.google.gson.Gson;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;
import org.jsoup.nodes.Document;
import org.jsoup.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController
{
    @RequestMapping("/ducats")
    @ResponseBody
    public List<Item> getDucatValues()
    {
        ArrayList<Item> items = new ArrayList<>();

        try {
            Document doc = Jsoup.connect("http://warframe.wikia.com/wiki/Ducats/Prices").get();
            Elements names = doc.select("#mw-customcollapsible-ducatsprices td:nth-of-type(1)");
            Elements ducatValues = doc.select("td:nth-of-type(3) span");

            for (int i = 0; i < names.size(); i++) {

                Item newItem = new Item();
                newItem.setName(((TextNode)names.get(i).childNode(2).childNode(0)).text());
                newItem.setDucatValue(Integer.parseInt(((TextNode)ducatValues.get(i).childNode(0)).text()));
                items.add(newItem);
            }
        }
        catch (java.io.IOException ex) {
        }

        return items;
    }

    @RequestMapping("/plat")
    @ResponseBody
    public MarketValue getPlatValues() {
        MarketValue data = new MarketValue();
        List<Item> items = getDucatValues();
        try {
            InputStream input = new URL("http://warframe.market/api/get_orders/Blueprint/" + items.get(0).getName().replace(" ", "%20")).openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            data = new Gson().fromJson(reader, MarketValue.class);
        }
        catch (java.io.IOException ex) {
        }

        return data;
    }
}
