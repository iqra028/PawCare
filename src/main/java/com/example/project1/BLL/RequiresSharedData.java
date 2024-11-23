package com.example.project1.BLL;

import com.example.project1.BLL.PawCare;

public interface RequiresSharedData {
    void setSharedData(PawCare pawCare, LoginClassCredentials loginCredentials);
}