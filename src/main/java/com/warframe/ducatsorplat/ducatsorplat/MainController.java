package com.warframe.ducatsorplat.ducatsorplat;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;
import org.jsoup.nodes.Document;
import org.jsoup.*;
import java.util.ArrayList;

@RestController
public class MainController
{
    @RequestMapping("/")
    public String getDucatValues()
    {
        StringBuilder sb = new StringBuilder();
        ArrayList<Item> items = new ArrayList<Item>();

        try {

            Document doc = Jsoup.connect("http://warframe.wikia.com/wiki/Ducats/Prices").get();
            Elements names = doc.select("#mw-customcollapsible-ducatsprices td:nth-of-type(1)");
            Elements ducatValues = doc.select("td:nth-of-type(3) span");

            for (int i = 0; i < names.size(); i++) {

                Item newItem = new Item();
                newItem.setName(names.get(i).attributes().get("data-sort-value"));
                newItem.setDucatValue(Integer.parseInt(((TextNode)ducatValues.get(i).childNode(0)).text()));
                items.add(newItem);
            }

        }
        catch (java.io.IOException ex) {
        }

       for (Item i : items) {
           sb.append(i.getName());
           sb.append(": ");

           if(i.getPlatValue() != null) {
               sb.append(i.getPlatValue().toString() + "p");
           }

           if(i.getDucatValue() != null) {
               sb.append(i.getDucatValue().toString() + "d");
           }


           sb.append("<br/>");
       }

        return sb.toString();
    }
}
