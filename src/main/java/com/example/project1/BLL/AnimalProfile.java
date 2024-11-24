package com.example.project1.BLL;

public class AnimalProfile extends Profile{

    public AnimalProfile(Animal animal) {
        super("Animal", animal);
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