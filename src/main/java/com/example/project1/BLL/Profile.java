package com.example.project1.BLL;

public abstract class Profile {
    private String profileType;
    protected Animal animal;

    public Profile(String profileType, Animal animal) {
        this.profileType = profileType;
        this.animal = animal;
    }

    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

}
