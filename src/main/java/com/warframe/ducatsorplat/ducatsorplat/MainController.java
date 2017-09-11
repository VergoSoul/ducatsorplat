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
import java.util.OptionalInt;

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
            for (Item i : items) {
                InputStream input = new URL("http://warframe.market/api/get_orders/Blueprint/" + i.getName().replace(" ", "%20")).openStream();
                Reader reader = new InputStreamReader(input, "UTF-8");
                data = new Gson().fromJson(reader, MarketValue.class);

                OptionalInt buyValue = data.getResponse().getBuy().stream().filter(b -> b.online_status == true).mapToInt(b -> b.getPrice()).max();
                OptionalInt sellValue = data.getResponse().getSell().stream().filter(b -> b.online_status == true).mapToInt(b -> b.getPrice()).min();

                if (buyValue.isPresent() && sellValue.isPresent()) {
                    i.setPlatValue(Math.round((buyValue.getAsInt() + sellValue.getAsInt()) / 2.0f));
                }
            }
        }
        catch (java.io.IOException ex) {
        }

        return data;
    }
}
