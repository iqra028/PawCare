package com.example.project1.BLL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeoLocation {

    private double latitude;
    private double longitude;
    private double radius;
    public GeoLocation(double latitude, double longitude, double radius) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }
    public GeoLocation() {
        this.latitude = 40.712776;
        this.longitude = -74.005974;
        this.radius = 9000;
    }
    public double[] fetchDynamicLocation() {

        try {
            String json = LocationFetcher.getValidLocation();
            double[] location= LocationParser.parseLocation(json);
            longitude = location[0];
            latitude = location[1];
            radius = 9000;
            return location;
        } catch (Exception e) {
            e.printStackTrace();
            return new double[]{40.712776, -74.005974};
        }
    }

    public void fetchDataFromOverpassAPI(double latitude, double longitude, double radius) {

        String overpassUrl = "https://overpass-api.de/api/interpreter?data=[out:json];" +
                "node(around:" + radius + "," + latitude + "," + longitude + ")" +
                "[amenity~\"animal_shelter|veterinary|pet_adoption_center|pet\"];out;";

        try {
            URL url = new URL(overpassUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                parseShelterData(jsonResponse);
            } else {
                System.out.println("Error fetching data: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Parse the fetched shelter data from Overpass API
    private void parseShelterData(JSONObject jsonResponse) {
        try {
            JSONArray elements = jsonResponse.getJSONArray("elements");

            if (elements.length() > 0) {
                // Store or process the shelter information here without displaying it
                for (int i = 0; i < elements.length(); i++) {
                    JSONObject shelter = elements.getJSONObject(i);

                    double shelterLat = shelter.getDouble("lat");
                    double shelterLon = shelter.getDouble("lon");
                    JSONObject tags = shelter.optJSONObject("tags");

                    String name = tags != null && tags.has("name") ? tags.getString("name") : "Unnamed Shelter";
                    String phone = tags != null && tags.has("phone") ? tags.getString("phone") : "Phone not available";
                    String website = tags != null && tags.has("website") ? tags.getString("website") : "Website not available";

                    // Store or process the shelter information as needed (e.g., add to a list or display in a GUI)
                }
            } else {
                System.out.println("No shelters found within the specified radius.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error parsing shelter information.");
        }
    }

    // Generate map HTML with dynamic latitude and longitude (returns HTML to be used elsewhere)
    public String generateMapHTML(double latitude, double longitude) {
        return ""
                + "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "    <link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.9.3/dist/leaflet.css\" />"
                + "    <script src=\"https://unpkg.com/leaflet@1.9.3/dist/leaflet.js\"></script>"
                + "</head>"
                + "<body>"
                + "    <div id=\"map\" style=\"width: 100%; height: 400px;\"></div>"
                + "    <script>"
                + "        var latitude = " + latitude + ";"
                + "        var longitude = " + longitude + ";"
                + "        var map = L.map('map').setView([latitude, longitude], 13);"
                + "        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {"
                + "            attribution: '© OpenStreetMap contributors' "
                + "        }).addTo(map);"
                + "        L.marker([latitude, longitude]).addTo(map)"
                + "            .bindPopup('Your Location')"
                + "            .openPopup();"
                + "    </script>"
                + "</body>"
                + "</html>";
    }
}
