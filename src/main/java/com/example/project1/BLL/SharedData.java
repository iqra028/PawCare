package com.example.project1.BLL;

import java.util.List;

public class SharedData {

    private static SharedData instance;
    private List<String> rescueCenters;
    private SharedData() {}
    public static SharedData getInstance() {
        if (instance == null) {
            instance = new SharedData();
        }
        return instance;
    }
    public void setRescueCenters(List<String> rescueCenters) {
        this.rescueCenters = rescueCenters;
    }
    public List<String> getRescueCenters() {
        return rescueCenters;
    }
}
