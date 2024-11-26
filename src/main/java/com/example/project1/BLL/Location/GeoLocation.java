package com.example.project1.BLL.Location;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class GeoLocation {

    private double latitude;
    private double longitude;
    private double radius;
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getRadius() {
        return radius;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }
    public GeoLocation(double latitude, double longitude, double radius) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }
    public GeoLocation() {
        this.latitude = 40.712776;
        this.longitude = -74.005974;
        this.radius = 5000;
    }
    public double[] fetchDynamicLocation() {
            System.out.println("fetchDynamicLocation");
            String json = LocationFetcher.getValidLocation();
            double[] location= LocationParser.parseLocation(json);
            longitude = location[0];
            latitude = location[1];
            radius = 9000;
            return location;
    }
    public List<String> parseAndDisplayShelterInfo(JSONObject jsonResponse) {
        List<String> shelterInfoList = new ArrayList<>();

        try {
            JSONArray elements = jsonResponse.getJSONArray("elements");

            System.out.println("Nearby Animal Shelters:");
            if (!elements.isEmpty()) {
                for (int i = 0; i < elements.length(); i++) {
                    JSONObject shelter = elements.getJSONObject(i);

                    double shelterLat = shelter.getDouble("lat");
                    double shelterLon = shelter.getDouble("lon");
                    JSONObject tags = shelter.optJSONObject("tags");

                    String name = tags != null && tags.has("name") ? tags.getString("name") : "Unnamed Shelter";
                    String phone = tags != null && tags.has("phone") ? tags.getString("phone") : "Phone not available";
                    String website = tags != null && tags.has("website") ? tags.getString("website") : "Website not available";

                    // Create a formatted string for each shelter
                    String shelterInfo = String.format(
                            "Name: %s\nLocation: Latitude %.6f, Longitude %.6f\nPhone: %s\nWebsite: %s",
                            name, shelterLat, shelterLon, phone, website);

                    shelterInfoList.add(shelterInfo);

                    System.out.println("-----------------------------------");
                    System.out.println("Name: " + name);
                    System.out.println("Location: Latitude " + shelterLat + ", Longitude " + shelterLon);
                    System.out.println("Phone: " + phone);
                    System.out.println("Website: " + website);
                }
            } else {
                String noSheltersMessage = "No shelters found within the specified radius.";
                System.out.println(noSheltersMessage);
                shelterInfoList.add(noSheltersMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Error parsing shelter information.";
            System.out.println(errorMessage);
            shelterInfoList.add(errorMessage);
        }

        return shelterInfoList;
    }

    public String generateMapHTML(double latitude, double longitude) {
        System.out.println("i came to generate map");
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
                + "            attribution: '© OpenStreetMap contributors'"
                + "        }).addTo(map);"
                + "        L.marker([latitude, longitude]).addTo(map)"
                + "            .bindPopup('Your Location')"
                + "            .openPopup();"
                + "    </script>"
                + "</body>"
                + "</html>";
    }
}
