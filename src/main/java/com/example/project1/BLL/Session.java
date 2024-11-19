package com.example.project1.BLL;
import java.util.*;
public class Session {
    private static Session instance;
    private User loggedInUser;
    private RescueCenter loggedInRescueCenter;
    private Vets loggedInVets;


    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
    public RescueCenter getLoggedInRescueCenter() {
        return loggedInRescueCenter;
    }
    public Vets getLoggedInVets() {
        return loggedInVets;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }
    public void setLoggedInRescueCenter(RescueCenter rescueCenter) {
        this.loggedInRescueCenter = rescueCenter;
    }
    public void setLoggedInVets(Vets vets) {
        this.loggedInVets = vets;
    }

    public void clearSession() {
        this.loggedInUser = null;
    }
}