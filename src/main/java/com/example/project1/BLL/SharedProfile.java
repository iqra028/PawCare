package com.example.project1.BLL;

public class SharedProfile {
    private static SharedProfile instance;
    private Profile selectedAnimalProfile;

    private SharedProfile() {
    }
    public static SharedProfile getInstance() {
        if (instance == null) {
            instance = new SharedProfile();
        }
        return instance;
    }
    public Profile getSelectedAnimalProfile() {
        return selectedAnimalProfile;
    }

    public void setSelectedAnimalProfile(Profile profile) {
        this.selectedAnimalProfile = profile;
    }
}
