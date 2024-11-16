package com.example.project1.BLL;

import java.sql.*;
import java.util.ArrayList;

public class RescueCenterRecords extends PersistanceHandler{

    public  boolean storeRecord(String name, String username, String password, String phoneNumber, String location, String email)
    {
        String sql = "INSERT INTO rescuecenter (centername, username, password, phonenumber, location, email) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setString(4, phoneNumber);
            stmt.setString(5, location);
            stmt.setString(6, email);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<RescueCenter> getAllRescueCenters() {
        ArrayList<RescueCenter> rescueCenters = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM rescuecenter");
            while (rs.next()) {
                rescueCenters.add(new RescueCenter(
                        rs.getString("rescuecenterid"),
                        rs.getString("username"),
                        rs.getString("centername"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("location"),
                        rs.getString("phonenumber")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rescueCenters;
    }
    public ArrayList<Vets> getAllVets() {
        ArrayList<Vets> vets = new ArrayList<>();
        return vets;
    }
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        return users;
    }

}
