package com.example.project1.BLL;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public abstract class PersistanceHandler {

    public abstract boolean addUser(User user);
    public abstract boolean addRescueCenter(RescueCenter rescueCenter);
    public abstract boolean addVet(Vets vet);
    public abstract ArrayList<Vets> getAllVets();
    public abstract ArrayList<RescueCenter> getAllRescueCenters();
    public abstract ArrayList<User> getAllUsers();

}

