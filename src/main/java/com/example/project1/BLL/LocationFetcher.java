package com.example.project1.BLL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class LocationFetcher {

    // Generates a random valid public IP address
    private static String generateRandomIp() {
        int first = (int) (Math.random() * (223 - 1 + 1) + 1); // Valid range: 1 to 223
        int second = (int) (Math.random() * 256);
        int third = (int) (Math.random() * 256);
        int fourth = (int) (Math.random() * 256);
        return first + "." + second + "." + third + "." + fourth;
    }

    // Fetches location data for a given IP
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
        while (true) {
            String randomIp = generateRandomIp();
            System.out.println("Trying IP: " + randomIp);
            String locationJson = fetchLocationForIp(randomIp);

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
        }
    }
}
