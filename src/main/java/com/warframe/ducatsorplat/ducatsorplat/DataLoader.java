package com.warframe.ducatsorplat.ducatsorplat;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.StringInputStream;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.OptionalInt;

public class DataLoader implements Runnable {

    public List<Item> getDucatValues() throws Exception
    {
        ArrayList<Item> items = new ArrayList<>();
        HashMap<String, String> nameCheck = new HashMap<String, String>() {
            {
                put("Boltor Prime Receiver", "Boltor Prime Reciever");
                put("Kavasa Prime Band", "Kavasa Prime Collar Band");
                put("Kavasa Prime Buckle", "Kavasa Prime Collar Buckle");
                put("Lex Prime Receiver", "Lex Prime Reciever");
                put("Paris Prime Lower Limb", "Paris Prime  Lower Limb");
                put("Paris Prime Grip", "Paris Prime  Grip");
            }
        };

        Document doc = Jsoup.connect("http://warframe.wikia.com/wiki/Ducats/Prices").get();
        Elements names = doc.select("#mw-customcollapsible-ducatsprices td:nth-of-type(1)");
        Elements ducatValues = doc.select("td:nth-of-type(3) span");

        for (int i = 0; i < names.size(); i++) {
            Item newItem = new Item();
            newItem.setName(((TextNode)names.get(i).childNode(2).childNode(0)).text());

            if (nameCheck.containsKey(newItem.getName())) {
                newItem.setName(nameCheck.get(newItem.getName()));
            }

            newItem.setDucatValue(Integer.parseInt(((TextNode)ducatValues.get(i).childNode(0)).text()));
            items.add(newItem);
        }

        return items;
    }

    public MarketValue getPlatValues() throws Exception {
        MarketValue data = new MarketValue();
        List<Item> items = getDucatValues();

        for (Item i : items) {
            InputStream input;

            if (i.getName().contains("Helios")) {
                input = new URL("http://warframe.market/api/get_orders/Set/" + i.getName().replace(" ", "%20")).openStream();
            }
            else {
                input = new URL("http://warframe.market/api/get_orders/Blueprint/" + i.getName().replace(" ", "%20")).openStream();
            }

            Reader reader = new InputStreamReader(input, "UTF-8");

            try {
                data = new Gson().fromJson(reader, MarketValue.class);
            }
            catch (Exception ex) {
                System.out.println(i.getName());
            }

            OptionalInt buyValue = data.getResponse().getBuy().stream().filter(b -> b.online_status).mapToInt(b -> b.getPrice()).max();
            OptionalInt sellValue = data.getResponse().getSell().stream().filter(b -> b.online_status).mapToInt(b -> b.getPrice()).min();

            if (buyValue.isPresent() && sellValue.isPresent()) {
                i.setPlatValue(Math.round((buyValue.getAsInt() + sellValue.getAsInt()) / 2.0f));
            }
        }

        SaveData(items);

        return data;
    }

    public void SaveData(List<Item> items) throws Exception {

        String bucketName     = "ducats-or-plat-data";
        String keyName        = "itemvalues";

        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAJSL4EWRWIJD6ACVA", "4xIcyH160eYdE2NsDpG+O1/K3jo28SBUilPO8QIl");

        AmazonS3 s3client =  AmazonS3ClientBuilder.standard().withRegion(Regions.US_WEST_2).withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
        ObjectMetadata meta = new ObjectMetadata();
        InputStream is;
        String json = new Gson().toJson( items);

        is = new StringInputStream(json);

        Long contentLength = Long.valueOf(json.length());

        meta.setContentLength(contentLength);

        try {
            System.out.println("Uploading a new object to S3 from a file\n");
            s3client.putObject(new PutObjectRequest(bucketName, keyName, is, meta));

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
                    "means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                getPlatValues();
                Thread.sleep(900000);
            }
            catch (Exception ex) {

            }
        }
    }
}
