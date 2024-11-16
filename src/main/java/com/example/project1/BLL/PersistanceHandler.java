package com.example.project1.BLL;
import java.util.UUID;
import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class PersistanceHandler {

    private String jdbcURL;
    private String username;
    private String password;


    public PersistanceHandler() {
        this.jdbcURL = "jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:6543/postgres";
        this.username = "postgres.crlvllcnuffkzpyrlmsu";
        this.password = "onyourface123";

    }

    protected Connection connect() throws SQLException {
        return DriverManager.getConnection(jdbcURL, username, password);
    }

    public String getIDByUsername(String tableName, String idColumn, String usernameColumn, String username) {
        //String sql = "SELECT " + idColumn + " FROM \"" + tableName + "\" WHERE \"" + usernameColumn + "\" = ?";
        String sql = "SELECT " + idColumn + " FROM " + tableName + " WHERE " + usernameColumn + " = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    UUID id = (UUID) rs.getObject(idColumn);
                    return id != null ? id.toString() : null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public abstract boolean storeRecord(String centerName, String username, String password, String phoneNumber, String location, String email);
    public abstract ArrayList<Vets> getAllVets();
    public abstract ArrayList<RescueCenter> getAllRescueCenters();
    public abstract ArrayList<User> getAllUsers();


}

