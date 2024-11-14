package com.example.project1.BLL;

import java.util.*;
public class FirstAid {

    private String firstaidId;
    private String injuryType;
    private String instructions;
    private String materials;

    public void updateFirstAidInfo(String newInstructions, String newMaterials) {
        this.instructions = newInstructions;
        this.materials = newMaterials;

    }

    // Method to get first aid information based on injury type
    public String getFirstAid(String injuryType) {
        return "hi";
    }

    // Method for step-by-step guidance
    public String stepByStepGuidance() {
        return "Step-by-Step Guidance for " + injuryType + ":\n" + instructions;
    }
}
