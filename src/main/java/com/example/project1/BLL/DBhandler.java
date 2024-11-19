package com.example.project1.BLL;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public  class DBhandler {


    private String jdbcURL;
    private String username;
    private String password;


    public DBhandler() {
        this.jdbcURL = "jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:6543/postgres";
        this.username = "postgres.crlvllcnuffkzpyrlmsu";
        this.password = "onyourface123";

    }

    protected Connection connect() throws SQLException {
        return DriverManager.getConnection(jdbcURL, username, password);
    }

    public String getIDByUsername(String tableName, String idColumn, String usernameColumn, String username) {
        String sql = "SELECT " + idColumn + " FROM " + tableName + " WHERE " + usernameColumn + " = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(idColumn); // Retrieve the ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if not found or on error
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

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM \"user\"");
            while (rs.next()) {
                users.add(new User(rs.getString("userid"),rs.getString("username"), rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getString("location"),rs.getString("phonenumber")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    public boolean storeUserRecord(String name, String username, String email, String password, String location, String phoneNumber)
    {
        String sql = "INSERT INTO \"user\" (name, username, email, password, location, phonenumber) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, username);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.setString(5, location);
            statement.setString(6, phoneNumber);

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

    public boolean storeVetRecord(String name, String username, String email, String password, String location, String phoneNumber)
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
    public boolean storeDonationRecord(Donation donation) {
        // Adjust the table and column names to be case-sensitive (if required)
        String sql = "INSERT INTO donations(amount, dateCreated, userid, rescuecenterid) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect(); // Ensure connect() provides a valid connection
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters
            stmt.setDouble(1, donation.getAmount());

            // If dateCreated is a LocalDate or LocalDateTime, set the correct timestamp
            if (donation.getDateCreated() instanceof LocalDate) {
                stmt.setDate(2, java.sql.Date.valueOf(donation.getDateCreated())); // If it's LocalDate
            }

            // Ensure that the userid and rescuecenterid are UUIDs
            stmt.setObject(3, UUID.fromString(donation.getUserid())); // Convert to UUID if it's a string
            stmt.setObject(4, UUID.fromString(donation.getRescuecenterid())); // Convert to UUID if it's a string

            // Execute the statement
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            // Handle SQL exceptions and log error details
            System.err.println("Error storing donation record: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }




    public  boolean storeCenterRecord(String name, String username, String password, String phoneNumber, String location, String email)
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

}

