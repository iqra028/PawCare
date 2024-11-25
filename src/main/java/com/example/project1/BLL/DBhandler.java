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
    public ArrayList<Alert> getAllAlerts() {
        ArrayList<Alert> alerts = new ArrayList<>();
        String sql = "SELECT * FROM alerts"; // Assuming the table is named "alerts"

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // Parse location array from the database (e.g., "latitude,longitude")
                String locationStr = rs.getString("location");
                double[] location = parseLocation(locationStr);

                // Create a new Alert object with retrieved values
                Alert alert = new Alert(
                        rs.getString("type"),                // Animal type
                        rs.getString("breed"),               // Breed
                        rs.getString("message"),
                        null,// Injury description/message
                        //convertToImage(rs.getBytes("image")), // Convert byte array to Image
                        location,                             // User location (parsed double array)
                        rs.getString("userid"),              // User ID (UUID as String)
                        rs.getString("rescuecenterid")       // Rescue center ID (UUID as String)
                );

                // Set additional properties
                alert.setAlertId(rs.getString("alertId")); // Alert ID (UUID as String)
                alert.setCompleted(rs.getBoolean("completed")); // Completed status
                alert.setDateCreated(rs.getObject("dateCreated", LocalDate.class)); // Date created
                alert.setAlertType(rs.getString("alertType")); // Alert type

                alerts.add(alert);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alerts;
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
                String volid = resultSet.getString("volunteer_id");

                // Convert vehicle image bytes to Image object
                Image vehicleImage = byteArrayToImage(vehicleImageBytes);

                Volunteer volunteer = new Volunteer(userId, cnic, vehicleType, vehicleImage, vehicleModel, availability);
                volunteer.setVolunteerId(volid);
                volunteers.add(volunteer);
                System.out.println("successful");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return volunteers;
    }
    public boolean isUserVolunteer(String id) {
        String sql = "SELECT volunteer FROM \"user\" WHERE userid = ?";

        try (Connection conn = connect(); // Use your database connection method
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Set the userId parameter
            statement.setObject(1, UUID.fromString(id));

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
    public boolean setVolunteerAvailability(boolean availability,String id) {
        String sql = "UPDATE volunteer SET availability = ? WHERE userid = ?";

        try (Connection conn = connect(); // Use your database connection method
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Set the 'availability' parameter
            statement.setBoolean(1, availability);

            // Set the userId parameter
            statement.setObject(2, UUID.fromString(id));

            // Execute the update query
            int rowsUpdated = statement.executeUpdate();

            // Return true if at least one row was updated (indicating success)
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUserAvailable(String id) {
        String sql = "SELECT availability FROM volunteer WHERE userid = ?";

        try (Connection conn = connect(); // Use your database connection method
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Set the userId parameter
            statement.setObject(1, UUID.fromString(id));

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Get the 'availability' column value (assuming 'availability' is a boolean field)
                return resultSet.getBoolean("availability");
            }

            // User not found
            System.out.println("User not found in the volunteer table.");
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
        System.out.println("set completed to true");
        System.out.println("Alert ID: " + alertId);  // Print the alertId for debugging

        String sql = "UPDATE alert SET completed = true WHERE alertid = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Convert alertId to UUID and print for debugging
            UUID alertUUID = UUID.fromString(alertId);
            System.out.println("Converted UUID: " + alertUUID);

            stmt.setObject(1, alertUUID);  // Set the alertId parameter

            // Execute the update
            int rowsUpdated = stmt.executeUpdate();

            // Print the number of rows updated for debugging
            System.out.println("Rows updated: " + rowsUpdated);

            // Return true if at least one row was updated
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println("Error updating alert record: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public boolean setCompletedToTrueRC(String alertId) {
        System.out.println("set completed to true");
        System.out.println("Alert ID: " + alertId);  // Print the alertId for debugging

        String sql = "UPDATE alert SET completed = true WHERE alertid = ? AND \"alertType\" = 'User'";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Convert alertId to UUID and print for debugging
            UUID alertUUID = UUID.fromString(alertId);
            System.out.println("Converted UUID: " + alertUUID);

            stmt.setObject(1, alertUUID);  // Set the alertId parameter

            // Execute the update
            int rowsUpdated = stmt.executeUpdate();

            // Print the number of rows updated for debugging
            System.out.println("Rows updated: " + rowsUpdated);

            // Return true if at least one row was updated
            return rowsUpdated > 0;

        } catch (SQLException e) {
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
            UUID uuid = UUID.fromString(userid);
            stmt.setObject(1, uuid);

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Alert alert = new Alert();
                    alert.setType(rs.getString("type"));
                    alert.setMessage(rs.getString("message"));
                    alert.setBreed(rs.getString("breed"));
                    alert.setAlertId(rs.getString("alertId"));

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

    public void animalWentToVet(String animal_id) {

    }
    public void addvet_animal(String animal_id, String vet_id) {
        // SQL query to insert into the animal_vet table
        String sql = "INSERT INTO vet_animals (vetid, animalid) VALUES (CAST(? AS UUID), CAST(? AS UUID))";

        try (Connection conn = connect(); // Ensure you have a valid database connection
             PreparedStatement stmt = conn.prepareStatement(sql);) {

            // Validate and set animal_id and vet_id as UUID
            UUID animalUuid = UUID.fromString(vet_id); // Ensure it's a valid UUID
            UUID vetUuid = UUID.fromString(animal_id);
            // Ensure it's a valid UUID

            stmt.setObject(1, vetUuid);
            stmt.setObject(2,animalUuid );


            // Execute the update and check if it was successful
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Animal " + animal_id + " successfully assigned to Vet " + vet_id + ".");
            } else {
                System.out.println("Failed to assign Animal " + animal_id + " to Vet " + vet_id + ".");
            }

        } catch (SQLException e) {
            // Log SQL exceptions
            System.err.println("Error while inserting into animal_vet: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // Handle invalid UUID format
            System.err.println("Invalid UUID format: " + e.getMessage());
        }
    }
    public void updateanimalwenttovet(String animal_id) {
        // SQL query to insert into the animal_vet table
        String sql = "UPDATE animals SET with_vet = true WHERE animal_id = CAST(? AS UUID)";

        try (Connection conn = connect(); // Ensure you have a valid database connection
             PreparedStatement stmt = conn.prepareStatement(sql);) {

            UUID vetUuid = UUID.fromString(animal_id);
            // Ensure it's a valid UUID

            stmt.setObject(1, vetUuid);


            // Execute the update and check if it was successful
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Animal " + animal_id + " successfully ");
            }

        } catch (SQLException e) {
            // Log SQL exceptions
            System.err.println("Error while inserting into animal_vet: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // Handle invalid UUID format
            System.err.println("Invalid UUID format: " + e.getMessage());
        }
    }


    public void updateVetAnimalHandled(String vetId, String animalId, boolean handledStatus) {
        String updateQuery = "UPDATE vet_animals " +
                "SET handled = ? " +
                "WHERE vetid = CAST(? AS UUID) " +
                "AND animalid = CAST(? AS UUID)";

        try (Connection connection = connect();  // Ensure you have a valid database connection
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            // Set the parameters for the query
            preparedStatement.setBoolean(1, handledStatus);

            // Convert input strings to UUID and set in the query
            try {
                preparedStatement.setObject(2, UUID.fromString(vetId));
                preparedStatement.setObject(3, UUID.fromString(animalId));
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid UUID format for VetID or AnimalID: " + e.getMessage());
                return; // Exit the function if UUID conversion fails
            }

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully updated 'handled' status to " + handledStatus +
                        " for VetID: " + vetId + ", AnimalID: " + animalId);
            } else {
                System.out.println("No matching records found for VetID: " + vetId + ", AnimalID: " + animalId);
            }

        } catch (SQLException e) {
            System.err.println("Error while updating vet_animals table: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void visitedvet(String animalId) {
        String updateQuery = "UPDATE animals " +
                "SET with_vet = false, visited_vet = true " +
                "WHERE animal_id = CAST(? AS UUID)";

        try (Connection connection = connect();
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

            // Convert animalId to UUID and set it in the query
            try {
                updateStmt.setObject(1, UUID.fromString(animalId));
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid UUID format for animal_id: " + e.getMessage());
                return; // Exit the function if UUID is invalid
            }

            // Execute the update
            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Animal status updated successfully for animal_id: " + animalId);
            } else {
                System.out.println("No records were updated. Check if the animal_id exists.");
            }

        } catch (SQLException e) {
            System.err.println("Error updating animal status: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadanimalsinvet(Vets vet) {
        String fetchAnimalsQuery = "SELECT a.animal_id, a.name, a.type, a.breed, a.color, " +
                "a.health_id, a.health_status, a.visited_vet, a.with_vet, " +
                "a.up_for_adoption, a.adopted, a.image, a.rescue_center_id " +  // Fetch image as bytea
                "FROM animals a " +
                "JOIN vet_animals va ON a.animal_id = va.animalid " +
                "WHERE va.vetid = CAST(? AS UUID) " +
                "AND va.handled = false";


        try (Connection connection = connect()) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connection to the database was successful.");
            }

            try (PreparedStatement fetchAnimalsStmt = connection.prepareStatement(fetchAnimalsQuery)) {
                fetchAnimalsStmt.setString(1, vet.getVetID()); // Pass as a string, explicitly cast in the query

                List<Profile> animalList = new ArrayList<>();

                try (ResultSet animalResultSet = fetchAnimalsStmt.executeQuery()) {
                    while (animalResultSet.next()) {
                        Animal animal = new Animal();
                        animal.setAnimalID(animalResultSet.getString("animal_id"));
                        animal.setName(animalResultSet.getString("name"));
                        animal.setType(animalResultSet.getString("type"));
                        animal.setBreed(animalResultSet.getString("breed"));
                        animal.setColor(animalResultSet.getString("color"));
                        animal.setHealthStatus(animalResultSet.getBoolean("health_status"));
                        animal.setVisitedVet(animalResultSet.getBoolean("visited_vet"));
                        animal.setWithVet(animalResultSet.getBoolean("with_vet"));
                        animal.setUpForAdoption(animalResultSet.getBoolean("up_for_adoption"));
                        animal.setAdopted(animalResultSet.getBoolean("adopted"));
                        String rescuecenterid=animalResultSet.getString("rescue_center_id");

                        // Retrieve the image as byte array
                        byte[] imageBytes = animalResultSet.getBytes("image");
                        if (imageBytes !=null) {
                            animal.setImage(byteArrayToImage(imageBytes));  // Set image as byte array
                        }

                        Profile f=new AnimalProfile(animal);
                        f.setRescueCenterId(rescuecenterid);
                        animalList.add(f);
                       // vet.addprofile(animal,rescuecenterid);
                    }
                }

                vet.setProfiles(animalList);
                // Assuming this method exists to set the animal list for the vet
                System.out.println("Loaded " + animalList.size() + " animals for vet " + vet.getVetID());
            }

        } catch (SQLException e) {
            System.err.println("Error loading animals for vet: " + e.getMessage());
            e.printStackTrace();
        }
    }









    public String storeAlertRecord(Alert alert, String type) {
        System.out.println("Entering storeAlertRecord method");
        if(alert.getImage()==null)
        {
            System.out.println("image is null");
        }
        System.out.println(alert.getImage());

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
            ////if (alert.getImage() != null) {
               // byte[] imageBytes = imageToByteArray(alert.getImage());
                stmt.setBytes(4, imageToByteArray(alert.getImage())); // Image
            //} else {
            //  stmt.setBytes(4, null); // No image
           // }

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
                alert.setAlertId(rs.getString("alertId"));

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

    public List<Donation> displayDonationRecords(String id) {
        String sql = "SELECT * FROM donations WHERE rescuecenterid = ?";
        List<Donation> donations = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, UUID.fromString(id)); // Convert to UUID if necessary

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
                healthStmt.setObject(1, healthId);
                healthStmt.setDouble(2, animal.getHealth().getTemperature());
                healthStmt.setInt(3, animal.getHealth().getHeartRate());
                healthStmt.setInt(4, animal.getHealth().getRespiratoryRate());
                healthStmt.setInt(5, animal.getHealth().getCapillaryRefillTime());
                healthStmt.setInt(6, animal.getHealth().getBloodOxygenLevel());
                healthStmt.setInt(7, animal.getHealth().getBloodGlucoseLevel());
                healthStmt.setDouble(8, animal.getHealth().getWeight());
                healthStmt.executeUpdate();
                conn.commit(); // Commit after health description insert
            }

            // Generate UUID for animalId
            UUID animalId = UUID.randomUUID();
            UUID rescueCenterUUID = UUID.fromString(rescueCenterId);

            // Insert into animals table
            try (PreparedStatement animalStmt = conn.prepareStatement(insertAnimalQuery)) {
                animalStmt.setObject(1, animalId);
                animalStmt.setString(2, animal.getName());
                animalStmt.setString(3, animal.getType());
                animalStmt.setString(4, animal.getBreed());
                animalStmt.setString(5, animal.getColor());
                animalStmt.setObject(6, healthId);
                animalStmt.setBoolean(7, animal.isHealthStatus());
                animalStmt.setBoolean(8, animal.isVisitedVet());
                animalStmt.setBoolean(9, animal.isWithVet());
                animalStmt.setBoolean(10, animal.isUpForAdoption());
                animalStmt.setBoolean(11, animal.isAdopted());
                animalStmt.setObject(12, rescueCenterUUID);
                animalStmt.setBytes(13, imageToByteArray(animal.getImage()));

                try (ResultSet rs = animalStmt.executeQuery()) {
                    if (rs.next()) {
                        animalId = UUID.fromString(rs.getString("animal_id"));
                    }
                }
                conn.commit(); // Commit after animal insert
            }

            return animalId.toString(); // Return the UUID as a String
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
    public ArrayList<Vitals> getVitals() throws SQLException {
        String query = "SELECT * FROM vitals";
        ArrayList<Vitals> vitalsList = new ArrayList<>();

        try (Connection conn = connect(); PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String vitalsIdString = resultSet.getString("id");
                UUID vitalsId = UUID.fromString(vitalsIdString);
                String vitalsIdAsString = vitalsId.toString();

                // Retrieve other columns as usual
                double lowerTemperature = resultSet.getDouble("lowertemperature");
                double upperTemperature = resultSet.getDouble("uppertemperature");
                String animalType = resultSet.getString("animaltype");
                int lowerHeartRate = resultSet.getInt("lowerheartrate");
                int upperHeartRate = resultSet.getInt("upperheartrate");
                int lowerRespiratoryRate = resultSet.getInt("lowerrespiratoryrate");
                int upperRespiratoryRate = resultSet.getInt("upperrespiratoryrate");
                int capillaryRefillTime = resultSet.getInt("capillaryrefilltime");
                int lowerBloodOxygen = resultSet.getInt("lowerbloodoxygen");
                int upperBloodOxygen = resultSet.getInt("upperbloodoxygen");
                int lowerBloodGlucose = resultSet.getInt("lowerbloodglucose");
                int upperBloodGlucose = resultSet.getInt("upperbloodglucose");

                Vitals vitals = new Vitals(vitalsIdAsString, lowerTemperature, upperTemperature, animalType,
                        lowerHeartRate, upperHeartRate, lowerRespiratoryRate, upperRespiratoryRate,
                        capillaryRefillTime, lowerBloodOxygen, upperBloodOxygen, lowerBloodGlucose, upperBloodGlucose);

                vitalsList.add(vitals);
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving vitals data for all animal types.", e);
        }

        return vitalsList;
    }


    public void saveReport(injuryReport report) {


        // SQL query to insert the report data
        String sql = "INSERT INTO report (vetid, rescuecenterid, animal_id, description, temperature, heartRate, " +
                "respiratoryRate, capillaryRefillTime, bloodOxygenLevel, bloodGlucoseLevel, weight) " +
                "VALUES (CAST(? AS UUID), CAST(? AS UUID), CAST(? AS UUID), ?, ?, ?, ?, ?, ?, ?, ?)";


        try (Connection conn = connect(); // Ensure you have a valid database connection
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Try to convert and set UUIDs
            try {
                stmt.setObject(1, UUID.fromString(report.getVetid())); // Set vetid as UUID
                stmt.setObject(2, UUID.fromString(report.getRescuecenterid())); // Set rescuecenterid as UUID
                stmt.setObject(3, UUID.fromString(report.getAnimal_id())); // Set animal_id as UUID
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid UUID format: " + e.getMessage());
                return; // Return early if UUIDs are invalid
            }

            // Set the other values in the SQL statement
            stmt.setString(4, report.getDescription()); // Set description
            stmt.setDouble(5, report.getTemperature()); // Set temperature
            stmt.setInt(6, report.getHeartRate()); // Set heart rate
            stmt.setInt(7, report.getRespiratoryRate()); // Set respiratory rate
            stmt.setInt(8, report.getCapillaryRefillTime()); // Set capillary refill time
            stmt.setInt(9, report.getBloodOxygenLevel()); // Set blood oxygen level
            stmt.setInt(10, report.getBloodGlucoseLevel()); // Set blood glucose level
            stmt.setDouble(11, report.getWeight()); // Set weight

            // Execute the update
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Report successfully saved.");
            } else {
                System.out.println("Failed to save report.");
            }

        } catch (SQLException e) {
            System.err.println("Error while saving report: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Get reports by rescue center
    public List<injuryReport> getReportsByRescueCenter(String rescuecenterid) {
        List<injuryReport> reports = new ArrayList<>();
        String sql = "SELECT * FROM report WHERE rescuecenterid = CAST(? AS UUID)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, UUID.fromString(rescuecenterid));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                injuryReport report = new injuryReport(
                        //rs.getString("reportid"),
                        rs.getString("vetid"),
                        rs.getString("rescuecenterid"),
                        rs.getString("animal_id"),
                        rs.getString("description"),
                        rs.getDouble("temperature"),
                        rs.getInt("heart_rate"),
                        rs.getInt("respiratory_rate"),
                        rs.getInt("capillary_refill_time"),
                        rs.getInt("blood_oxygen_level"),
                        rs.getInt("blood_glucose_level"),
                        rs.getDouble("weight")
                );
                reports.add(report);
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching reports by rescuecenterid: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid UUID format: " + e.getMessage());
        }
        return reports;
    }

    // Get reports by animal ID
    public List<injuryReport> getReportsByAnimalId(String animal_id) {
        List<injuryReport> reports = new ArrayList<>();
        String sql = "SELECT * FROM report WHERE animal_id = CAST(? AS UUID)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, UUID.fromString(animal_id));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                injuryReport report = new injuryReport(
                        rs.getString("vetid"),
                        rs.getString("rescuecenterid"),
                        rs.getString("animal_id"),
                        rs.getString("description"),
                        rs.getDouble("temperature"),
                        rs.getInt("heart_rate"),
                        rs.getInt("respiratory_rate"),
                        rs.getInt("capillary_refill_time"),
                        rs.getInt("blood_oxygen_level"),
                        rs.getInt("blood_glucose_level"),
                        rs.getDouble("weight")
                );
                reports.add(report);
            }

        } catch (SQLException e) {
            System.err.println("Error while fetching reports by animal_id: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid UUID format: " + e.getMessage());
        }
        return reports;
    }

    // Load all reports
    public List<injuryReport> loadReports() {
        List<injuryReport> reports = new ArrayList<>();
        String sql = "SELECT * FROM report";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                injuryReport report = new injuryReport(
                        rs.getString("vetid"),
                        rs.getString("rescuecenterid"),
                        rs.getString("animal_id"),
                        rs.getString("description"),
                        rs.getDouble("temperature"),
                        rs.getInt("heartrate"),
                        rs.getInt("respiratoryRate"),
                        rs.getInt("capillaryRefillTime"),
                        rs.getInt("bloodOxygenLevel"),
                        rs.getInt("bloodGlucoseLevel"),
                        rs.getDouble("weight")
                );
                reports.add(report);
            }

            // Log the number of reports loaded
            System.out.println("Number of reports loaded: " + reports.size());

        } catch (SQLException e) {
            System.err.println("Error while loading all reports: " + e.getMessage());
            e.printStackTrace();
        }

        return reports;
    }


    public void addAdoptionRequest(AdoptionRequest adoptionRequest) {
        // Insert query with auto-generated requestID
        String query = "INSERT INTO adoption_requests (user_id, rescue_center_id, animal_id, has_allergy, has_suitable_living_condition, reason_to_adopt, application_status, is_resolved) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setObject(1, UUID.fromString(adoptionRequest.getUserId()));  // Convert string to UUID before setting
            stmt.setObject(2, UUID.fromString(adoptionRequest.getRescueCenterId()));  // Convert string to UUID before setting
            stmt.setObject(3, UUID.fromString(adoptionRequest.getAnimalId()));  // Convert string to UUID before setting
            stmt.setBoolean(4, adoptionRequest.isHas_allergy());
            stmt.setBoolean(5, adoptionRequest.isSuitable_living_conditions());
            stmt.setString(6, adoptionRequest.getReason_to_adopt());
            stmt.setBoolean(7, adoptionRequest.isApplicationStatus());
            stmt.setBoolean(8, adoptionRequest.getIsResolved());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        String generatedRequestID = generatedKeys.getString(1);
                        adoptionRequest.setRequestID(generatedRequestID);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<AdoptionRequest> getAdoptionRequests(RescueCenter rc) {
        ArrayList<AdoptionRequest> adoptionRequests = new ArrayList<>();
        String rescueCenterIdString = rc.getRescueCenterID();
        UUID rescueCenterId = UUID.fromString(rescueCenterIdString);

        String query = "SELECT * FROM adoption_requests WHERE rescue_center_id = ?";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setObject(1, rescueCenterId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String requestId = resultSet.getObject("request_id", UUID.class).toString();
                String userId = resultSet.getObject("user_id", UUID.class).toString();
                String animalId = resultSet.getObject("animal_id", UUID.class).toString();
                boolean hasAllergy = resultSet.getBoolean("has_allergy");
                boolean suitableLivingConditions = resultSet.getBoolean("has_suitable_living_condition");
                String reason = resultSet.getString("reason_to_adopt");
                boolean applicationStatus = resultSet.getBoolean("application_status");
                boolean isResolved = resultSet.getBoolean("is_resolved"); // New attribute

                AdoptionRequest adoptionRequest = new AdoptionRequest(requestId, userId, rescueCenterIdString, animalId,
                        hasAllergy, suitableLivingConditions, reason, applicationStatus, isResolved);

                adoptionRequests.add(adoptionRequest);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adoptionRequests;
    }

    public boolean updateAdoptionReqStatus(String rescueCenterID, AdoptionRequest request) {
        String query = "UPDATE adoption_requests SET application_status = ?, is_resolved = ? WHERE rescue_center_id = ? AND request_id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, request.isApplicationStatus());
            stmt.setBoolean(2, request.getIsResolved());
            stmt.setObject(3, UUID.fromString(rescueCenterID));
            stmt.setObject(4, UUID.fromString(request.getRequestID()));

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if update was successful
        } catch (SQLException e) {
            System.err.println("Error updating adoption request in database: " + e.getMessage());
            return false; // Return false in case of failure
        }
    }





}

