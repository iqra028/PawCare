package com.example.project1.BLL;

public class ProfileFactory {
    public Profile createProfile(String type,Animal animal)
    {
        switch (type.toLowerCase()) {
            case "adoption":
                return new AdoptionProfile(animal);
            case "animal":
                return new AnimalProfile(animal);

            default:
                throw new IllegalArgumentException("Invalid profile type: " + type);
        }

    }
}
