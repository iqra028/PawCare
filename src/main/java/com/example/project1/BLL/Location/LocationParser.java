package com.example.project1.BLL.Location;

import org.json.JSONObject;

public class LocationParser {
    public static double[] parseLocation(String json) {
        JSONObject obj = new JSONObject(json);
        double latitude = obj.getDouble("lat");
        double longitude = obj.getDouble("lon");
        return new double[]{latitude, longitude};
    }
}
