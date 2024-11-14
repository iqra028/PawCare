package com.example.project1.BLL;

public class FormFactory {

        public static Form createForm(String formType) {
            switch (formType) {
                case "UserRegistration":
                    return new UserRegistrationForm("", "", "", "","");
                case "VetRegistration":
                    return new VetRegistrationForm( "", "", "", "", "", "");
                case "RescueCenterRegistration":
                    return new RescueCenterRegForm("", "", "", "", "", "");
                case "LoginForm":
                    return new LoginForm("", "","");
                default:
                    throw new IllegalArgumentException("Unknown form type: " + formType);
            }
        }

}
