package com.example.project1.BLL;

import java.sql.*;
import java.util.*;

public class DBhandler extends PersistanceHandler{


    private String jdbcURL;
    private String username;
    private String password;


    public DBhandler() {
        this.jdbcURL = "jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:6543/postgres";
        this.username = "postgres.crlvllcnuffkzpyrlmsu";
        this.password = "onyourface123";

    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(jdbcURL, username, password);
    }

    public boolean addUser(User user) {
        String sql = "INSERT INTO \"user\" (name,username,  email, password, gender) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getGender());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Method to add a RescueCenter
    public boolean addRescueCenter(RescueCenter rescueCenter) {
        String sql = "INSERT INTO rescuecenter (centername,username,password, phonenumber, location ,email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rescueCenter.getName());
            stmt.setString(2, rescueCenter.getUserName());
            stmt.setString(3, rescueCenter.getPassword());
            stmt.setString(4, rescueCenter.getPhoneNumber());
            stmt.setString(5, rescueCenter.getLocation());
            stmt.setString(6, rescueCenter.getEmail());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to add a Vet
    public boolean addVet(Vets vet) {
        String sql = "INSERT INTO vets (vetname, username, password, phonenumber, location, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vet.getName());
            stmt.setString(2, vet.getUserName());
            stmt.setString(3, vet.getPassword());
            stmt.setString(4, vet.getPhoneNumber());
            stmt.setString(5, vet.getLocation());
            stmt.setString(6, vet.getEmail());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //for retrieving data from database:

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM \"user\"");
            while (rs.next()) {
                users.add(new User(rs.getString("username"), rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getString("gender")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Retrieve all vets from the database with ID as String
    public ArrayList<Vets> getAllVets() {
        ArrayList<Vets> vets = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM vets");
            while (rs.next()) {
                vets.add(new Vets(
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

    // Retrieve all rescue centers from the database with ID as String
    public ArrayList<RescueCenter> getAllRescueCenters() {
        ArrayList<RescueCenter> rescueCenters = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM rescuecenter");
            while (rs.next()) {
                rescueCenters.add(new RescueCenter(
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
}

