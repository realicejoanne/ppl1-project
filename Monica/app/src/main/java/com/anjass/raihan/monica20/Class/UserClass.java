package com.anjass.raihan.monica20.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserClass {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;

    public UserClass(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
