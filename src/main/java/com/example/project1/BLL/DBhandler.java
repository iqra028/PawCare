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
    public boolean storeVolunteerRecord(String userId, String cnic, String vehicleType, Image vehicleImage, String vehicleModel, Boolean availability) {
        String insertVolunteerSql = "INSERT INTO volunteer (userid, cnic, vehicle_type, vehicle_image, vehicle_model, availability) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        String updateUserSql = "UPDATE \"user\" SET volunteer = ? WHERE userid = ?"; // Escaped user table name

        try (Connection conn = connect();  // Use your database connection method
             PreparedStatement insertStatement = conn.prepareStatement(insertVolunteerSql);
             PreparedStatement updateStatement = conn.prepareStatement(updateUserSql)) {

            // Insert into volunteer table
            insertStatement.setObject(1, UUID.fromString(userId));  // Set userId as UUID
            insertStatement.setString(2, cnic);    // Set CNIC
            insertStatement.setString(3, vehicleType); // Set vehicle type
            insertStatement.setBytes(4, imageToByteArray(vehicleImage)); // Convert image to byte array
            insertStatement.setString(5, vehicleModel); // Set vehicle model
            insertStatement.setBoolean(6, availability); // Set availability

            int rowsInserted = insertStatement.executeUpdate();

            // Update the user's table to set 'volunteer' column to true
            if (rowsInserted > 0) {
                updateStatement.setBoolean(1, true);  // Set volunteer to true
                updateStatement.setObject(2, UUID.fromString(userId));  // Match userId
                int rowsUpdated = updateStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("User's volunteer status updated successfully!");
                } else {
                    System.out.println("Failed to update user's volunteer status.");
                }
            }

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Image byteArrayToImage(byte[] byteArray) {
        if (byteArray == null) {
            return null;  // Return null if no image is found
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
        return new Image(byteArrayInputStream);
    }
    public ArrayList<Volunteer> getAllVolunteers() {
        String selectVolunteerSql = "SELECT * FROM volunteer";
        ArrayList<Volunteer> volunteers = new ArrayList<>();

        try (Connection conn = connect();  // Use your database connection method
             PreparedStatement statement = conn.prepareStatement(selectVolunteerSql);
             ResultSet resultSet = statement.executeQuery()) {

            // Loop through the results and create Volunteer objects
            while (resultSet.next()) {
                String userId = resultSet.getString("userid");
                String cnic = resultSet.getString("cnic");
                String vehicleType = resultSet.getString("vehicle_type");
                byte[] vehicleImageBytes = resultSet.getBytes("vehicle_image");
                String vehicleModel = resultSet.getString("vehicle_model");
                boolean availability = resultSet.getBoolean("availability");

                // Convert vehicle image bytes to Image object
                Image vehicleImage = byteArrayToImage(vehicleImageBytes);

                Volunteer volunteer = new Volunteer(userId, cnic, vehicleType, vehicleImage, vehicleModel, availability);
                volunteers.add(volunteer);
                System.out.println("successful");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return volunteers;
    }
    public boolean isUserVolunteer() {
        String sql = "SELECT volunteer FROM \"user\" WHERE userid = ?";

        try (Connection conn = connect(); // Use your database connection method
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Set the userId parameter
            statement.setObject(1, UUID.fromString(Session.getInstance().getLoggedInUser().getUserID()));

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Get the 'volunteer' column value
                return resultSet.getBoolean("volunteer");
            }

            // User not found
            System.out.println("User not found in the database.");
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateVolunteerAvailability(String userId, boolean availability) {
        String sql = "UPDATE volunteer SET availability = ? WHERE userid = ?";

        try (Connection conn = connect(); // Use your database connection method
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Set parameters
            statement.setBoolean(1, availability); // Set availability (true/false)
            statement.setObject(2, UUID.fromString(userId)); // Set userId as UUID

            // Execute the update
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Volunteer availability updated successfully!");
                return true;
            } else {
                System.out.println("Volunteer not found or update failed.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
    public boolean setCompletedToTrue(String alertId) {
       System.out.println(alertId);
        String sql = "UPDATE alert SET completed = true WHERE alertid = ?AND \" alertType\" = 'User'";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setObject(1, UUID.fromString(alertId));  // Convert alertId to UUID if it's a string

            // Execute the statement
            int rowsUpdated = stmt.executeUpdate();

            // Return true if at least one row was updated
            return rowsUpdated > 0;

        } catch (SQLException e) {
            // Handle SQL exceptions and log error details
            System.err.println("Error updating alert record: " + e.getMessage());
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
    public List<Alert> getRescueCenterAlerts(String userid) {
        List<Alert> alerts = new ArrayList<>();
        String sql = "SELECT alertid, type, message, breed, image, location, date_created, userid, rescuecenterid " +
                "FROM alert WHERE userid = CAST(? AS UUID) AND completed = false AND \"alertType\" = 'RescueCenter'";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Getting the UUID from the session, ensure this is a valid UUID string
            UUID uuid = UUID.fromString(Session.getInstance().getLoggedInUser().getUserID());
            stmt.setObject(1, uuid);

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Alert alert = new Alert();
                    alert.setType(rs.getString("type"));
                    alert.setMessage(rs.getString("message"));
                    alert.setBreed(rs.getString("breed"));

                    // Handle image bytes from DB
                    byte[] imageBytes = rs.getBytes("image");
                    if (imageBytes != null) {
                        InputStream imageStream = new ByteArrayInputStream(imageBytes);
                        Image image = new Image(imageStream);
                        alert.setImage(image);
                    }

                    // Parsing location and other fields
                    alert.setLocation(parseLocation(rs.getString("location")));
                    alert.setDateCreated(rs.getDate("date_created").toLocalDate());
                    alert.setUserid(rs.getString("userid"));
                    alert.setRescuecenterid(rs.getString("rescuecenterid"));

                    alerts.add(alert);
                }
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





    public String storeAlertRecord(Alert alert, String type) {
        System.out.println("Entering storeAlertRecord method");

        // Updated SQL query to insert without specifying alertid (auto-generated in DB)
        String sql = "INSERT INTO alert(type, message, breed, image, location, date_created, userid, rescuecenterid, \"alertType\", completed) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set parameters for the SQL query
            stmt.setString(1, alert.getType()); // Alert type
            stmt.setString(2, alert.getMessage()); // Alert message
            stmt.setString(3, alert.getBreed()); // Animal breed

            // If image exists, convert to bytes and insert, otherwise set to null
            if (alert.getImage() != null) {
                byte[] imageBytes = imageToByteArray(alert.getImage());
                stmt.setBytes(4, imageBytes); // Image
            } else {
                stmt.setBytes(4, null); // No image
            }

            // Handle location - assuming it's an array with two values (latitude, longitude)
            if (alert.getLocation() != null) {
                String locationString = alert.getLocation()[0] + "," + alert.getLocation()[1]; // Concatenate location values
                stmt.setString(5, locationString); // Location
            } else {
                stmt.setString(5, null); // No location
            }

            // Set the current date for the date_created column
            stmt.setDate(6, java.sql.Date.valueOf(LocalDate.now())); // Date created

            // Check if userid and rescuecenterid are not null and are valid UUIDs
            if (alert.getUserid() != null && !alert.getUserid().isEmpty()) {
                try {
                    stmt.setObject(7, UUID.fromString(alert.getUserid())); // User ID
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid UUID format for userid: " + alert.getUserid());
                    return null; // Invalid UUID format
                }
            } else {
                System.err.println("User ID is null or empty.");
                return null; // User ID is null or empty
            }

            if (alert.getRescuecenterid() != null && !alert.getRescuecenterid().isEmpty()) {
                try {
                    stmt.setObject(8, UUID.fromString(alert.getRescuecenterid())); // Rescue Center ID
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid UUID format for rescuecenterid: " + alert.getRescuecenterid());
                    return null; // Invalid UUID format
                }
            } else {
                System.err.println("Rescue Center ID is null or empty.");
                return null; // Rescue Center ID is null or empty
            }

            // Set the type (from the method parameter) for alertType
            stmt.setString(9, type); // Alert type (from the parameter)

            // Set false for the 'completed' column
            stmt.setBoolean(10, false); // Completed status (false by default)

            // Execute the query and check how many rows were inserted
            int rowsInserted = stmt.executeUpdate();
            System.out.println("Number of rows inserted: " + rowsInserted);

            // Retrieve the generated alertid
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // Get the generated alert ID (as a UUID)
                        UUID alertId = (UUID) generatedKeys.getObject(1); // First column (alertid)

                        // Convert UUID to String and return it
                        System.out.println("Generated alert ID: " + alertId.toString());
                        return alertId.toString(); // Return the UUID as a string
                    } else {
                        System.err.println("Failed to retrieve generated alert ID.");
                        return null;
                    }
                }
            } else {
                System.err.println("No rows inserted.");
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Error storing alert record: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }





    public List<Alert> getAlertsByRescueCenter(String id ) {

        List<Alert> alerts = new ArrayList<>();
        String sql = "SELECT alertid, type, message, breed, image, location, date_created, userid, rescuecenterid " +
                "FROM alert WHERE rescuecenterid = CAST(? AS UUID) AND completed = false AND \"alertType\" = 'User'";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String rescueCenterId = id;
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
    public List<Animal> getAnimalsByRescueCenter(String rescueCenterID) {
        List<Animal> animals = new ArrayList<>();
        String query = "SELECT a.*, h.* FROM animals a " +
                "JOIN health_description h ON a.health_id = h.id " +
                "WHERE a.rescue_center_id = CAST(? AS UUID)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, rescueCenterID);  // Setting the rescueCenterID as String (will be cast to UUID in SQL)

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UUID animalID = UUID.fromString(rs.getString("animal_id"));
                    String type = rs.getString("type");
                    String breed = rs.getString("breed");
                    String color = rs.getString("color");
                    String healthID = rs.getString("health_id");
                    boolean healthStatus = rs.getBoolean("health_status");
                    boolean visitedVet = rs.getBoolean("visited_vet");
                    boolean withVet = rs.getBoolean("with_vet");
                    boolean upForAdoption = rs.getBoolean("up_for_adoption");
                    boolean adopted = rs.getBoolean("adopted");
                    String name = rs.getString("name");

                    byte[] imageBytes = rs.getBytes("image");
                    Image image = imageBytes != null ? new Image(new ByteArrayInputStream(imageBytes)) : null;


                    double temperature = rs.getDouble("temperature");
                    int heartRate = rs.getInt("heart_rate");
                    int respiratoryRate = rs.getInt("respiratory_rate");
                    int capillaryRefillTime = rs.getInt("capillary_refill_time");
                    int bloodOxygenLevel = rs.getInt("blood_oxygen_level");
                    int bloodGlucoseLevel = rs.getInt("blood_glucose_level");
                    double weight = rs.getDouble("weight");

                    HealthDescription healthDescription = new HealthDescription(temperature, heartRate, respiratoryRate,
                            capillaryRefillTime, bloodOxygenLevel, bloodGlucoseLevel, weight);

                    Animal animal = new Animal(animalID.toString(), name, type, breed, color, healthDescription,
                            healthStatus, visitedVet, withVet, upForAdoption, adopted, image);

                    animals.add(animal);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return animals;
    }

    public UUID getHealthIdFromAnimal(String animalIdString) {
        UUID animalId = UUID.fromString(animalIdString);

        String query = "SELECT health_id FROM animals WHERE animal_id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, animalId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String healthIdString = resultSet.getString("health_id");
                return UUID.fromString(healthIdString);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateAnimalInDB(Animal updatedAnimal) {
        String animalIdString = updatedAnimal.getAnimalID();
        UUID healthId = getHealthIdFromAnimal(animalIdString);

        if (healthId == null) {
            System.out.println("No health record found for the given animal ID.");
            return false;
        }

        boolean healthUpdateSuccess = updateHealthDescriptionInDB(updatedAnimal.getHealth(), healthId);

        if (healthUpdateSuccess) {
            return updateAnimalDetailsInDB(updatedAnimal, healthId);
        }
        return false;
    }
    public boolean updateAnimalDetailsInDB(Animal updatedAnimal, UUID healthId) {
        String updateQuery = "UPDATE animals SET type = ?, breed = ?, color = ?, health_id = ?, " +
                "health_status = ?, visited_vet = ?, with_vet = ?, up_for_adoption = ?, adopted = ?, " +
                "name = ? WHERE animal_id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            // Set the parameters in the prepared statement
            preparedStatement.setString(1, updatedAnimal.getType());
            preparedStatement.setString(2, updatedAnimal.getBreed());
            preparedStatement.setString(3, updatedAnimal.getColor());
            preparedStatement.setObject(4, healthId); // health_id is a UUID
            preparedStatement.setBoolean(5, updatedAnimal.isHealthStatus());
            preparedStatement.setBoolean(6, updatedAnimal.isVisitedVet());
            preparedStatement.setBoolean(7, updatedAnimal.isWithVet());
            preparedStatement.setBoolean(8, updatedAnimal.isUpForAdoption());
            preparedStatement.setBoolean(9, updatedAnimal.isAdopted());
            preparedStatement.setString(10, updatedAnimal.getName());
            preparedStatement.setObject(11, UUID.fromString(updatedAnimal.getAnimalID())); // animal_id as UUID

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateHealthDescriptionInDB(HealthDescription updatedHealth, UUID healthId) {
        String updateQuery = "UPDATE health_description SET temperature = ?, heart_rate = ?, respiratory_rate = ?, " +
                "capillary_refill_time = ?, blood_oxygen_level = ?, blood_glucose_level = ?, weight = ? " +
                "WHERE id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setDouble(1, updatedHealth.getTemperature());
            preparedStatement.setInt(2, updatedHealth.getHeartRate());
            preparedStatement.setInt(3, updatedHealth.getRespiratoryRate());
            preparedStatement.setInt(4, updatedHealth.getCapillaryRefillTime());
            preparedStatement.setInt(5, updatedHealth.getBloodOxygenLevel());
            preparedStatement.setInt(6, updatedHealth.getBloodGlucoseLevel());
            preparedStatement.setDouble(7, updatedHealth.getWeight());
            preparedStatement.setObject(8, healthId);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteAnimal(String animalId) {
        String getHealthIdQuery = "SELECT health_id FROM Animals WHERE animal_id = ?";
        String deleteAnimalQuery = "DELETE FROM Animals WHERE animal_id = ?";
        String deleteHealthDescriptionQuery = "DELETE FROM health_description WHERE id = ?";

        try (Connection conn = connect();  // Establish the database connection
             PreparedStatement getHealthIdStmt = conn.prepareStatement(getHealthIdQuery);
             PreparedStatement deleteAnimalStmt = conn.prepareStatement(deleteAnimalQuery);
             PreparedStatement deleteHealthDescriptionStmt = conn.prepareStatement(deleteHealthDescriptionQuery)) {

            // Convert string animalId to UUID
            UUID animalUuid = UUID.fromString(animalId);

            getHealthIdStmt.setObject(1, animalUuid);
            ResultSet resultSet = getHealthIdStmt.executeQuery();
            UUID healthId = null;
            if (resultSet.next()) {
                healthId = (UUID) resultSet.getObject("health_id");
            }

            if (healthId == null) {
                System.err.println("No health_id found for the given animal_id: " + animalId);
                return false;
            }

            deleteAnimalStmt.setObject(1, animalUuid);
            int animalRowsAffected = deleteAnimalStmt.executeUpdate();
            if (animalRowsAffected == 0) {
                System.err.println("Failed to delete the animal with animal_id: " + animalId);
                return false;
            }

            deleteHealthDescriptionStmt.setObject(1, healthId);
            int healthRowsAffected = deleteHealthDescriptionStmt.executeUpdate();
            return healthRowsAffected > 0;

        } catch (IllegalArgumentException e) {
            System.err.println("Invalid UUID format: " + animalId);
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }






}

