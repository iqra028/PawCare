package com.example.project1.BLL.Profiles;

import com.example.project1.BLL.Animal;

public abstract class Profile {
    private String profileType;
    protected Animal animal;
    protected String rescueCenterId;

    public Profile(String profileType, Animal animal) {
        this.profileType = profileType;
        this.animal = animal;
        rescueCenterId="";
    }

    public String getRescueCenterId() {
        return rescueCenterId;
    }

    public void setRescueCenterId(String rescueCenterId) {
        this.rescueCenterId = rescueCenterId;
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
    public abstract Profile FindMatch(String type,String name, String breed, String color );
}