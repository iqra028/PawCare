package com.example.project1.BLL.Location;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.json.JSONObject;

public class LocationFetcher {

    private static String generateRandomIp() {
        List<String> ipList = Arrays.asList(
                "63.116.61.253"/* ,
                "51.140.0.23",
                "103.205.179.249",
                "103.23.153.172",
                "88.190.221.100"*/
        );
        Random random = new Random();
        return ipList.get(random.nextInt(ipList.size()));
    }

    private static String fetchLocationForIp(String ip) {
        try {
            URL url = new URL("http://ip-api.com/json/" + ip);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getValidLocation() {
        System.out.println("getValidLocation");
        String randomIp = generateRandomIp();
        System.out.println("getValidLocation1");
        System.out.println("Trying IP: " + randomIp);
        String locationJson = fetchLocationForIp(randomIp);
        System.out.println("getValidLocation1");
        if (locationJson != null) {
            JSONObject obj = new JSONObject(locationJson);
            if (obj.has("lat") && obj.has("lon")) {
                System.out.println("Valid location found for IP: " + randomIp);
                return locationJson;
            } else {
                System.out.println("Invalid location data for IP: " + randomIp);
            }
        } else {
            System.out.println("Failed to fetch data for IP: " + randomIp);
        }
        return null;
    }
}
