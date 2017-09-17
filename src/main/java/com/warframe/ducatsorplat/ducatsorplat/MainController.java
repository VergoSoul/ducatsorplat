package com.warframe.ducatsorplat.ducatsorplat;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.*;
import java.util.ArrayList;

@Controller
public class MainController
{
    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    public ArrayList<Item> GetData() {

        String bucketName     = "ducats-or-plat-data";
        String keyName        = "itemvalues";
        AmazonS3 s3client =  AmazonS3ClientBuilder.standard().withRegion(Regions.US_WEST_2).withCredentials(new EnvironmentVariableCredentialsProvider()).build();
        S3Object object;
        ArrayList<Item> items = null;

        try {
            System.out.println("Uploading a new object to S3 from a file\n");
            object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
            InputStream objectData = object.getObjectContent();
            Reader reader = new InputStreamReader(objectData, "UTF-8");
            items = new Gson().fromJson(reader, new TypeToken<ArrayList<Item>>() {}.getType());
            items.toString();

        } catch (Exception ase) {
            System.out.println("Error Getting Data");
            System.out.println("Error Message:    " + ase.getMessage());
        }

        return items;
    }
}
