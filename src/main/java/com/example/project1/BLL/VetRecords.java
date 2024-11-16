package com.example.project1.BLL;

import java.sql.*;
import java.util.ArrayList;

public class VetRecords extends PersistanceHandler{

    public boolean storeRecord(String name, String username, String email, String password, String location, String phoneNumber)
    {
        String sql = "INSERT INTO vets (vetname, username, password, phonenumber, location, email) VALUES (?, ?, ?, ?, ?, ?)";

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
    public ArrayList<Vets> getAllVets() {
        ArrayList<Vets> vets = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM vets");
            while (rs.next()) {
                vets.add(new Vets(
                        rs.getString("vetid"),
                        rs.getString("username"),
                        rs.getString("vetname"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("location"),
                        rs.getString("phonenumber")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vets;
    }
    public ArrayList<RescueCenter> getAllRescueCenters() {
        ArrayList<RescueCenter> rescueCenters = new ArrayList<>();
        return rescueCenters;
    }
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        return users;
    }
}
