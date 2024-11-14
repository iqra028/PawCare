package com.example.project1.BLL;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;
public class Main {
    public static void main(String[] args) {
        PawCare pawCare = new PawCare();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to PawCare");
        System.out.println("Select an option:");
        System.out.println("1. Login");
        System.out.println("2. Register");

        int mainChoice = scanner.nextInt();
        scanner.nextLine();

        switch (mainChoice) {
            case 1:
                pawCare.login();
                break;

            case 2:
                System.out.println("Select an option to register:");
                System.out.println("1. User");
                System.out.println("2. Vet");
                System.out.println("3. Rescue Center");

                int registerChoice = scanner.nextInt();
                scanner.nextLine();

                switch (registerChoice) {
                    case 1:
                        pawCare.registerUser();
                        break;
                    case 2:
                        pawCare.registerVet();
                        break;
                    case 3:
                        pawCare.registerRescueCenter();
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
                break;

            default:
                System.out.println("Invalid choice.");
        }
        scanner.close();


    }
}