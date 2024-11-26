package com.example.project1.BLL.Profiles;

import com.example.project1.BLL.Animal;

public class AdoptionProfile extends Profile {

    public AdoptionProfile(Animal animal) {
        super("Adoption", animal);
    }
    public  Profile FindMatch(String type,String name, String breed, String color )
    {
        if (animal.getType().equalsIgnoreCase(type) &&
                animal.getBreed().equalsIgnoreCase(breed) &&
                animal.getColor().equalsIgnoreCase(color)) {
            return this;
        }
        return null;
    }




}
