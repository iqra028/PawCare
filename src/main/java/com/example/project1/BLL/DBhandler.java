package com.example.project1.BLL;

import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import javax.imageio.ImageIO;

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
    private byte[] imageToByteArray(Image image) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            // Using ImageIO to write the Image to ByteArrayOutputStream
            // JavaFX Image needs to be saved in a format (e.g., PNG or JPEG)
            ImageIO.write(javafx.embed.swing.SwingFXUtils.fromFXImage(image, null), "PNG", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            System.err.println("Error converting image to byte array: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public boolean storeAlertRecord(Alert alert) {
        System.out.println("Entering storeAlertRecord method");

        String sql = "INSERT INTO alert(type, message, breed, image, location, date_created, userid, rescuecenterid) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, alert.getType());
            stmt.setString(2, alert.getMessage());
            stmt.setString(3, alert.getBreed());
            if (alert.getImage() != null) {
                byte[] imageBytes = imageToByteArray(alert.getImage());
                stmt.setBytes(4, imageBytes);
            } else {
                stmt.setBytes(4, null);
            }
            if (alert.getLocation() != null) {
                String locationString = alert.getLocation()[0] + "," + alert.getLocation()[1];
                stmt.setString(5, locationString);
            } else {
                stmt.setString(5, null);
            }

            stmt.setDate(6, java.sql.Date.valueOf(LocalDate.now()));

            try {
                stmt.setObject(7, UUID.fromString(alert.getUserid()));
                stmt.setObject(8, UUID.fromString(alert.getRescuecenterid()));
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid UUID format for user or rescuecenterid: " + e.getMessage());
                return false;
            }
            int rowsInserted = stmt.executeUpdate();
            System.out.println("Number of rows inserted: " + rowsInserted);
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Error storing alert record: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public List<Alert> getAlertsByRescueCenter() {

        List<Alert> alerts = new ArrayList<>();
        String sql = "SELECT alertid, type, message, breed, image, location, date_created, userid, rescuecenterid " +
                "FROM alert WHERE rescuecenterid = CAST(? AS UUID)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String rescueCenterId = Session.getInstance().getLoggedInRescueCenter().getRescueCenterID();
            UUID uuid = UUID.fromString(rescueCenterId);
            stmt.setObject(1, uuid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Alert alert = new Alert();
                alert.setType(rs.getString("type"));
                alert.setMessage(rs.getString("message"));
                alert.setBreed(rs.getString("breed"));

                byte[] imageBytes = rs.getBytes("image");
                if (imageBytes != null) {
                    InputStream imageStream = new ByteArrayInputStream(imageBytes);
                    Image image = new Image(imageStream);
                    alert.setImage(image);  // Set image (JavaFX Image)
                }
                alert.setLocation(parseLocation(rs.getString("location")));  // Assuming parseLocation is implemented
                alert.setDateCreated(rs.getDate("date_created").toLocalDate());
                alert.setUserid(rs.getString("userid"));
                alert.setRescuecenterid(rs.getString("rescuecenterid"));

                alerts.add(alert);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving alert records: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid UUID format for rescuecenterid: " + e.getMessage());
            e.printStackTrace();
        }

        return alerts;
    }

    private double[] parseLocation(String locationString) {
        if (locationString == null || locationString.isEmpty()) {
            return null;
        }
        String[] parts = locationString.split(",");
        double[] location = new double[parts.length];
        for (int i = 0; i < parts.length; i++) {
            location[i] = Double.parseDouble(parts[i]);
        }
        return location;
    }

    public List<Donation> displayDonationRecords() {
        String sql = "SELECT * FROM donations WHERE rescuecenterid = ?";
        List<Donation> donations = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, UUID.fromString(Session.getInstance().getLoggedInRescueCenter().getRescueCenterID())); // Convert to UUID if necessary

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Donation donation = new Donation();
                    donation.setAmount(rs.getDouble("amount"));
                    donation.setDateCreated(rs.getDate("dateCreated").toLocalDate());
                    donation.setUserid(rs.getString("userid"));
                    donation.setRescuecenterid(rs.getString("rescuecenterid"));

                    donations.add(donation);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving donation records: " + e.getMessage());
            e.printStackTrace();
        }

        return donations;
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

    public String addAnimal(Animal animal, String rescueCenterId) {
        String insertHealthQuery = """
        INSERT INTO health_description (id, temperature, heart_rate, respiratory_rate,
                                        capillary_refill_time, blood_oxygen_level, 
                                        blood_glucose_level, weight)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
    """;

        String insertAnimalQuery = """
        INSERT INTO animals (animal_id, name, type, breed, color, health_id,
                             health_status, visited_vet, with_vet, 
                             up_for_adoption, adopted, rescue_center_id, image)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        RETURNING animal_id
    """;

        try (Connection conn = connect()) {
            conn.setAutoCommit(false);

            // Generate UUID for healthId
            UUID healthId = UUID.randomUUID();

            // Insert into health_description
            try (PreparedStatement healthStmt = conn.prepareStatement(insertHealthQuery)) {
                healthStmt.setObject(1, healthId);  // Pass UUID to health_id
                healthStmt.setDouble(2, animal.getHealth().getTemperature());
                healthStmt.setInt(3, animal.getHealth().getHeartRate());
                healthStmt.setInt(4, animal.getHealth().getRespiratoryRate());
                healthStmt.setInt(5, animal.getHealth().getCapillaryRefillTime());
                healthStmt.setInt(6, animal.getHealth().getBloodOxygenLevel());
                healthStmt.setInt(7, animal.getHealth().getBloodGlucoseLevel());
                healthStmt.setDouble(8, animal.getHealth().getWeight());
                healthStmt.executeUpdate();
            }

            // Generate UUID for animalId
            UUID animalId = UUID.randomUUID();

            // Convert rescueCenterId to UUID
            UUID rescueCenterUUID = UUID.fromString(rescueCenterId);  // Convert String to UUID

            // Insert into animals table
            try (PreparedStatement animalStmt = conn.prepareStatement(insertAnimalQuery)) {
                animalStmt.setObject(1, animalId);  // Pass UUID to animal_id
                animalStmt.setString(2, animal.getName());
                animalStmt.setString(3, animal.getType());
                animalStmt.setString(4, animal.getBreed());
                animalStmt.setString(5, animal.getColor());
                animalStmt.setObject(6, healthId);  // Pass UUID to health_id
                animalStmt.setBoolean(7, animal.isHealthStatus());
                animalStmt.setBoolean(8, animal.isVisitedVet());
                animalStmt.setBoolean(9, animal.isWithVet());
                animalStmt.setBoolean(10, animal.isUpForAdoption());
                animalStmt.setBoolean(11, animal.isAdopted());
                animalStmt.setObject(12, rescueCenterUUID);  // Pass UUID to rescue_center_id
                animalStmt.setBytes(13, imageToByteArray(animal.getImage())); // Convert image to byte[]

                try (ResultSet rs = animalStmt.executeQuery()) {
                    if (rs.next()) {
                        animalId = UUID.fromString(rs.getString("animal_id"));
                    }
                }
            }

            conn.commit();
            return animalId.toString();  // Return the UUID as a String
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }







}

