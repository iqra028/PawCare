package com.example.project1.BLL;

public abstract class Form {

    protected String formType;
    protected boolean checkForm;

    public Form(String formType) {

        this.formType = formType;
        this.checkForm=false;

    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public abstract boolean enterDetails();

    public boolean submitForm() {
        // Additional validation??
        return true;
    }

}
